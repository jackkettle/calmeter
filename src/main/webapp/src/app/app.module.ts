import { NgModule } from '@angular/core'
import { RouterModule } from "@angular/router";
import { AppComponent } from "./app";
import { BrowserModule } from "@angular/platform-browser";
import { HttpModule } from "@angular/http";
import { ROUTES } from "./app.routes";
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// PLugins
import { SelectModule } from 'ng-select';

// App views
import { DashboardModule } from "../views/dashboard/dashboard.module";
import { MinorViewModule } from "../views/minor-view/minor-view.module";
import { FoodModule } from "../views/food/food.module";
import { DiaryModule } from "../views/diary/diary.module";
import { AddRecipeModule } from "../views/food/add-recipe/add-recipe.module";
import { AddFoodModule } from "../views/food/add-food/add-food.module";

// App modules/components
import { NavigationModule } from "../views/common/navigation/navigation.module";
import { FooterModule } from "../views/common/footer/footer.module";
import { TopnavbarModule } from "../views/common/topnavbar/topnavbar.module";

@NgModule({
    declarations: [AppComponent],
    imports: [

        // Angular modules
        BrowserModule,
        HttpModule,
        FormsModule, 
        ReactiveFormsModule,

        // Plugin modules
        SelectModule,

        // Views
        DashboardModule,
        MinorViewModule,
        FoodModule,
        AddRecipeModule,
        DiaryModule,
        AddFoodModule,

        // Modules
        NavigationModule,
        FooterModule,
        TopnavbarModule,

        RouterModule.forRoot(ROUTES)
    ],
    providers: [{ provide: LocationStrategy, useClass: HashLocationStrategy }],
    bootstrap: [AppComponent]
})

export class AppModule { }