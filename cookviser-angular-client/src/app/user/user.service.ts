import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of as observableOf } from 'rxjs';
import { environment } from 'src/environments/environment';
import { IUserCard } from './user-card.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private http: HttpClient;
  private host: string;

  private users: IUserCard[] = [
    {
      username: 'User 1',
      overallRating: 4.8,
      profileImageUrl: 'https://cdn4.vectorstock.com/i/1000x1000/92/93/chef-profile-avatar-icon-vector-10179293.jpg',
      description: 'User 1 description'
    },
    {
      username: 'User 2',
      overallRating: 4.4,
      profileImageUrl: 'https://cdn4.vectorstock.com/i/1000x1000/92/93/chef-profile-avatar-icon-vector-10179293.jpg',
      description: 'User 2 description'
    },
    {
      username: 'User 3',
      overallRating: 4.3,
      profileImageUrl: 'https://cdn4.vectorstock.com/i/1000x1000/92/93/chef-profile-avatar-icon-vector-10179293.jpg',
      description: 'User 3 description'
    },
    {
      username: 'User 4',
      overallRating: 4.1,
      profileImageUrl: 'https://cdn4.vectorstock.com/i/1000x1000/92/93/chef-profile-avatar-icon-vector-10179293.jpg',
      description: 'User 4 description'
    },
    {
      username: 'User 5',
      overallRating: 4,
      profileImageUrl: 'https://cdn4.vectorstock.com/i/1000x1000/92/93/chef-profile-avatar-icon-vector-10179293.jpg',
      description: 'User 5 description'
    },
  ];

  public constructor(http: HttpClient) {
    this.http = http;
    this.host = environment.domain.concat('/user');
  }

  public fetchAll(): Observable<IUserCard[]> {
    return observableOf(this.users);
    // return this.http.get<IUserCard[]>(`${this.host}/getAll`);
  }

  public fetchBestThreeChefs(): Observable<IUserCard[]> {
    return observableOf(this.users.slice(0, 3));
    // return this.http.get<IUserCard[]>(`${this.host}/getBestThreeChefs`);
  }
}
