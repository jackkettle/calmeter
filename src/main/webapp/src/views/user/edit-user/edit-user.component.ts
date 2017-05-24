import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';


import * as $ from "jquery";

@Component({
    selector: 'editUserSelector',
    templateUrl: 'edit-user.template.html'
})

export class EditUserComponent implements OnInit {

    formGroup: FormGroup;

    firstName: FormControl;
    lastName: FormControl;
    email: FormControl;
    dateOfBirth: FormControl;
    sex: FormControl;
    height: FormControl;
    weight: FormControl;

    constructor(private formBuilder: FormBuilder) { }

    ngOnInit() {

        this.formGroup = this.formBuilder.group({
            firstName: [''],
            lastName: [''],
            email: [''],
            dateOfBirth: [''],
            sex: [''],
            height: [''],
            weight: ['']
        });

        $(".height-slider").ionRangeSlider({
            values: this.generateHeightValues()
        });

        $(".weight-slider").ionRangeSlider({
            values: this.generateWeightValues()
        });

        $('.datepicker').datepicker({
            startView: 2,
            format: "dd/mm/yyyy"
        }).on("changeDate", (event) => {
            this.formGroup.controls['dateOfBirth'].setValue(this.getDateFormat(event.date));
        })

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

    getDateFormat(date: Date) {
        var days = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        var month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
        var dateFormat = days + "/" + month + "/" + date.getFullYear();
        return dateFormat
    }

    round(value, precision) {
        var multiplier = Math.pow(10, precision || 0);
        return Math.round(value * multiplier) / multiplier;
    }

    onWeightKey(event) {
        var slider = $(".weight-slider").data("ionRangeSlider");
        var value = event.target.value;
        var newValue = (value * 10) - 10;
        slider.update({
            from: newValue
        });
    }

    onHeightKey(event) {
        var slider = $(".height-slider").data("ionRangeSlider");
        var value = event.target.value;
        var newValue = value - 1;
        slider.update({
            from: newValue
        });
    }

    save(data) {
        console.log(data);
    }

}