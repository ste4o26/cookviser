import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent {
  public isLogedIn: boolean;
// TODO is loged in should come from backend!!!
  constructor() {
    this.isLogedIn = false;
  }
}
