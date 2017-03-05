import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { AddFoodComponent } from './add-food.component';
import { FormsModule } from '@angular/forms';
import { SelectModule } from 'ng-select';


@NgModule({
    declarations: [AddFoodComponent],
    imports: [
        BrowserModule,
        FormsModule,
        SelectModule
    ]
})
export class AddFoodModule { }
