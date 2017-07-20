import { Routes } from "@angular/router";
import { LoginComponent } from "../views/login/login.component";
import { RegistrationComponent } from "../views/registration/registration.component";
import { DashboardComponent } from "../views/dashboard/dashboard.component";
import { minorViewComponent } from "../views/minor-view/minor-view.component";
import { FoodComponent } from "../views/food/food.component";
import { AddRecipeComponent } from "../views/food/add-recipe/add-recipe.component";
import { AddFoodComponent } from "../views/food/add-food/add-food.component";
import { DiaryComponent } from "../views/diary/diary.component";
import { GoalsComponent } from "../views/goals/goals.component";
import { AddDiaryEntryComponent } from "../views/diary/add-diary-entry/add-diary-entry.component";
import { UserComponent } from "../views/user/user.component";
import { EditUserComponent } from "../views/user/edit-user/edit-user.component";

import { AuthGuard } from '../_guards/auth.guard';

export const ROUTES: Routes = [
    // Login
    { path: 'login', component: LoginComponent },
    { path: 'registration', component: RegistrationComponent },

    // Main redirect
    { path: '', redirectTo: 'mainView', pathMatch: 'full', canActivate: [AuthGuard] },

    // App views
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'minorView', component: minorViewComponent, canActivate: [AuthGuard] },
    { path: 'food', component: FoodComponent, canActivate: [AuthGuard] },
    { path: 'food/addRecipe', component: AddRecipeComponent, canActivate: [AuthGuard] },
    { path: 'food/addFood', component: AddFoodComponent, canActivate: [AuthGuard] },
    { path: 'goals', component: GoalsComponent, canActivate: [AuthGuard] },
    { path: 'diary', component: DiaryComponent, canActivate: [AuthGuard] },
    { path: 'diary/addEntry', component: AddDiaryEntryComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'user/edit', component: EditUserComponent, canActivate: [AuthGuard] },

    // Handle all other routes
    { path: '**', component: DashboardComponent, canActivate: [AuthGuard] }
];
