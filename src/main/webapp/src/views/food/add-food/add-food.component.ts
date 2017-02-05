import { Component, OnInit } from '@angular/core';

import * as $ from "jquery";


@Component({
    selector: 'addFood',
    templateUrl: 'add-food.template.html'
})
export class AddFoodComponent implements OnInit {
    
    constructor() {
    }

    ngOnInit() {
        console.log("This bullshit is now working")
        $(".touchspin3").TouchSpin(
            data
        );
    }
}

var data = {
    min: 0,
    max: 5000,
    step: 1,
    decimals: 2,
    buttondown_class: 'btn btn-white',
    buttonup_class: 'btn btn-white'
};