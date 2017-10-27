import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { FoodActionComponent } from './food-action.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SelectModule } from 'ng2-select';


@NgModule({
    declarations: [FoodActionComponent],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        SelectModule
    ]
})
export class FoodActionModule { }
