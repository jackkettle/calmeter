import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { ControlMessagesComponent } from './control-messages.component';

@NgModule({
    declarations: [
        ControlMessagesComponent
    ],
    imports: [
        BrowserModule,
    ],
    exports: [
        ControlMessagesComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ControlMessagesModule { }
