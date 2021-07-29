import { Component, OnDestroy, OnInit } from '@angular/core';
import { Observable, pipe, Subscription } from 'rxjs';
import { map, tap } from 'rxjs/operators'

import { CuisineService } from '../recipe/service/cuisine.service';
import { RecipeService } from '../recipe/service/recipe.service';

import { IRecipeCard } from '../recipe/interface/recipe-card.interface';
import { ICuisine } from '../recipe/interface/cuisine.interface';
import { IUserCard } from '../user/user-card.interface';
import { UserService } from '../user/user.service';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private recipeService: RecipeService;
  private cuisineService: CuisineService;
  private userService: UserService;
  private subscriptions: Subscription[];

  public todaysBestRecipes: IRecipeCard[] | null;
  public mostPopulatedCuisines: ICuisine[] | null;
  public bestChefs: IUserCard[] | null;

  public constructor(recipeService: RecipeService, cuisineService: CuisineService, userService: UserService) {
    this.recipeService = recipeService;
    this.cuisineService = cuisineService;
    this.userService = userService;
    this.subscriptions = [];

    this.todaysBestRecipes = [];
    this.mostPopulatedCuisines = [];
    this.bestChefs = [];
  }


  private recipeSubsriber(): void {
    const recipes$: Subscription = this.recipeService
      .fetchTodaysBest()
      .subscribe((response: HttpResponse<IRecipeCard[]>) => {
        this.todaysBestRecipes = response.body;
      }, (errorResponse: HttpErrorResponse) => {
        console.log(errorResponse.error.message);
      });

    this.subscriptions.push(recipes$);
  }

  private cuisineSubscriber(): void {
    const cuisines$: Subscription = this.cuisineService
      .fetchFisrtsThreeMostPopulated()
      // .fetchAll()
      .subscribe((response: ICuisine[]) => {
        console.log(response);
        const data = response;

        // @ts-ignore: Object is possibly 'null'.
        data.push(
          {
            id: '',
            name: 'Explorer more',
            imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
          }
        );

        this.mostPopulatedCuisines = data;
      }, (responseError: HttpErrorResponse) => {
        console.log(responseError.error.message);
      });

    this.subscriptions.push(cuisines$);
  }

  private userSubscriber(): void {
    const chefs$: Subscription = this.userService.fetchBestThreeChefs()
      .subscribe((repsonse: HttpResponse<IUserCard[]>) => {
        this.bestChefs = repsonse.body;
      }, (responseError: HttpErrorResponse) => {
        console.log(responseError.error.message);
      });

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
