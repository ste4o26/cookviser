import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CousinueCardComponent } from './cousinue-card/cousinue-card.component';
import { SheredModule } from '../shered/shered.module';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from '../app-routing.module';
import { CreateCuisineComponent } from './create-cuisine/create-cuisine.component';
import { CuisineListComponent } from './cuisine-list/cuisine-list.component';

@NgModule({
  declarations: [
    CreateCuisineComponent,
    CuisineListComponent,
    CousinueCardComponent
  ],
  imports: [
    CommonModule,
    SheredModule,
    FormsModule,
    AppRoutingModule
  ],
  exports: [CousinueCardComponent]
})
export class CuisineModule { }
