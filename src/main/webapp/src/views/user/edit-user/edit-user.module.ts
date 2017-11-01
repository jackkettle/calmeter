import { NgModule } from '@angular/core';
import { EditUserComponent } from './edit-user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SimpleNotificationsModule } from 'angular2-notifications';

@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule,
        SimpleNotificationsModule
    ],
    exports: [],
    declarations: [EditUserComponent],
    providers: [],
})
export class EditUserModule { }
