import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ICuisine } from '../interface/cuisine.interface';
import { CuisineService } from '../service/cuisine.service';

@Component({
  selector: 'app-cuisine-list',
  templateUrl: './cuisine-list.component.html',
  styleUrls: ['./cuisine-list.component.css']
})
export class CuisineListComponent implements OnInit, OnDestroy {
  public cuisines: ICuisine[] = [];
  public subscriptions: Subscription[] = [];

  public constructor(private cuisineService: CuisineService) { }

  public ngOnInit(): void {
    const cuisines$ = this.cuisineService
      .fetchAll()
      .subscribe(data => this.cuisines = data);

    this.subscriptions.push(cuisines$);
  }

  public ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

}
