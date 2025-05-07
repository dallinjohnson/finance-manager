import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/pages/login/login.component';
import { DashboardComponent } from './features/dashboard/pages/dashboard/dashboard.component';
import { Role } from './features/auth/models/user';
import { AuthGuard } from './features/auth/guards/auth.guard';
import { NotFoundComponent } from './core/pages/not-found/not-found.component';
import { GuestOnlyGuard } from './features/auth/guards/guest-only.guard';
import { TransactionsComponent } from './features/transactions/pages/transactions/transactions.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'dashboard' },
  {
    path: 'dashboard',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    data: {
      roles: [Role.USER],
    },
  },
  { 
    path: 'transactions', component: TransactionsComponent, canActivate: [AuthGuard],
    data: {
      roles: [Role.USER],
    }
  },
  { path: 'login', component: LoginComponent, canActivate: [GuestOnlyGuard] },
  { path: '**', component: NotFoundComponent },
];
