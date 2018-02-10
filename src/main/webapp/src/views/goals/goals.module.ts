import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";
import {SelectModule} from 'ng2-select';
import {GoalsComponent} from './goals.component';
import {SimpleNotificationsModule} from "angular2-notifications/index";

@NgModule({
    imports: [CommonModule, SelectModule, RouterModule, SimpleNotificationsModule],
    exports: [],
    declarations: [GoalsComponent],
    providers: [],
})
export class GoalsModule {
}
