import { Injectable } from '@angular/core';
 
@Injectable()
export class SharedData {
 
    public diaryNotificationQueue: Array<any>;
 
    public constructor() {
        this.diaryNotificationQueue = new Array();
    }
 
}