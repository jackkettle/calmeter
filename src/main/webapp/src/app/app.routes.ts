import {Routes} from "@angular/router";
import {mainViewComponent} from "../views/main-view/main-view.component";
import {minorViewComponent} from "../views/minor-view/minor-view.component";
import {foodMainViewComponent} from "../views/food-view/food-main-view.component";

export const ROUTES:Routes = [
    // Main redirect
    {path: '', redirectTo: 'mainView', pathMatch: 'full'},

    // App views
    {path: 'mainView', component: mainViewComponent},
    {path: 'minorView', component: minorViewComponent},
    {path: 'foodMainView', component: foodMainViewComponent},

    // Handle all other routes
    {path: '**',    component: mainViewComponent }
];
