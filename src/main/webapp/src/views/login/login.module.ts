import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { RouterModule } from "@angular/router";
import { FormsModule }   from '@angular/forms';
import { LoginComponent } from './login.component';

@NgModule({
    imports: [FormsModule, CommonModule, RouterModule],
    exports: [],
    declarations: [LoginComponent],
    providers: [],
})
export class LoginModule { }
