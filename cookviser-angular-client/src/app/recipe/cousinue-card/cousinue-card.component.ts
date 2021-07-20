import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-cousinue-card',
  templateUrl: './cousinue-card.component.html',
  styleUrls: ['./cousinue-card.component.css']
})
export class CousinueCardComponent {

  constructor() { }

  mouseEnterHandler(headerElement: HTMLElement): void {
    headerElement.classList.remove("hidden");
  }

  mouseLeaveHandler(headerElement: HTMLElement): void {
    headerElement.classList.add("hidden");
  }
}
