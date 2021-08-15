import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { NotificationService } from 'src/app/shered/notification.service';
import { IRecipeCard } from '../interface/recipe-card.interface';
import { RecipeService } from '../service/recipe.service';

@Component({
  selector: 'app-recipe-list',
  templateUrl: './recipe-list.component.html',
  styleUrls: ['./recipe-list.component.css']
})
export class RecipeListComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];
  private params: any;
  private recipesCount: number = 8;

  public hasNextPage: boolean = true;
  public recipes: IRecipeCard[] = [];
  public currentHeading: string = '';

  public constructor(private activatedRout: ActivatedRoute,
    private recipeService: RecipeService,
    private notificationService: NotificationService) {
    const params$: Subscription = this.activatedRout.params.subscribe(params => this.params = params);
    this.subscriptions.push(params$);
  }

  private isExistingParamsInRoute(): boolean {
    return Object.keys(this.params).length === 0;
  }

  private nextRecipesSubscriberger = (data: IRecipeCard[]): void => {
    if (data.length == this.recipes.length) {
      this.hasNextPage = false;
    } else {
      this.hasNextPage = true;
      this.recipes = data;
      this.recipesCount += 8;
    }
  }

  private loadNextRecipes(): void {
    const recipes$: Subscription = this.recipeService
      .fetchNextRecipes(this.recipesCount)
      .subscribe(data => this.nextRecipesSubscriberger(data),
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(recipes$);
  }


  private loadNextRecipesByCuisine(): void {
    const recipes$: Subscription = this.recipeService
      .fetchNextByCuisine(this.params.cuisine, this.recipesCount)
      .subscribe(data => this.nextRecipesSubscriberger(data),
        (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));

    this.subscriptions.push(recipes$);
    this.currentHeading = this.params.cuisine;
  }

  public onScroll(): void {
    if (this.isExistingParamsInRoute()) {
      this.loadNextRecipes();
    } else {
      this.loadNextRecipesByCuisine();
    }
  }

  public ngOnInit(): void {
    if (this.isExistingParamsInRoute()) {
      this.loadNextRecipes();
      this.currentHeading = 'All'
    } else {
      this.loadNextRecipesByCuisine();
    }
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}