import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
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

  constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/cuisine');
  }

  fetchAll(): Observable<ICuisine[]> {
    // return this.http.get<ICuisine[]>(`${this.host}/getAll`);
    return observableOf(this.cuisines);
  }

  fetchFisrtsThreeMostPopulated(): Observable<ICuisine[]>{
    // return this.http.get<ICuisine[]>(`${this.host}/getFirstThreeMostPopulated`);
    return observableOf(this.cuisines.slice(0, 3))
  }
}
