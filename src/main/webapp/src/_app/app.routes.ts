import { Routes } from "@angular/router";
import { DashboardComponent } from "../views/dashboard/dashboard.component";
import { minorViewComponent } from "../views/minor-view/minor-view.component";
import { FoodComponent } from "../views/food/food.component";
import { AddRecipeComponent } from "../views/food/add-recipe/add-recipe.component";
import { AddFoodComponent } from "../views/food/add-food/add-food.component";
import { DiaryComponent } from "../views/diary/diary.component";
import { AddDiaryEntryComponent } from "../views/diary/add-diary-entry/add-diary-entry.component";
import { UserComponent } from "../views/user/user.component";
import { EditUserComponent } from "../views/user/edit-user/edit-user.component";





export const ROUTES: Routes = [
    // Main redirect
    { path: '', redirectTo: 'mainView', pathMatch: 'full' },

    // App views
    { path: 'dashboard', component: DashboardComponent },
    { path: 'minorView', component: minorViewComponent },
    { path: 'food', component: FoodComponent },
    { path: 'food/addRecipe', component: AddRecipeComponent },
    { path: 'food/addFood', component: AddFoodComponent },
    { path: 'diary', component: DiaryComponent },
    { path: 'diary/addEntry', component: AddDiaryEntryComponent },
    { path: 'user', component: UserComponent },
    { path: 'user/edit', component: EditUserComponent },
    // Handle all other routes
    { path: '**', component: DashboardComponent }
];
