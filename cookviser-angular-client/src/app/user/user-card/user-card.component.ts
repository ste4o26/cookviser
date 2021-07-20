import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {

  constructor() { }

  mouseEnterHandler(articleElement: HTMLElement): void{
    articleElement.classList.remove("hidden");
  }

  mouseLeaveHandler(articleElement: HTMLElement): void{
    articleElement.classList.add("hidden");
  }
}
