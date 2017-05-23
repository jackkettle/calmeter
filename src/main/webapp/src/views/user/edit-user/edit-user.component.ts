import { Component, OnInit } from '@angular/core';

import * as $ from "jquery";

@Component({
    selector: 'editUserSelector',
    templateUrl: 'edit-user.template.html'
})

export class EditUserComponent implements OnInit {
    constructor() { }

    ngOnInit() {
        $(".height-slider").ionRangeSlider({
            values: this.generateHeightValues()
        });

        $(".weight-slider").ionRangeSlider({
            values: this.generateWeightValues()
        });

        $('.datepicker').datepicker({
            startView: 2,
            keyboardNavigation: false,
            forceParse: false,
            autoclose: true
        });
    }

    generateHeightValues() {
        let array = [];
        for (var i = 1; i <= 250; i++) {
            let string = i + " cm. | " + this.convertHeightToImperial(i);
            array.push(string);
        }
        return array;
    }

    convertHeightToImperial(input) {
        let totalInches = input * 0.39370;
        let feet = Math.floor(totalInches / 12);
        let leftInches = Math.floor(totalInches % 12);
        return feet + " ft, " + leftInches + " in";
    }

    generateWeightValues() {
        let array = [];
        for (var i = 1; i <= 300; i = i + 0.1) {
            let string = this.round(i, 1) + " kgs. | " + this.convertWeightToImperial(i);
            array.push(string);
        }
        return array;
    }

    convertWeightToImperial(input) {
        let totalInches = input * 2.2046;
        let feet = Math.floor(totalInches / 14);
        let leftInches = Math.floor(totalInches % 14);
        return feet + " st, " + leftInches + " lbs";
    }

    round(value, precision) {
        var multiplier = Math.pow(10, precision || 0);
        return Math.round(value * multiplier) / multiplier;
    }
}