import { Component, OnInit } from '@angular/core';

import * as $ from "jquery";


@Component({
    selector: 'addFood',
    templateUrl: 'add-food.template.html'
})
export class AddFoodComponent implements OnInit {
    constructor() { }

    ngOnInit() {

        console.log("This bullshit is now working")

        $(".touchspin1").TouchSpin({
            verticalbuttons: true,
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });

    }
}