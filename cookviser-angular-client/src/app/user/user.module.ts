import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCardComponent } from './user-card/user-card.component';
import { AppRoutingModule } from '../app-routing.module';
import { UserProfileComponent } from './user-profile/user-profile.component';

@NgModule({
  declarations: [
    UserCardComponent,
    UserProfileComponent
  ],
  imports: [
    CommonModule,
    AppRoutingModule
  ],
  exports: [
    UserCardComponent
  ]
})

export class UserModule { }
