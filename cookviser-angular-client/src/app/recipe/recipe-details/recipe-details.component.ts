import { HttpResponse } from '@angular/common/http';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router'

import { Observable, Subscription } from 'rxjs';
import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/auth/auth.service';
import { IUser } from 'src/app/auth/interface/user.interface';
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
    private authService: AuthService) {
  }

  public toggleIsCookingHandler() {
    this.isCooking = !this.isCooking;
  }

  public onRateHandler(event: any) {
    if (this.recipe == null || this.recipe.publisherUsername === undefined) {
      console.log("log some error to the user")
      return
    }
    const username = this.authService.getLoggedInUsername();
    if(username === null) {
      console.log('Access Denied');
      return;
    }

    this.userService
      .fetchByUsername(username)
      .subscribe((response: HttpResponse<IUser>) => {
        if (!response.ok || response.body == null) {
          console.log("log some error to the user")
          return;
        }

        const rating: IRate = {
          rateValue: event.newValue,
          user: response.body,
          recipe: this.recipe
        }

        this.recipeService
          .rate(rating)
          .subscribe(data => this.recipe = data.recipe);
      });


  }

  public ngOnInit(): void {
    let recipeId: string = '';
    const route$: Subscription = this.activatedRoute.params
      .subscribe(params => recipeId = params.id);

    const recipeDetails$: Subscription = this.recipeService
      .fetchById(recipeId)
      .subscribe(data => this.recipe = data);

    this.subscriptions.push()
    this.subscriptions.push(route$);
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }
}
