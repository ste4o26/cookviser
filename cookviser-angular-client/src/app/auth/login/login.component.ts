import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { pipe, Subscription } from 'rxjs';
import { first } from 'rxjs/operators';
import { AuthService } from '../auth.service';
import { IUserLogin } from '../interface/user-login.interface';
import { IUser } from '../interface/user.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, OnDestroy {
  private authService: AuthService;
  private router: Router;
  private subscirptions: Subscription[];

  public constructor(authService: AuthService, router: Router) {
    this.authService = authService;
    this.router = router;
    this.subscirptions = [];
  }

  public ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/home');
    }
  }

  public ngOnDestroy(): void {
    this.subscirptions.forEach(subscription => subscription.unsubscribe());
  }

  public submitHandler(user: IUserLogin) {
    const login$: Subscription = this.authService
      .login(user)
      .subscribe(
        (response: HttpResponse<IUser>) => {
          // @ts-ignore: Object is possibly 'null'.
          const token: string = response.headers.get('jwtToken');
          this.authService.setUserToken(token);

          // @ts-ignore: Object is possibly 'null'.
          const username: string = response.body.username;
          this.authService.setLoggedInUsername(username);

          this.router.navigateByUrl('/home');
        },
        (errorResponse: HttpErrorResponse) => {
          // TODO handle the errors properly with eror component and/or notifications!
          console.log(errorResponse.error.message)
        }
      );

    this.subscirptions.push(login$);
  }

}
