import { Component, OnInit } from '@angular/core';
import { Observable, pipe } from 'rxjs';
import { map, tap } from 'rxjs/operators'
import { IRecipeCard } from '../recipe/recipe-card.interface';
import { RecipeService } from '../recipe/recipe.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  private recipeService: RecipeService;
  public todaysBestRecipes: IRecipeCard[];

  constructor(recipeService: RecipeService) {
    this.recipeService = recipeService;
    this.todaysBestRecipes = [];
  }

  ngOnInit(): void {
    let observable: Observable<IRecipeCard[]> = this.recipeService.fetchTodaysBest();
    observable
      .pipe(map(data => this.todaysBestRecipes = data))
      .subscribe();
  }

}
