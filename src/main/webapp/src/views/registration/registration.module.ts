import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegistrationComponent } from './registration.component';
import { RegistrationService } from '../../_services';


@NgModule({
    imports: [FormsModule, ReactiveFormsModule, RouterModule, CommonModule],
    exports: [],
    declarations: [RegistrationComponent],
    providers: [RegistrationService],
})
export class RegistrationModule { }
