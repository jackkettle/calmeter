import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {RouterModule} from "@angular/router";
import {ChartsModule} from 'ng2-charts/ng2-charts';
import {DiaryComponent} from "./diary.component";
import { SimpleNotificationsModule } from 'angular2-notifications';




@NgModule({
    declarations: [DiaryComponent],
    imports     : [BrowserModule, ChartsModule, SimpleNotificationsModule, RouterModule]
})

export class DiaryModule {}