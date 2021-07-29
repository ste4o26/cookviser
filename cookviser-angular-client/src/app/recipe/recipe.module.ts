import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from '../app-routing.module';

import { RecipeCardComponent } from './recipe-card/recipe-card.component';
import { CousinueCardComponent } from './cousinue-card/cousinue-card.component';
import { RecipeDetailsComponent } from './recipe-details/recipe-details.component';
import { CreateRecipeComponent } from './create-recipe/create-recipe.component';
import { TestComponent } from './test/test.component';

@NgModule({
  declarations: [
    RecipeCardComponent,
    CousinueCardComponent,
    RecipeDetailsComponent,
    CreateRecipeComponent,
    TestComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  exports: [
    RecipeCardComponent,
    CousinueCardComponent
  ]
})

export class RecipeModule { }
