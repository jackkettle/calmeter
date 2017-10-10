import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule} from "@angular/router";
import { SelectModule } from 'ng2-select';
import { GoalsComponent } from './goals.component';

@NgModule({
    imports: [CommonModule, SelectModule, RouterModule],
    exports: [],
    declarations: [GoalsComponent],
    providers: [],
})
export class GoalsModule { }
