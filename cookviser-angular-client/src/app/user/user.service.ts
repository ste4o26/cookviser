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
    return this.http.get<IUser[]>(`${this.host}/all`);
  }

  public fetchBestThreeChefs(): Observable<IUserCard[]> {
    return this.http.get<IUserCard[]>(`${this.host}/best-three`);
  }

  public fetchByUsername(username: string): Observable<HttpResponse<IUser>> {
    let params: HttpParams = new HttpParams().set('username', username);
    return this.http.get<IUser>(`${this.host}/by-username`, { params, observe: 'response' });
  }

  public updateProfileImage(formData: FormData): Observable<HttpResponse<IUser>> {
    return this.http.put<IUser>(`${this.host}/update-profile-image`, formData, { observe: 'response' });
  }

  public updateProfile(user: IUser, editorUsername: string): Observable<HttpResponse<IUser>> {
    const params: HttpParams = new HttpParams().set('editorUsername', editorUsername);
    return this.http.put<IUser>(`${this.host}/update-profile`, user, { params, observe: 'response' });
  }

  public promote(username: string): Observable<HttpResponse<IUser>> {
    return this.http.put<IUser>(`${this.host}/promote`, username, { observe: 'response' });
  }

  public demote(username: string): Observable<HttpResponse<IUser>> {
    return this.http.put<IUser>(`${this.host}/demote`, username, { observe: 'response' });
  }
}
