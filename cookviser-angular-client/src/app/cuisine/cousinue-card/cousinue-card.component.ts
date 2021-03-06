import { Component, Input } from '@angular/core';
import { ICuisine } from '../interface/cuisine.interface';

@Component({
  selector: 'app-cousinue-card',
  templateUrl: './cousinue-card.component.html',
  styleUrls: ['./cousinue-card.component.css']
})
export class CousinueCardComponent {

  @Input()
  public cuisine: ICuisine = {id: '', name: '', imageThumbnailUrl: '' };

  public constructor() { }

  public mouseEnterHandler(headerElement: HTMLElement): void {
    headerElement.classList.remove("hidden");
  }

  public mouseLeaveHandler(headerElement: HTMLElement): void {
    headerElement.classList.add("hidden");
  }
}
