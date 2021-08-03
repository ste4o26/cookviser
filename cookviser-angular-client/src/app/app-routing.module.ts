import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home.component';
import { CreateRecipeComponent } from './recipe/create-recipe/create-recipe.component';
import { CuisineListComponent } from './recipe/cuisine-list/cuisine-list.component';
import { RecipeDetailsComponent } from './recipe/recipe-details/recipe-details.component';
import { RecipeListComponent } from './recipe/recipe-list/recipe-list.component';
import { TestComponent } from './recipe/test/test.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'user/profile/:username', component: UserProfileComponent },
  { path: 'recipe/add', component: CreateRecipeComponent },
  { path: 'recipe/details/:id', component: RecipeDetailsComponent },
  { path: 'recipe/all', component: RecipeListComponent },
  { path: 'recipe/all/:cuisine', component: RecipeListComponent },
  { path: 'cuisine/all', component: CuisineListComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  // { path: 'test', component: TestComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
