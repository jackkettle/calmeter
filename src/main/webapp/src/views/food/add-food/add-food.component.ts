import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray } from '@angular/forms';


import * as $ from "jquery";


@Component({
    selector: 'addFood',
    templateUrl: 'add-food.template.html'

})
export class AddFoodComponent implements OnInit {

    formGroup: FormGroup;

    name: FormControl;

    vitaminFormArray: FormArray;
    mineralFormArray: FormArray;

    vitaminSelectArray: Array<any> = [];
    mineralSelectArray: Array<any> = [];

    constructor( @Inject(FormBuilder) fb: FormBuilder) {

        const row = new FormArray([
            new FormControl(''),
            new FormControl(''),
        ]);
        
        this.vitaminFormArray.push(row);
        this.mineralSelectArray.push(row);

        this.formGroup = fb.group({
            name: this.name,
            vitaminFormArray: this.vitaminFormArray,
            mineralFormArray: this.mineralFormArray
        });

    }

    ngOnInit() {

        $(".touchspinWhole").TouchSpin(
            dataWhole
        );

        $(".touchspin2Decimal").TouchSpin(
            data2Decimal
        );

        let opts = new Array(vitaminList.length);
        for (let i = 0; i < vitaminList.length; i++) {
            opts[i] = {
                value: vitaminList[i],
                label: vitaminList[i]
            };
        }
        this.vitaminSelectArray = opts.slice(0);

        opts = new Array(mineralList.length);
        for (let i = 0; i < mineralList.length; i++) {
            opts[i] = {
                value: mineralList[i],
                label: mineralList[i]
            };
        }
        this.mineralSelectArray = opts.slice(0);
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