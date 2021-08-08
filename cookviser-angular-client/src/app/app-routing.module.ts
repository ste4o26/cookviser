import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { AdminGuard } from './guard/admin.guard';
import { AuthGuard } from './guard/auth.guard';
import { UserProfileGuard } from './guard/user-profile.guard';
import { HomeComponent } from './home/home.component';
import { CreateRecipeComponent } from './recipe/create-recipe/create-recipe.component';
import { CuisineListComponent } from './recipe/cuisine-list/cuisine-list.component';
import { RecipeDetailsComponent } from './recipe/recipe-details/recipe-details.component';
import { RecipeListComponent } from './recipe/recipe-list/recipe-list.component';
import { UserProfileComponent } from './user/user-profile/user-profile.component';
import { UsersListComponent } from './user/users-list/users-list.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'user/profile/:username', component: UserProfileComponent, canActivate: [AuthGuard, UserProfileGuard] },
  { path: 'user/all', component: UsersListComponent, canActivate: [AuthGuard, AdminGuard] },
  { path: 'recipe/add', component: CreateRecipeComponent, canActivate: [AuthGuard] },
  { path: 'recipe/details/:id', component: RecipeDetailsComponent },
  { path: 'recipe/all', component: RecipeListComponent },
  { path: 'recipe/all/:cuisine', component: RecipeListComponent },
  { path: 'cuisine/all', component: CuisineListComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
