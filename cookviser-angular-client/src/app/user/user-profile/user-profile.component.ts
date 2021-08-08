import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, DoCheck, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
import { NotificationService } from 'src/app/shered/notification.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  private profileImage: File | null = null;
  private username: string = '';

  public user: IUser | null = null;

  public constructor(private userService: UserService,
    private authService: AuthService,
    private router: Router,
    private notificationSercice: NotificationService,
    private activatedRoute: ActivatedRoute) { }


  private getUsernameFromParams(): void {
    const params$ = this.activatedRoute.params
      .subscribe(params => this.username = params.username);

    this.subscriptions.push(params$);
  }

  private loadUser(): void {
    this.getUsernameFromParams();
    // const username: string = this.authService.getLoggedInUsername();
    const user$ = this.userService
      .fetchByUsername(this.username)
      .subscribe((response: HttpResponse<IUser>) => this.user = response.body,
        (errorResponse: HttpErrorResponse) => this.notificationSercice.showError(errorResponse.error.message));

    this.subscriptions.push(user$);
  }

  private hasAnyUpdates(user: IUser): boolean {
    // return user.username !== this.user?.username || user.email !== this.user?.email || user.description !== this.user?.description;
    return user.email !== this.user?.email || user.description !== this.user?.description;
  }

  public changeProfileImageHandler(event: any): void {
    this.profileImage = event.target.files[0];
  }

  public submitImageHandler(): void {
    if (this.profileImage === null) {
      this.notificationSercice.showError('Profile image is required!');
      return;
    }

    if (this.user === null) {
      this.notificationSercice.showError("Please Log In to continue!");
      return;
    }

    const formData: FormData = new FormData();
    formData.append('profileImage', this.profileImage);
    formData.append('username', this.user.username);

    this.userService
      .updateProfileImage(formData)
      .subscribe((response: HttpResponse<IUser>) => {
        this.user = response.body;
        this.notificationSercice.showSucces('Profile image updated.');
      }, (errorResponse: HttpErrorResponse) => this.notificationSercice.showError(errorResponse.error.message));
  }

  public submitDataHandler(user: IUser): void {
    if (!this.hasAnyUpdates(user)) {
      this.notificationSercice.showError('You must change any of the fields to update your profile!');
      return;
    }

    this.userService.updateProfile(user, this.username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (response.body === null) {
          this.notificationSercice.showError('Something went wrong please Re Log In!');
          return;
        }
        this.user = response.body;
        this.router.navigateByUrl(`/user/profile/${this.username}`);
        this.notificationSercice.showSucces('Profile updated.');
      }, (errorResponse: HttpErrorResponse) => {
        if (errorResponse.status >= 500) {
          // TODO make an error page for all responses with >= 500 status!!!!
          this.notificationSercice.showError('Sorry the problem is on our side. We are working on it. Please try again later.');
        }

        this.notificationSercice.showError(errorResponse.error.message);
      });
  }

  public ngOnInit(): void {
    this.loadUser();
  }

  // ngDoCheck(): void {
  //   // this.loadUser();
  //   const olderUsername = this.username;
  //   console.log(olderUsername)
  //   this.getUsernameFromParams();
  //   if (olderUsername !== this.username) {
  //     this.loadUser();
  //     this.router.navigateByUrl(`/user/profile/${this.username}`);
  //   }
  // }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
