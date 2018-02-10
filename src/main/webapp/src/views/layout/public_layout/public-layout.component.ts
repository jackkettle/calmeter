import {Component, OnInit} from '@angular/core';

declare var $: any;

@Component({
    selector: 'public-layout',
    templateUrl: './public-layout.component.html'
})
export class PublicLayoutComponent implements OnInit {

    constructor() {
    }

    ngOnInit() {
        $('body').addClass('gray-bg');
    }

}
