import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import {
  Component, DoCheck
  , OnDestroy, OnInit
} from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
import { UserService } from 'src/app/user/user.service';
import { NotificationService } from '../notification.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements DoCheck{
  private user: IUser | null = null;

  public username: string = '';
  public isLogedIn: boolean = false;
  public isBasicUser: boolean = true;

  public constructor(private authService: AuthService) { }

  private loadUserIfLoggedIn() {
    this.isLogedIn = this.authService.isLoggedIn();
    if (!this.isLogedIn) {
      return;
    }

    this.user = this.authService.getLoggedInUser();
    this.username = this.authService.getLoggedInUsername();
    this.isBasicUser = this.user?.role.roleName === 'ROLE_USER';
  }

  public clickHandler(): void {
    this.authService.logout();
    this.isLogedIn = this.authService.isLoggedIn();
  }

  public ngDoCheck(): void {
    this.loadUserIfLoggedIn();
  }
}
