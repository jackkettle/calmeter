import { NgModule } from "@angular/core";
import { RouterModule } from "@angular/router";
import { BrowserModule } from "@angular/platform-browser";
import { ChartsModule } from 'ng2-charts/ng2-charts';
import { FoodComponent } from "./food.component";

@NgModule({
    declarations: [FoodComponent],
    imports: [BrowserModule, ChartsModule, RouterModule],
})

export class FoodModule { }