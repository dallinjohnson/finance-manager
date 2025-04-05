import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '../services/auth.service';
import { Role } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): MaybeAsync<GuardResult> {
    console.log('can activate ran');
    // If route has no roles, route will activate automatically
    const roles: Role[] = route.data['roles'];
    if (!roles) {
      return true;
    }

    if (!this.authService.isLoggedIn()) {
      return this.router.createUrlTree(['/login']);
    }

    const expectedRoles = route.data['roles'] as string[];

    const userRole = this.authService.getUserRole();
    if (!userRole || !expectedRoles.includes(userRole)) {
      return this.router.createUrlTree(['/unauthorized']);
    }

    return true;
  }
}
