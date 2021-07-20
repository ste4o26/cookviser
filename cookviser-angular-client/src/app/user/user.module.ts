import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UserCardComponent } from './user-card/user-card.component';
import { AppRoutingModule } from '../app-routing.module';

@NgModule({
  declarations: [
    UserCardComponent
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
