import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { OnCreate } from '../../../_directives/';
import { AddDiaryEntryComponent } from "./add-diary-entry.component";

@NgModule({
    declarations: [
        AddDiaryEntryComponent,
        OnCreate],
    imports: [
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        NgxDatatableModule]
})

export class AddDiaryEntryModule { }