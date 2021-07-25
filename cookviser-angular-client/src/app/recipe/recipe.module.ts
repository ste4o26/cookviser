import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RecipeCardComponent } from './recipe-card/recipe-card.component';
import { CousinueCardComponent } from './cousinue-card/cousinue-card.component';
import { AppRoutingModule } from '../app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { RecipeDetailsComponent } from './recipe-details/recipe-details.component';

@NgModule({
  declarations: [
    RecipeCardComponent,
    CousinueCardComponent,
    RecipeDetailsComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    HttpClientModule
  ],
  exports: [
    RecipeCardComponent,
    CousinueCardComponent
  ]
})

export class RecipeModule { }
