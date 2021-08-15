import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'

import { Observable, Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
import { ConstantMessages } from 'src/app/shered/constants';
import { NotificationService } from 'src/app/shered/notification.service';
import { UserService } from 'src/app/user/user.service';
import { IRate } from '../interface/rate.interface';

import { IRecipe } from '../interface/recipe.interface';
import { RecipeService } from '../service/recipe.service';

@Component({
  selector: 'app-recipe-details',
  templateUrl: './recipe-details.component.html',
  styleUrls: ['./recipe-details.component.css']
})
export class RecipeDetailsComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  public recipe: IRecipe | null = null;
  public isCooking: boolean = false;
  public isBasicUser: boolean = true;

  public constructor(private recipeService: RecipeService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router) { }

  private loadRecipe(): void {
    let recipeId: string = '';
    const route$: Subscription = this.activatedRoute.params
      .subscribe(params => recipeId = params.id);

    const recipeDetails$: Subscription = this.recipeService
      .fetchById(recipeId)
      .subscribe(data => this.recipe = data);

    this.subscriptions.push(recipeDetails$);
    this.subscriptions.push(route$);
  }

  public toggleIsCookingHandler(): void { this.isCooking = !this.isCooking; }

  public onRateHandler(event: any): void {
    if (this.recipe == null || this.recipe.publisherUsername === undefined) {
      this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
      return;
    }
    const username = this.authService.getLoggedInUsername();
    if (username === null) {
      this.notificationService.showError(ConstantMessages.ACCESS_DENIED_ERROR);
      this.router.navigateByUrl('/auth/login');
      return;
    }

    const user$: Subscription = this.userService
      .fetchByUsername(username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (!response.ok || response.body == null) {
          this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
          return;
        }

        const rating: IRate = {
          rateValue: event.newValue,
          user: response.body,
          recipe: this.recipe
        }

        const rate$: Subscription = this.recipeService
          .rate(rating)
          .subscribe(data => {
            this.recipe = data.recipe
            this.notificationService.showSucces(`You have rated ${this.recipe?.name} with ${rating.rateValue} stars.`);
          }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

        this.subscriptions.push(rate$);
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(user$);
  }

  public deleteRecipeHandler(): void {
    if (this.recipe == null) {
      this.notificationService.showError(ConstantMessages.PLEASE_TRY_AGAIN_ERROR);
      return;
    }

    const recipe$: Subscription = this.recipeService.deleteById(this.recipe?.id)
      .subscribe((data: IRecipe) => {
        this.notificationService.showSucces(ConstantMessages.RECIPE_DELETED_SUCCESS);
        this.router.navigateByUrl('/recipe/all');
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.massage));

    this.subscriptions.push(recipe$);
  }

  public ngOnInit(): void {
    if (!this.authService.isLoggedIn()) { this.isBasicUser = true; }
    else { this.isBasicUser = this.authService.getLoggedInUser().role.roleName === 'ROLE_USER'; }
    this.loadRecipe();
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}