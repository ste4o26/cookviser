import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { IUser } from '../auth/interface/user.interface';
import { NotificationService } from '../shered/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {

  public constructor(private authService: AuthService, private router: Router, private notificationService: NotificationService) { }

  private isBasicUser(): boolean {
    const user: IUser = this.authService.getLoggedInUser();
    return user.role.roleName === 'ROLE_USER';
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (this.isBasicUser()){
      this.router.navigateByUrl('/home');
      this.notificationService.showError('You are not authorized to go to that page!');
      return false;
    }

    return true;
  }
}
