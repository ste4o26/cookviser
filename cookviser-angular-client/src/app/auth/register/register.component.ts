import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationService } from 'src/app/shered/notification.service';
import { AuthService } from '../auth.service';
import { IUserRegister } from '../interface/user-register.interface';
import { IUser } from '../interface/user.interface';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  public constructor(private authService: AuthService,
    private router: Router,
    private notificationService: NotificationService) { }

  public submitHandler(user: IUserRegister): void {
    const register$: Subscription = this.authService
      .register(user)
      .subscribe((response: HttpResponse<IUser>) => {
        this.notificationService.showSucces('You have register successfully.')
        this.router.navigateByUrl('/auth/login');
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(register$);
  }

  public ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigateByUrl('/home');
    }
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
