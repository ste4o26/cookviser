import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { IRecipeCard } from '../interface/recipe-card.interface'
import { Observable } from 'rxjs';
import { of as observableOf } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class RecipeService {
  private http: HttpClient;
  private host: string;
  private recipes: IRecipeCard[] = [
    {
      id:'first',
      title: 'Recipe 1',
      publisher: 'Publisher 1',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      id:'second',
      title: 'Recipe 2',
      publisher: 'Publisher 2',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      id:'third',
      title: 'Recipe 3',
      publisher: 'Publisher 3',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      id:'fourht',
      title: 'Recipe 4',
      publisher: 'Publisher 4',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      id:'fifth',
      title: 'Recipe 4',
      publisher: 'Publisher 4',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      id:'sixth',
      title: 'Recipe 4',
      publisher: 'Publisher 4',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    }
  ];

  constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/recipe');
  }

  fetchTodaysBest(): Observable<IRecipeCard[]> {
    return observableOf(this.recipes.slice(0, 4))
    // return this.http.get<IRecipeCard[]>(`${this.host}/getTodaysBest`);
  }

  fetchAll(): Observable<IRecipeCard[]> {
    return observableOf(this.recipes);
    // return this.http.get<IRecipeCard[]>(`${this.host}/getAll`);
  }

}
