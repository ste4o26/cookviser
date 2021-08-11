import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from '../app-routing.module';
import { RatingModule } from 'ng-starrating';

import { RecipeCardComponent } from './recipe-card/recipe-card.component';
import { CousinueCardComponent } from './cousinue-card/cousinue-card.component';
import { RecipeDetailsComponent } from './recipe-details/recipe-details.component';
import { CreateRecipeComponent } from './create-recipe/create-recipe.component';
import { CuisineListComponent } from './cuisine-list/cuisine-list.component';
import { RecipeListComponent } from './recipe-list/recipe-list.component';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { NgxSpinnerModule } from 'ngx-spinner';
import { CreateCuisineComponent } from './create-cuisine/create-cuisine.component';

@NgModule({
  declarations: [
    RecipeCardComponent,
    CousinueCardComponent,
    RecipeDetailsComponent,
    CreateRecipeComponent,
    CuisineListComponent,
    RecipeListComponent,
    CreateCuisineComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RatingModule,
    InfiniteScrollModule,
    NgxSpinnerModule
  ],
  exports: [
    RecipeCardComponent,
    CousinueCardComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class RecipeModule { }
