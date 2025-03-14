import { Component, Inject, inject } from '@angular/core';
import { ReactiveFormsModule, FormControl, FormGroup } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <h2>Login</h2>
    <form [formGroup]="loginForm" (ngSubmit)="onSubmit()">
      <label for="username">Username</label>
      <input id="username" type="email" formControlName="username" />
      <label for="password">Password</label>
      <input id="password" type="password" formControlName="password" />
      <input type="submit" />
    </form>
    <div>
      <span>Don't have an account? <a routerLink="/register">Register</a></span>
    </div>
  `,
  styleUrl: './login.component.css',
})
export class LoginComponent {
  constructor(private authService: AuthService) {}

  loading = false;
  errorMessage = '';

  loginForm = new FormGroup({
    username: new FormControl(''),
    password: new FormControl(''),
  });

  onSubmit() {
    const username = this.loginForm.value.username || '';
    const password = this.loginForm.value.password || '';

    this.loading = true;

    // Call the login method from AuthService
    this.authService.login(username, password).subscribe({
      next: () => {
        this.loading = false;
        // Redirect or perform other actions after successful login
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = 'Login failed. Please check your credentials.';
        console.error(error); // Optional: Log the error for debugging
      },
    });
  }

  ngOnInit(): void {
    if (this.authService.isAuthenticated$) {
      console.log('User is authenticated');
    }
  }
}
