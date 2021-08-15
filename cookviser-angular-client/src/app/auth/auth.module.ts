import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from '../app-routing.module';
import { SheredModule } from '../shered/shered.module';

@NgModule({
  declarations: [
    RegisterComponent,
    LoginComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    AppRoutingModule,
    SheredModule
  ]
})
export class AuthModule { }
