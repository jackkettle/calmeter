import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { SharedModule } from '../../../_directives/shared.module';
import { FoodSelectionComponent } from './food-selection.component';

@NgModule({
    declarations: [
        FoodSelectionComponent
    ],
    imports: [
        SharedModule,
        BrowserModule,
        FormsModule,
        ReactiveFormsModule,
        NgxDatatableModule
    ],
    exports: [
        FoodSelectionComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodSelectionModule { }
