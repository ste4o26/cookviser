import { Injectable } from '@angular/core';
import { NotificationsService } from 'angular2-notifications';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  private options = {
  position: ['bottom', 'right'],
    timeOut: 3000,
    pauseOnHover: true,
    animate: 'fade',
    showProgressBar: true
  }

  constructor(private angular2NotificationsService: NotificationsService) { }

  public showSucces(message: string): void {
    this.angular2NotificationsService.success('Succcess', message, this.options);
  }

  public showError(message: string): void {
    this.angular2NotificationsService.error('Error', message, this.options);
  }
}
