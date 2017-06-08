import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';  
import { FormsModule }   from '@angular/forms';
import { LoginComponent } from './login.component';

@NgModule({
    imports: [FormsModule, CommonModule],
    exports: [],
    declarations: [LoginComponent],
    providers: [],
})
export class LoginModule { }
