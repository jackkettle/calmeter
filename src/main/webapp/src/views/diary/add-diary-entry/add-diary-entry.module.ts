import { NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

import { AddDiaryEntryComponent } from "./add-diary-entry.component";

@NgModule({
    declarations: [AddDiaryEntryComponent],
    imports: [BrowserModule, NgxDatatableModule]
})

export class AddDiaryEntryModule { }