import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IRecipeCard } from './recipe-card.interface'
import { Observable } from 'rxjs';
import { of as observableOf } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private http: HttpClient;
  private host: string;

  constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain;
  }

  fetchTodaysBest(): Observable<IRecipeCard[]> {
    const recipes: IRecipeCard[] = [
      {
        title: 'Recipe 1',
        publisher: 'Publisher 1',
        imageThumbnail: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
      },
      {
        title: 'Recipe 2',
        publisher: 'Publisher 2',
        imageThumbnail: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
      },
      {
        title: 'Recipe 3',
        publisher: 'Publisher 3',
        imageThumbnail: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
      },
      {
        title: 'Recipe 4',
        publisher: 'Publisher 4',
        imageThumbnail: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
      }
    ];

    return observableOf(recipes)
    // return this.http.get<IRecipeCard[]>(`${this.host}/recipe/todaysBest`);
  }

}
