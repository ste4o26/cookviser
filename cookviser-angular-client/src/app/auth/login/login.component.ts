import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationService } from 'src/app/shered/notification.service';
import { AuthService } from '../auth.service';
import { IUserLogin } from '../interface/user-login.interface';
import { IUser } from '../interface/user.interface';
import { ConstantMessages } from '../../shered/constants'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  private subscirptions: Subscription[] = [];

  public constructor(private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService) { }

  public submitHandler(user: IUserLogin) {
    const login$: Subscription = this.authService
      .login(user)
      .subscribe(
        (response: HttpResponse<IUser>) => {
          if (response.body === null) {
            this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
            return;
          }

          const token = response.headers.get('jwtToken');
          if (token === null) {
            this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
            return;
          }

          this.authService.setUserToken(token);
          this.authService.setLoggedInUsername(response.body?.username);
          this.authService.setLoggedInUser(response.body);

          this.notificationService.showSucces(ConstantMessages.LOG_IN_SUCCES);
          this.router.navigateByUrl('/home');
        }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscirptions.push(login$);
  }

  public ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/home');
    }
  }

  public ngOnDestroy(): void {
    this.subscirptions.forEach(subscription => subscription.unsubscribe());
  }
}
