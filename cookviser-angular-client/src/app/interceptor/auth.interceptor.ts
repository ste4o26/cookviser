import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../auth/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  private authService: AuthService;

  public constructor(authService: AuthService) { 
    this.authService = authService;
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (request.url.includes(`${this.authService.host}/login`)) {
      return next.handle(request);
    }

    if (request.url.includes(`${this.authService.host}/register`)) {
      return next.handle(request);
    }

    this.authService.loadToken();
    const token: string | null = this.authService.getToken();

    const clonedRequest: HttpRequest<any> = request.clone({setHeaders: {Authorization: `Bearer ${token}`}});
    return next.handle(clonedRequest);
  }
}
