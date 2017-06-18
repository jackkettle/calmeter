import { NgModule } from '@angular/core';
import { RouterModule } from "@angular/router";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegistrationComponent } from './registration.component';
import { RegistrationService } from '../../_services/registration.service';


@NgModule({
    imports: [FormsModule, ReactiveFormsModule, RouterModule],
    exports: [],
    declarations: [RegistrationComponent],
    providers: [RegistrationService],
})
export class RegistrationModule { }
