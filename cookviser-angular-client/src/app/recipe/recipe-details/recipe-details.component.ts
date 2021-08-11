import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'

import { Observable, Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
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

  public constructor(private recipeService: RecipeService,
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router) {
  }

  private loadRecipe(): void {
    let recipeId: string = '';
    const route$: Subscription = this.activatedRoute.params
      .subscribe(params => recipeId = params.id);

    const recipeDetails$: Subscription = this.recipeService
      .fetchById(recipeId)
      .subscribe(data => this.recipe = data);

    this.subscriptions.push(recipeDetails$)
    this.subscriptions.push(route$)
  }

  public toggleIsCookingHandler() {
    this.isCooking = !this.isCooking;
  }

  public onRateHandler(event: any) {
    if (this.recipe == null || this.recipe.publisherUsername === undefined) {
      this.notificationService.showError("Something went wrong! Please try again.")
      return
    }
    const username = this.authService.getLoggedInUsername();
    if (username === null) {
      this.notificationService.showError('Access Denied! Please login to continue!');
      this.router.navigateByUrl('/auth/login');
      return;
    }

    this.userService
      .fetchByUsername(username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (!response.ok || response.body == null) {
          this.notificationService.showError("Something went wrong! Please try again.")
          return;
        }

        const rating: IRate = {
          rateValue: event.newValue,
          user: response.body,
          recipe: this.recipe
        }

        this.recipeService
          .rate(rating)
          .subscribe(data => {
            this.recipe = data.recipe
            this.notificationService.showSucces(`You have rated ${this.recipe?.name} with ${rating.rateValue} stars successfully.`);
          }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
      },
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
  }

  deleteRecipeHandler() {
    console.log(this.recipe);
    if (this.recipe == null) {
      this.notificationService.showError('Something went wrong on our side! Please try again later.');
      return;
    }
    this.recipeService.deleteById(this.recipe?.id)
      .subscribe((data: IRecipe) => {
        this.notificationService.showSucces(`Recipe ${data.name} was deleted.`);
        this.router.navigateByUrl('/recipe/all');
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.massage));
  }

  public ngOnInit(): void {
    this.loadRecipe();
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
