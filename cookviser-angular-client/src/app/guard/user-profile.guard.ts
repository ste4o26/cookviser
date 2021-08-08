import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../auth/auth.service';
import { IUser } from '../auth/interface/user.interface';
import { NotificationService } from '../shered/notification.service';

@Injectable({
  providedIn: 'root'
})
export class UserProfileGuard implements CanActivate {

  public constructor(private authService: AuthService, private router: Router, private notificationService: NotificationService) { }

  private isBasicUser(): boolean {
    const loggedInUser: IUser = this.authService.getLoggedInUser();
    return loggedInUser.role.roleName === 'ROLE_USER';
  }

  public canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.isBasicUser()) {
      return true;
    }

    let userProfileUsername: string = route.params.username;
    if (this.isBasicUser() && this.authService.getLoggedInUsername() === userProfileUsername) {
      return true;
    }

    this.router.navigateByUrl('/home');
    this.notificationService.showError('You are not authorized to visit that page!');
    return false;
  }
}
