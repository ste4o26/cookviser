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

  public constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/cuisine');
  }

  public fetchAll(): Observable<ICuisine[]> {
    return this.http.get<ICuisine[]>(`${this.host}/all`);
  }

  public fetchFisrtsThreeMostPopulated(): Observable<ICuisine[]> {
    return this.http.get<ICuisine[]>(`${this.host}/first-four-most-populated`);
  }
}
