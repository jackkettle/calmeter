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
import { NgxDatatableModule } from '@swimlane/ngx-datatable';

// App views
import { DashboardModule } from "../views/dashboard/dashboard.module";
import { MinorViewModule } from "../views/minor-view/minor-view.module";
import { FoodModule } from "../views/food/food.module";
import { AddRecipeModule } from "../views/food/add-recipe/add-recipe.module";
import { AddFoodModule } from "../views/food/add-food/add-food.module";
import { DiaryModule } from "../views/diary/diary.module";
import { AddDiaryEntryModule } from "../views/diary/add-diary-entry/add-diary-entry.module";
import { UserModule } from "../views/user/user.module";
import { EditUserModule } from "../views/user/edit-user/edit-user.module";


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
        NgxDatatableModule,

        // Views
        DashboardModule,
        MinorViewModule,
        FoodModule,
        AddRecipeModule,
        AddFoodModule,
        DiaryModule,
        AddDiaryEntryModule,
        UserModule,
        EditUserModule,

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