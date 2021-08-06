import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { AuthGuard } from './guard/auth.guard';
import { HomeComponent } from './home/home.component';
import { CreateRecipeComponent } from './recipe/create-recipe/create-recipe.component';
import { CuisineListComponent } from './recipe/cuisine-list/cuisine-list.component';
import { RecipeDetailsComponent } from './recipe/recipe-details/recipe-details.component';
import { RecipeListComponent } from './recipe/recipe-list/recipe-list.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'user/profile/:username', component: UserProfileComponent, canActivate: [AuthGuard] },
  { path: 'recipe/add', component: CreateRecipeComponent, canActivate: [AuthGuard] },
  { path: 'recipe/details/:id', component: RecipeDetailsComponent, canActivate: [AuthGuard] },
  { path: 'recipe/all', component: RecipeListComponent, canActivate: [AuthGuard] },
  { path: 'recipe/all/:cuisine', component: RecipeListComponent, canActivate: [AuthGuard] },
  { path: 'cuisine/all', component: CuisineListComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
