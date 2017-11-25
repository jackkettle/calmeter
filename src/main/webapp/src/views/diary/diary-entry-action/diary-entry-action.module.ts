import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../../_directives/shared.module';
import { DiaryEntryActionComponent } from "./diary-entry-action.component";
import { SimpleNotificationsModule } from 'angular2-notifications';
import { FoodSelectionModule } from '../../food/food-selection/food-selection.module';

@NgModule({
    declarations: [
        DiaryEntryActionComponent
    ],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        SimpleNotificationsModule,
        SharedModule,
        FoodSelectionModule
    ]
})

export class DiaryEntryActionModule { }