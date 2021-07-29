import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegisterComponent } from './auth/register/register.component';
import { HomeComponent } from './home/home.component';
import { CreateRecipeComponent } from './recipe/create-recipe/create-recipe.component';
import { TestComponent } from './recipe/test/test.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'auth/login', component: LoginComponent },
  { path: 'auth/register', component: RegisterComponent },
  { path: 'recipe/add', component: CreateRecipeComponent },
  { path: 'test', component: TestComponent },
  // {path: 'auth/logout', component: }
  // {path: 'user/profile', component: UserProfileComponent}
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})

export class AppRoutingModule { }
