import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from '../app-routing.module';
import { RatingModule } from 'ng-starrating';

import { RecipeCardComponent } from './recipe-card/recipe-card.component';
import { RecipeDetailsComponent } from './recipe-details/recipe-details.component';
import { CreateRecipeComponent } from './create-recipe/create-recipe.component';
import { RecipeListComponent } from './recipe-list/recipe-list.component';
import { CuisineModule } from '../cuisine/cuisine.module';
import { SheredModule } from '../shered/shered.module';

@NgModule({
  declarations: [
    RecipeCardComponent,
    RecipeDetailsComponent,
    CreateRecipeComponent,
    RecipeListComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    RatingModule,
    CuisineModule,
    SheredModule
  ],
  exports: [RecipeCardComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class RecipeModule { }
