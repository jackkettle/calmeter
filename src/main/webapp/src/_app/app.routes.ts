import { Routes } from "@angular/router";
import { LoginComponent } from "../views/login/login.component";
import { RegistrationComponent } from "../views/registration/registration.component";
import { DashboardComponent } from "../views/dashboard/dashboard.component";
import { FoodComponent } from "../views/food/food.component";
import { MealActionComponent } from "../views/food/meal-action/meal-action.component";
import { FoodActionComponent } from "../views/food/food-action/food-action.component";
import { DiaryComponent } from "../views/diary/diary.component";
import { GoalsComponent } from "../views/goals/goals.component";
import { DiaryEntryActionComponent } from "../views/diary/diary-entry-action/diary-entry-action.component";
import { UserComponent } from "../views/user/user.component";
import { EditUserComponent } from "../views/user/edit-user/edit-user.component";
import { FoodSelectionComponent } from "../views/food/food-selection/food-selection.component";

import { AuthGuard } from '../_guards/auth.guard';

export const ROUTES: Routes = [
    // Login
    { path: 'login', component: LoginComponent },
    { path: 'registration', component: RegistrationComponent },

    // Main redirect
    { path: '', redirectTo: 'mainView', pathMatch: 'full', canActivate: [AuthGuard] },

    // App views
    { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
    { path: 'food', component: FoodComponent, canActivate: [AuthGuard] },
    { path: 'food/add/:id', component: FoodActionComponent, canActivate: [AuthGuard] },
    { path: 'food/edit/:id', component: FoodActionComponent, canActivate: [AuthGuard] },    
    { path: 'food/meal/:acton', component: MealActionComponent, canActivate: [AuthGuard] },
    { path: 'food/meal/add/:diaryId', component: MealActionComponent, canActivate: [AuthGuard] },
    { path: 'food/meal/edit/:id', component: MealActionComponent, canActivate: [AuthGuard] },    
    { path: 'goals', component: GoalsComponent, canActivate: [AuthGuard] },
    { path: 'diary', component: DiaryComponent, canActivate: [AuthGuard] },
    { path: 'diary/:date', component: DiaryComponent, canActivate: [AuthGuard] },
    { path: 'diary/add/:date', component: DiaryEntryActionComponent, canActivate: [AuthGuard] },
    { path: 'diary/edit/:id', component: DiaryEntryActionComponent, canActivate: [AuthGuard] },
    { path: 'user', component: UserComponent, canActivate: [AuthGuard] },
    { path: 'user/edit', component: EditUserComponent, canActivate: [AuthGuard] },
    { path: 'foodSelection', component: FoodSelectionComponent, canActivate: [AuthGuard] },
    

    // Handle all other routes
    { path: '**', component: DashboardComponent, canActivate: [AuthGuard] }
];
