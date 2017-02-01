import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { AddFoodComponent } from './add-food.component';
import { FormsModule } from '@angular/forms';

@NgModule({
    declarations: [AddFoodComponent],
    imports: [
        BrowserModule,
        FormsModule
    ]
})
export class AddFoodModule { }
