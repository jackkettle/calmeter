import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AppLayoutComponent} from './app-layout.component';
import {RouterModule} from "@angular/router";
import {TopnavbarModule} from "../../common/topnavbar/topnavbar.module";
import {FooterModule} from "../../common/footer/footer.module";
import {NavigationModule} from "../../common/navigation/navigation.module";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        TopnavbarModule,
        NavigationModule,
        FooterModule
    ],
    declarations: [AppLayoutComponent]
})
export class AppLayoutModule {
}
