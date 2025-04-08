import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule, RouterLink],
  template: `
    <h2>Register</h2>
    <form [formGroup]="registerForm" (ngSubmit)="onSubmit()">
      <label for="username">Username</label>
      <input id="username" type="email" formControlName="email" />
      <label for="password">Password</label>
      <input id="password" type="password" formControlName="password" />
      <input type="submit" />
    </form>
    <div>
      <span>Already have an account? <a routerLink="/login">Log In</a></span>
    </div>
  `,
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  constructor(private authService: AuthService) {}

  loading = false;
  errorMessage = '';

  registerForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  onSubmit() {
    const email = this.registerForm.value.email || '';
    const password = this.registerForm.value.password || '';

    this.authService.register(email, password).subscribe({
      next: () => {
        this.loading = false;
        // Redirect or perform other actions after successful login
        console.log('SUCCESS');
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage = 'Register failed. Please check your credentials.';
        console.error(error); // Optional: Log the error for debugging
      },
    });
  }
}
