import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { jwtDecode } from 'jwt-decode';

interface LoginResponse {
  token: string;
}

interface DecodedToken {
  sub: string;
  iat: number;
  exp: number;
  roles: string[];
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/api/auth';
  private readonly TOKEN_KEY = 'finance_manager_auth_token';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(
    this.hasValidToken()
  );

  constructor(private http: HttpClient, private router: Router) {}

  // --- Auth Actions ---
  login(email: string, password: string) {
    return this.http
      .post<LoginResponse>(`${this.API_URL}/authenticate`, { email, password })
      .pipe(
        tap((response) => {
          this.storeToken(response.token);
          this.isAuthenticatedSubject.next(true);
          this.router.navigate(['/dashboard']);
        })
      );
  }

  register(email: string, password: string) {
    return this.http
      .post<LoginResponse>(`${this.API_URL}/register`, { email, password })
      .pipe(
        tap((response) => {
          this.storeToken(response.token);
          this.isAuthenticatedSubject.next(true);
          this.router.navigate(['/login']);
        })
      );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/login']);
  }

  // --- Token Management ---
  private storeToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  private hasValidToken(): boolean {
    const token = this.getToken();
    if (!token) return false;

    try {
      const payload = this.decodeToken(token);
      return payload.exp * 1000 > Date.now();
    } catch (e) {
      return false;
    }
  }

  private decodeToken(token: string): DecodedToken {
    return jwtDecode(token);
  }

  // --- Role & Auth Checks ---
  get isAuthenticated(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  getUserRole(): string | null {
    // could add support for multiple rows later
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = this.decodeToken(token);
      const roles = payload.roles;
      return Array.isArray(roles) ? roles[0] : null;
    } catch {
      return null;
    }
  }

  hasRole(expectedRole: string): boolean {
    return this.getUserRole() === expectedRole;
  }

  isLoggedIn(): boolean {
    return this.hasValidToken();
  }
}
