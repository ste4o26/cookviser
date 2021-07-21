import { Component, OnInit } from '@angular/core';
import { Observable, pipe } from 'rxjs';
import { map, tap } from 'rxjs/operators'

import { CuisineService } from '../recipe/service/cuisine.service';
import { RecipeService } from '../recipe/service/recipe.service';

import { IRecipeCard } from '../recipe/interface/recipe-card.interface';
import { ICuisine } from '../recipe/interface/cuisine.interface';
import { IUserCard } from '../user/user-card.interface';
import { UserService } from '../user/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private recipeService: RecipeService;
  private cuisineService: CuisineService;
  private userService: UserService;

  public todaysBestRecipes: IRecipeCard[];
  public mostPopulatedCuisines: ICuisine[];
  public bestChefs: IUserCard[];

  public constructor(recipeService: RecipeService, cuisineService: CuisineService, userService: UserService) {
    this.recipeService = recipeService;
    this.cuisineService = cuisineService;
    this.userService = userService;

    this.todaysBestRecipes = [];
    this.mostPopulatedCuisines = [];
    this.bestChefs = [];
  }

  public ngOnInit(): void {
    let recipes$: Observable<IRecipeCard[]> = this.recipeService.fetchTodaysBest();
    recipes$
      .pipe(map(data => this.todaysBestRecipes = data))
      .subscribe();

    let cuisines$: Observable<ICuisine[]> = this.cuisineService.fetchFisrtsThreeMostPopulated();
    cuisines$
      .pipe(
        tap(data => data.push({ name: 'Explorer more', imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg' })),
        map(data => this.mostPopulatedCuisines = data)
      )
      .subscribe();

    let chefs$: Observable<IUserCard[]> = this.userService.fetchBestThreeChefs();
    chefs$
      .pipe(map(data => this.bestChefs = data))
      .subscribe();
  }



}
