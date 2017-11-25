import { NgModule } from '@angular/core';
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../_directives/shared.module';
import { MealActionComponent } from './meal-action.component';
import { FoodSelectionModule } from '../../food/food-selection/food-selection.module';
import { ControlMessagesModule } from '../../common/control-messages/control-messages.module';

@NgModule({
    declarations: [MealActionComponent],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        FoodSelectionModule,
        ControlMessagesModule
    ],
})
export class MealActionModule { }
