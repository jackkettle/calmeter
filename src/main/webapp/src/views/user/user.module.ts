import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from "@angular/router";
import {UserComponent} from './user.component';

@NgModule({
    imports: [CommonModule, RouterModule],
    exports: [],
    declarations: [UserComponent],
    providers: [],
})
export class UserModule {
}
