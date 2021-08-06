import { Component, DoCheck, OnChanges } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements DoCheck {
  public username: string = '';
  public isLogedIn: boolean = false;

  public constructor(private authService: AuthService) { }

  public ngDoCheck(): void {
    this.isLogedIn = this.authService.isLoggedIn();
    this.username = this.authService.getLoggedInUsername();
  }

  public clickHandler(): void {
    this.authService.logout();
    this.isLogedIn = this.authService.isLoggedIn();
  }
}
