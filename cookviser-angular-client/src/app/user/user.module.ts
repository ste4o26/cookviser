import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCardComponent } from './user-card/user-card.component';
import { AppRoutingModule } from '../app-routing.module';
import { UserProfileComponent } from './user-profile/user-profile.component';
import { FormsModule } from '@angular/forms';
import { RecipeModule } from '../recipe/recipe.module';
import { UsersListComponent } from './users-list/users-list.component';

@NgModule({
  declarations: [
    UserCardComponent,
    UserProfileComponent,
    UsersListComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule,
    FormsModule,
    RecipeModule
  ],
  exports: [
    UserCardComponent
  ]
})

export class UserModule { }
