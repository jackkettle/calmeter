import { Component, OnInit } from '@angular/core';

import * as $ from "jquery";

@Component({
    selector: 'editUserSelector',
    templateUrl: 'edit-user.template.html'
})

export class EditUserComponent implements OnInit {
    constructor() { }

    ngOnInit() {
        var $range = $(".js-range-slider");

        $range.ionRangeSlider({
            values: this.generateHeightValues()
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
        let foot = "ft";
        let inch = "in";

        for (var i = 1; i < 9; i++) {
            for (var j = 1; j < 12; j++) {
                let unit = i + " " + foot + ", " + j + " " + inch;
                array.push(unit);
            }
        }

        return array;
    }
}