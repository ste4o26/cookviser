import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from '../auth.service';
import { IUserRegister } from '../interface/user-register.interface';
import { IUser } from '../interface/user.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {
  private authService: AuthService;
  private router: Router;
  private subscriptions: Subscription[];

  public constructor(authService: AuthService, router: Router) {
    this.authService = authService;
    this.router = router;
    this.subscriptions = [];
  }

  public ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/home');
    }
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  public keyUpHandler(email: string) {
    const regex: string = '[a-zA-Z0-9._-]{3,}@[a-zA-Z0-9.-]{3,}\.[a-zA-Z]{2,4}';
  }

  public submitHandler(user: IUserRegister): void {
    const register$: Subscription = this.authService
      .register(user)
      .subscribe((response: HttpResponse<IUser>) => {
        // TODO display some messages to the user! 
        this.router.navigateByUrl('/auth/login');
      }, (errorResponse: HttpErrorResponse) => console.log(errorResponse.error.message));
      
    this.subscriptions.push(register$);
  }
}
