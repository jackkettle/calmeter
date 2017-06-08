import { NgModule } from '@angular/core'
import { RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
import { BrowserModule } from "@angular/platform-browser";
import { HttpModule } from "@angular/http";
import { ROUTES } from "./app.routes";
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Config
import { APP_CONFIG, AppConfig } from './app.config';

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
import { LoginModule } from "../views/login/login.module";

// App modules/components
import { NavigationModule } from "../views/common/navigation/navigation.module";
import { FooterModule } from "../views/common/footer/footer.module";
import { TopnavbarModule } from "../views/common/topnavbar/topnavbar.module";

import { AuthGuard } from "../_guards/auth.guard";
import { AuthenticationService } from "../_services/authentication.service";


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
        LoginModule,

        // Modules
        NavigationModule,
        FooterModule,
        TopnavbarModule,

        RouterModule.forRoot(ROUTES)
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: APP_CONFIG, useValue: AppConfig },
        AuthGuard,
        AuthenticationService
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }