import { NgModule } from '@angular/core';
import { EditUserComponent } from './edit-user.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


@NgModule({
    imports: [
        FormsModule,
        ReactiveFormsModule
    ],
    exports: [],
    declarations: [EditUserComponent],
    providers: [],
})
export class EditUserModule { }
