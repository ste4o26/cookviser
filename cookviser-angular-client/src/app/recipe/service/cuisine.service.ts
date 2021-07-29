import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of as observableOf } from 'rxjs';
import { ICuisine } from '../interface/cuisine.interface';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CuisineService {
  private http: HttpClient;
  private host: string;
  private cuisines = [
    {
      name: 'Cuisine 1',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      name: 'Cuisine 2',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      name: 'Cuisine 3',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      name: 'Cuisine 4',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
    {
      name: 'Cuisine 5',
      imageThumbnailUrl: 'https://iamafoodblog.b-cdn.net/wp-content/uploads/2020/05/homemade-birria-tacos-recipe-3273w.jpg'
    },
  ];

  public constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/cuisine');
  }

  public fetchAll(): Observable<ICuisine[]> {
    return this.http.get<ICuisine[]>(`${this.host}/all`);
    // return observableOf(this.cuisines);
  }

  public fetchFisrtsThreeMostPopulated(): Observable<ICuisine[]> {
    return this.http.get<ICuisine[]>(`${this.host}/firstThreeMostPopulated`);
    // return observableOf(this.cuisines.slice(0, 3))
  }
}
