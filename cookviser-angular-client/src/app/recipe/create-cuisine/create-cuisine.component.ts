import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationService } from 'src/app/shered/notification.service';
import { ICuisine } from '../interface/cuisine.interface';
import { CuisineService } from '../service/cuisine.service';

@Component({
  selector: 'app-create-cuisine',
  templateUrl: './create-cuisine.component.html',
  styleUrls: ['./create-cuisine.component.css']
})
export class CreateCuisineComponent {
  private file: File | null = null;

  constructor(private cuisineService: CuisineService,
    private notificationService: NotificationService,
    private router: Router) { }

  selectFileHandler(event: any) {
    this.file = event.target.files[0];
  }

  submitHandler(cuisine: ICuisine) {
    if (this.file === null) {
      this.notificationService.showError('Image is required!');
      return;
    }

    const formData = new FormData();
    formData.append('cuisineImage', this.file);
    formData.append('name', cuisine.name);

    this.cuisineService.create(formData)
      .subscribe((data: ICuisine) => {
        this.notificationService.showSucces(`Cuisine ${data.name} is created.`);
        this.router.navigateByUrl('/cuisine/all');
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
  }
}
