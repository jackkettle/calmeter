import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

import * as $ from "jquery";


@Component({
    selector: 'addFood',
    //templateUrl: 'views/food/add-food/add-food.template.html'
    templateUrl: 'add-food.template.html'

})
export class AddFoodComponent implements OnInit {

    vitaminArray: Array<any> = vitaminList;

    constructor() {
    }

    ngOnInit() {
        console.log("This bullshit is now working")
        $(".touchspinWhole").TouchSpin(
            dataWhole
        );

        $(".touchspin2Decimal").TouchSpin(
            data2Decimal
        );
    }
}

var dataWhole = {
    min: 0,
    max: 5000,
    step: 1,
    buttondown_class: 'btn btn-white',
    buttonup_class: 'btn btn-white'
};

var data2Decimal = {
    min: 0,
    max: 5000,
    step: .01,
    decimals: 2,
    buttondown_class: 'btn btn-white',
    buttonup_class: 'btn btn-white'
}

var vitaminList = [
    "VITAMIN_A",
    "VITAMIN_B1",
    "VITAMIN_B2",
    "VITAMIN_B3",
    "VITAMIN_B5",
    "VITAMIN_B6",
    "VITAMIN_B12",
    "BIOTIN",
    "VITAMIN_C",
    "CHOLINE",
    "VITAMIN_D",
    "VITAMIN_E",
    "FOLIC_ACID",
    "VITAMIN_K"
];

var mineralList = [
    "CALCIUM",
    "CHLORIDE",
    "CHROMIUM",
    "COPPER",
    "FLUORIDE",
    "IODINE",
    "IRON",
    "MAGNESIUM",
    "MANGANESE",
    "MOLYBDENUM",
    "PHOSPHORUS",
    "POTASSIUM",
    "SELENIUM",
    "SODIUM",
    "SULFUR",
    "ZINC"
];