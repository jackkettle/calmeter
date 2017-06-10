import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {ChartsModule} from 'ng2-charts/ng2-charts';
import {DiaryComponent} from "./diary.component";

@NgModule({
    declarations: [DiaryComponent],
    imports     : [BrowserModule, ChartsModule],
})

export class DiaryModule {}