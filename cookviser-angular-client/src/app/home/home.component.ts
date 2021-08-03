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

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[];

  public todaysBestRecipes: any = [];
  public mostPopulatedCuisines: ICuisine[] = [];
  public bestChefs: IUserCard[] = [];

  public constructor(private recipeService: RecipeService, private cuisineService: CuisineService, private userService: UserService) {
    this.recipeService = recipeService;
    this.cuisineService = cuisineService;
    this.userService = userService;
    this.subscriptions = [];
  }


  private recipeSubsriber(): void {
    const recipes$: Subscription = this.recipeService
      .fetchTodaysBest()
      .pipe(map((data: IRecipeCard[]) => { this.todaysBestRecipes = data; }))
      .subscribe();

    this.subscriptions.push(recipes$);
  }

  private cuisineSubscriber(): void {
    const cuisines$: Subscription = this.cuisineService
      .fetchFisrtsThreeMostPopulated()
      .subscribe((response: ICuisine[]) => {
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
    const chefs$: Subscription = this.userService
      .fetchBestThreeChefs()
      .subscribe((data: any) => {
        console.log("user data: ", data)
        this.bestChefs = data;
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
