import {Routes} from "@angular/router";
import {DashboardComponent} from "../views/dashboard/dashboard.component";
import {minorViewComponent} from "../views/minor-view/minor-view.component";
import {foodMainViewComponent} from "../views/food-view/food-main-view.component";

export const ROUTES:Routes = [
    // Main redirect
    {path: '', redirectTo: 'mainView', pathMatch: 'full'},

    // App views
    {path: 'dashboard', component: DashboardComponent},
    {path: 'minorView', component: minorViewComponent},
    {path: 'foodMainView', component: foodMainViewComponent},

    // Handle all other routes
    {path: '**',    component: DashboardComponent }
];
