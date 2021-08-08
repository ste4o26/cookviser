import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { NotificationService } from '../shered/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  public constructor(private authService: AuthService, private router: Router, private notficationService: NotificationService) {}

  private isLoggedInUser(): boolean {
    if (this.authService.isLoggedIn()) {
      return true;
    }

    this.router.navigate(['/auth/login']);
    this.notficationService.showError('Access Denied! Please Log In to continue.');
    return false;
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    return this.isLoggedInUser();
  }
}
