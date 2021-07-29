import { Component, Input, OnInit } from '@angular/core';
import { ICuisine } from '../interface/cuisine.interface';

@Component({
  selector: 'app-cousinue-card',
  templateUrl: './cousinue-card.component.html',
  styleUrls: ['./cousinue-card.component.css']
})
export class CousinueCardComponent {

  @Input()
  public cuisine: ICuisine = {id: '', name: '', imageThumbnailUrl: '' };

  constructor() { }

  mouseEnterHandler(headerElement: HTMLElement): void {
    headerElement.classList.remove("hidden");
  }

  mouseLeaveHandler(headerElement: HTMLElement): void {
    headerElement.classList.add("hidden");
  }
}
