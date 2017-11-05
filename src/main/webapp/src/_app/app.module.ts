import { NgModule } from '@angular/core'
import { RouterModule } from "@angular/router";
import { AppComponent } from "./app.component";
import { BrowserModule } from "@angular/platform-browser";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpModule, Http } from "@angular/http";
import { ROUTES } from "./app.routes";
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

// Config
import { APP_CONFIG, AppConfig } from './app.config';

// Providers
import { SharedData } from "../_providers/shared-data.provider";

//Pipes
import { ReversePipe } from "../_pipes";

// Directives
import { SharedModule } from '../_directives/shared.module';

// Plugins
import { SelectModule } from 'ng2-select';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { AuthHttp, AuthConfig } from 'angular2-jwt';
import { SimpleNotificationsModule } from 'angular2-notifications';

// App views
import { DashboardModule } from "../views/dashboard/dashboard.module";
import { FoodModule } from "../views/food/food.module";
import { MealActionModule } from "../views/food/meal-action/meal-action.module";
import { FoodActionModule } from "../views/food/food-action/food-action.module";
import { DiaryModule } from "../views/diary/diary.module";
import { GoalsModule } from "../views/goals/goals.module";
import { DiaryEntryActionModule } from "../views/diary/diary-entry-action/diary-entry-action.module";
import { UserModule } from "../views/user/user.module";
import { EditUserModule } from "../views/user/edit-user/edit-user.module";
import { LoginModule } from "../views/login/login.module";
import { RegistrationModule } from "../views/registration/registration.module";

// App modules/components
import { NavigationModule } from "../views/common/navigation/navigation.module";
import { FooterModule } from "../views/common/footer/footer.module";
import { TopnavbarModule } from "../views/common/topnavbar/topnavbar.module";

import { FoodSelectionModule } from "../views/food/food-selection/food-selection.module";

import { AuthGuard } from "../_guards/auth.guard";
import { AuthService } from "../_services/auth.service";
import { AuthHttpService } from "../_services/auth-http.service";

export function getAuthHttp(http) {
    return new AuthHttp(new AuthConfig({
        headerName: 'X-Authorization',
        headerPrefix: 'Bearer',
        tokenName: 'id_token',
        globalHeaders: [
            { 'Accept': 'application/json' },
            { 'Content-Type': 'application/json' }],
        tokenGetter: (() => localStorage.getItem('id_token')),
    }), http);
}

@NgModule({
    declarations: [AppComponent],
    imports: [

        // Angular modules
        BrowserModule,
        BrowserAnimationsModule,
        HttpModule,
        FormsModule,
        ReactiveFormsModule,

        // Plugin modules
        SelectModule,
        NgxDatatableModule,
        SimpleNotificationsModule.forRoot(),

        
        // Views
        DashboardModule,    
        FoodModule,
        MealActionModule,
        FoodActionModule,
        DiaryModule,
        DiaryEntryActionModule,
        GoalsModule,
        UserModule,
        EditUserModule,
        LoginModule,
        RegistrationModule,
        FoodSelectionModule,

        // Modules
        NavigationModule,
        FooterModule,
        TopnavbarModule,
        SharedModule,

        RouterModule.forRoot(ROUTES)
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: APP_CONFIG, useValue: AppConfig },
        {
            provide: AuthHttp,
            useFactory: getAuthHttp,
            deps: [Http]
        },
        AuthGuard,
        AuthService,
        AuthHttpService,
        SharedData,
        ReversePipe
    ],
    bootstrap: [AppComponent]
})

export class AppModule { }