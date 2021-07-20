import { Component, Input, OnInit } from '@angular/core';
import { IRecipeCard } from '../recipe-card.interface';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrls: ['./recipe-card.component.css']
})

export class RecipeCardComponent implements OnInit {

  // TODO find a better way of doing this initiallization eg creating an implementation of this interface!
  @Input()
  public recipe: IRecipeCard = {title: '', publisher: '', imageThumbnail: ''};

  constructor() {
  }

  ngOnInit(): void {

  }
}
