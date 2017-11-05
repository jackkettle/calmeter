import { NgModule } from '@angular/core';
import { OnCreate } from './on-create.directive';

@NgModule({
    declarations: [
        OnCreate
    ],
    exports: [
        OnCreate
    ]
})
export class SharedModule{}