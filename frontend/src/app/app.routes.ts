import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { DashboardComponent } from './home/dashboard.component';
import { Role } from './models/user';
import { AuthGuard } from './guards/auth.guard';
import { NotFoundComponent } from './not-found/not-found.component';
import { MainLayoutComponent } from './components/main-layout/main-layout.component';
import { GuestOnlyGuard } from './guards/guest-only.guard';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  // Pages with layout
  {
    path: '',
    component: MainLayoutComponent,
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent,
        canActivate: [AuthGuard],
        data: {
          roles: [Role.USER],
        },
      },
    ],
  },
  // Full screen pages
  { path: 'login', component: LoginComponent, canActivate: [GuestOnlyGuard] },
  { path: '**', component: NotFoundComponent },
];
