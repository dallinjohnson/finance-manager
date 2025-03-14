import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { BehaviorSubject, tap } from 'rxjs';

interface LoginResponse {
  token: string;
}

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private readonly API_URL = 'http://localhost:8080/api/auth';
  private readonly TOKEN_KEY = 'finance_manager_auth_token';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient, private router: Router) {
    this.checkInitialAuth();
  }

  private checkInitialAuth() {
    const token = localStorage.getItem(this.TOKEN_KEY);
    this.isAuthenticatedSubject.next(!!token);
  }

  login(email: string, password: string) {
    return this.http
      .post<LoginResponse>(`${this.API_URL}/authenticate`, {
        email: email,
        password: password,
      })
      .pipe(
        tap((response) => {
          this.storeToken(response.token);
          this.isAuthenticatedSubject.next(true);
          this.router.navigate(['/']);
        })
      );
  }

  register(email: string, password: string) {
    return this.http
      .post<LoginResponse>(`${this.API_URL}/register`, {
        email: email,
        password: password,
      })
      .pipe(
        tap((response) => {
          this.storeToken(response.token);
          this.isAuthenticatedSubject.next(true);
          // this.router.navigate(['/'])
          console.log(response);
        })
      );
  }

  logout() {
    localStorage.removeItem(this.TOKEN_KEY);
    this.isAuthenticatedSubject.next(false);
    // this.router.navigate(['/login']);
  }

  get isAuthenticated$() {
    return this.isAuthenticatedSubject.asObservable();
  }

  getToken(): string | null {
    return localStorage.getItem(this.TOKEN_KEY);
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  private storeToken(token: string) {
    localStorage.setItem(this.TOKEN_KEY, token);
  }
}
