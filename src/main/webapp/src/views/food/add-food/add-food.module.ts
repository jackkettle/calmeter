import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { AddFoodComponent } from './add-food.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SelectModule } from 'ng-select';


@NgModule({
    declarations: [AddFoodComponent],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        SelectModule
    ]
})
export class AddFoodModule { }
