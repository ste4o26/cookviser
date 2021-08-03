import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpParams, HttpRequest, HttpResponse } from '@angular/common/http';
import { IRecipeCard } from '../interface/recipe-card.interface'
import { Observable } from 'rxjs';
import { of as observableOf } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IRecipe } from '../interface/recipe.interface';
import { IRate } from '../interface/rate.interface';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private http: HttpClient;
  private host: string;

  public constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/recipe');
  }

  public fetchTodaysBest(): Observable<IRecipeCard[]> {
    return this.http.get<IRecipeCard[]>(`${this.host}/best-four`);
  }

  public fetchAll(): Observable<IRecipeCard[]> {
    return this.http.get<IRecipeCard[]>(`${this.host}/all`);
  }

  public fetchAllCategories(): Observable<string[]> {
    return this.http.get<string[]>(`${this.host}/all-categories`);
  }

  public create(recipe: IRecipe): Observable<HttpResponse<IRecipe>> {
    return this.http.post<IRecipe>(`${this.host}/create`, recipe, { observe: 'response' });
  }

  public uploadRecipeImage(formData: FormData): Observable<HttpResponse<IRecipe>> {
    return this.http.post<IRecipe>(`${this.host}/upload-recipe-image`, formData, { observe: 'response' });
  }

  public rate(rating: IRate): Observable<IRate> {
    return this.http.post<IRate>(`${this.host}/rate`, rating);
  }

  public fetchById(recipeId: string): Observable<IRecipe> {
    const params = new HttpParams().set('recipeId', recipeId);
    return this.http.get<IRecipe>(`${this.host}/details`, { params })
  }
}
