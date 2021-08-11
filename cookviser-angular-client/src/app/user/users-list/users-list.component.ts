import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, DoCheck, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { IUser } from 'src/app/auth/interface/user.interface';
import { NotificationService } from 'src/app/shered/notification.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  public users: IUser[] = [];

  public constructor(private userService: UserService,
    private notificationService: NotificationService,
    private router: Router) { }

  public promoteUser(username: string) {
    this.userService.promote(username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (response.body === null || response.status >= 500) {
          this.notificationService.showError('Something went wrong on our side! Please try again later.');
          return;
        }
        const message = `User ${response.body?.username} was promoted to ${response.body?.role.roleName}`;
        this.notificationService.showSucces(message);
        this.users = this.users.filter(user => user.username !== response.body?.username);
        this.users.push(response.body);
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
  }

  public demoteUser(username: string) {
    this.userService.demote(username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (response.body === null || response.status >= 500) {
          this.notificationService.showError('Something went wrong on our side! Please try again later.');
          return;
        }
        const message = `User ${response.body?.username} was promoted to ${response.body?.role.roleName}`;
        this.notificationService.showSucces(message);
        this.users = this.users.filter(user => user.username !== response.body?.username);
        this.users.push(response.body);
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
  }

  public ngOnInit(): void {
    const users$ = this.userService
      .fetchAll()
      .subscribe((data: IUser[]) => this.users = data,
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(users$);
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
