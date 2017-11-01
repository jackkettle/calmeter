import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { OnCreate } from '../../../_directives/';
import { DiaryEntryActionComponent } from "./diary-entry-action.component";
import { SimpleNotificationsModule } from 'angular2-notifications';


@NgModule({
    declarations: [
        DiaryEntryActionComponent,
        OnCreate],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        NgxDatatableModule,
        SimpleNotificationsModule]
})

export class DiaryEntryActionModule { }