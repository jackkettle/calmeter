import {Component, OnInit} from '@angular/core';

declare var $: any;

@Component({
    selector: 'app-layout',
    templateUrl: './app-layout.component.html'
})
export class AppLayoutComponent implements OnInit {

    constructor() {
    }

    ngOnInit() {
        $('body').removeClass('gray-bg');
    }

}
