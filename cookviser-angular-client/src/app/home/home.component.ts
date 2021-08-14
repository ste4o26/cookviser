import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, pipe, Subscription } from 'rxjs';
import { map, tap } from 'rxjs/operators'

import { CuisineService } from '../recipe/service/cuisine.service';
import { RecipeService } from '../recipe/service/recipe.service';

import { IRecipeCard } from '../recipe/interface/recipe-card.interface';
import { ICuisine } from '../recipe/interface/cuisine.interface';
import { IUserCard } from '../user/user-card.interface';
import { UserService } from '../user/user.service';
import { HttpErrorResponse } from '@angular/common/http';
import { NotificationService } from '../shered/notification.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  public todaysBestRecipes: any = [];
  public mostPopulatedCuisines: ICuisine[] = [];
  public bestChefs: IUserCard[] = [];

  public constructor(private recipeService: RecipeService,
    private cuisineService: CuisineService,
    private userService: UserService,
    private notificationService: NotificationService) { }


  private recipeSubsriber(): void {
    const recipes$: Subscription = this.recipeService
      .fetchTodaysBest()
      .subscribe((data: IRecipeCard[]) => this.todaysBestRecipes = data,
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(recipes$);
  }

  private cuisineSubscriber(): void {
    const cuisines$: Subscription = this.cuisineService
      .fetchFisrtsThreeMostPopulated()
      .subscribe((data: ICuisine[]) => this.mostPopulatedCuisines = data,
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(cuisines$);
  }

  private userSubscriber(): void {
    const chefs$: Subscription = this.userService
      .fetchBestThreeChefs()
      .subscribe((data: IUserCard[]) => {
        this.bestChefs = data;
        console.log(data);
      },
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(chefs$);
  }

  public ngOnInit(): void {
    this.recipeSubsriber();
    this.cuisineSubscriber();
    this.userSubscriber();
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
