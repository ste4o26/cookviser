import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { IUserRegister } from './interface/user-register.interface';
import { environment } from 'src/environments/environment';
import { IUserLogin } from './interface/user-login.interface';
import { Observable } from 'rxjs';
import { JwtHelperService } from '@auth0/angular-jwt';
import { IUser } from './interface/user.interface';
import { Router } from '@angular/router';
import { AuthGuard } from '../guard/auth.guard';
import { NotificationService } from '../shered/notification.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private token: string = '';
  private loggedInUsername: string = '';
  private jwtHelperService: JwtHelperService;

  public host: string = environment.domain.concat('/auth');

  public constructor(private http: HttpClient) {
    this.jwtHelperService = new JwtHelperService();
  }

  public setUserToken(token: string): void {
    this.token = token;
    localStorage.setItem('jwtToken', this.token)
  }

  public setLoggedInUsername(loggedInUsername: string) {
    this.loggedInUsername = loggedInUsername;
    localStorage.setItem('loggedInUsername', this.loggedInUsername);
  }

  public loadToken(): void {
    const tempToken = localStorage.getItem('jwtToken');
    if(tempToken === null) {
      this.token = '';
      return;
    }

    this.token = tempToken;
  }

  public getToken(): string {
    return this.token;
  }

  public getLoggedInUsername(): string {
    return this.loggedInUsername;
  }

  public isLoggedIn(): boolean {
    this.loadToken();
    if (this.token == null || this.token === '') {
      this.logout();
      return false;
    }

    const subject: string = this.jwtHelperService.decodeToken(this.token).sub;
    if ((subject != null || subject !== '') && !this.jwtHelperService.isTokenExpired(this.token)) {
      this.loggedInUsername = subject;
      return true;
    }

    return false;
  }

  public register(user: IUserRegister): Observable<HttpResponse<IUser>> {
    return this.http.post<HttpResponse<IUser>>(`${this.host}/register`, user);
  }

  public login(user: IUserLogin): Observable<HttpResponse<IUser>> {
    return this.http.post<IUser>(`${this.host}/login`, user, { observe: 'response' });
  }

  public logout(): void {
    this.token = '';
    localStorage.removeItem('jwtToken')

    this.loggedInUsername = '';
    localStorage.removeItem('loggedInUsername');
  }
}
