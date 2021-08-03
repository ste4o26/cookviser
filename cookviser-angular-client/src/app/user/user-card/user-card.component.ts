import { Component, Input, OnInit } from '@angular/core';
import { IUserCard } from '../user-card.interface';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.component.html',
  styleUrls: ['./user-card.component.css']
})
export class UserCardComponent {

  @Input()
  public user: IUserCard;

  public constructor() { 
    this.user = {
      username: '',
      profileImageUrl: '',
      description: '',
      overallRating: 0
    }
  }

  public mouseEnterHandler(articleElement: HTMLElement): void {
    articleElement.classList.remove("hidden");
  }

  public mouseLeaveHandler(articleElement: HTMLElement): void {
    articleElement.classList.add("hidden");
  }
}
