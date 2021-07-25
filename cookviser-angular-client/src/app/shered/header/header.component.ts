import { Component, DoCheck, OnChanges, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements DoCheck {
  private authService: AuthService;
  public username: string | null;
  public isLogedIn: boolean;

  public constructor(authService: AuthService) {
    this.authService = authService
    this.isLogedIn = false;
    this.username = null;
  }

  public ngDoCheck(): void {
    this.isLogedIn = this.authService.isLoggedIn();
    this.username = this.authService.getLoggedInUsername();
  }

  public clickHandler(): void {
    this.authService.logout();
    this.isLogedIn = this.authService.isLoggedIn();
  }
}
