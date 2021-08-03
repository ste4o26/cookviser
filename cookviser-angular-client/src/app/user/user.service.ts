import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of as observableOf } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUser } from '../auth/interface/user.interface';
import { IUserCard } from './user-card.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http: HttpClient;
  private host: string;

  public constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/user');
  }

  public fetchAll(): Observable<IUser[]> {
    // return observableOf(this.users);
    return this.http.get<IUser[]>(`${this.host}/all`);
  }

  public fetchBestThreeChefs(): Observable<IUserCard[]> {
    // return observableOf(this.users.slice(0, 3));
    return this.http.get<IUserCard[]>(`${this.host}/best-three`);
  }

  public fetchByUsername(username: string): Observable<HttpResponse<IUser>> {
    let params: HttpParams = new HttpParams().set('username', username);
    return this.http.get<IUser>(`${this.host}/by-username`, { params, observe: 'response'});
  }
}
