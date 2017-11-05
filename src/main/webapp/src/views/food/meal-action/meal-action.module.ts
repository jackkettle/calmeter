import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { MealActionComponent } from './meal-action.component';
import { FoodSelectionModule } from '../../food/food-selection/food-selection.module';

@NgModule({
    declarations: [MealActionComponent],
    imports: [
        BrowserModule,
        FoodSelectionModule
    ],
})
export class MealActionModule { }
