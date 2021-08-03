import { Component, Input } from '@angular/core';
import { IRecipeCard } from '../interface/recipe-card.interface';

@Component({
  selector: 'app-recipe-card',
  templateUrl: './recipe-card.component.html',
  styleUrls: ['./recipe-card.component.css']
})

export class RecipeCardComponent {

  @Input()
  public recipe: IRecipeCard = {id: '', name: '', publisherUsername: '', recipeThumbnail: ''};

  constructor() {
  }
}
