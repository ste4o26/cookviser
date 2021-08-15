import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConstantMessages } from 'src/app/shered/constants';
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

  public constructor(private cuisineService: CuisineService,
    private notificationService: NotificationService,
    private router: Router) { }

  public selectFileHandler(event: any) {
    this.file = event.target.files[0];
  }

  public submitHandler(cuisine: ICuisine) {
    if (this.file === null) {
      this.notificationService.showError(ConstantMessages.IMAGE_REQUIRED_ERROR);
      return;
    }

    const formData = new FormData();
    formData.append('cuisineImage', this.file);
    formData.append('name', cuisine.name);

    this.cuisineService.create(formData)
      .subscribe((data: ICuisine) => {
        this.notificationService.showSucces(ConstantMessages.CUISINE_CREATED_SUCCES);
        this.router.navigateByUrl('/cuisine/all');
      }, (errorResponse: HttpErrorResponse) => this.notificationService.showError(errorResponse.error.message));
  }
}
