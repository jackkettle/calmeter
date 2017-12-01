import { Component, OnInit, Injectable, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { NotificationsService } from 'angular2-notifications';
import { APP_CONFIG, IAppConfig } from '../../../_app/app.config';
import { UserService } from "../../../_services/";

import * as $ from "jquery";

@Component({
    selector: 'editUserSelector',
    templateUrl: 'edit-user.template.html',
    providers: [UserService]
})
export class EditUserComponent implements OnInit {

    public formGroup: FormGroup;

    public notificationOptions: any;

    constructor(
        @Inject(APP_CONFIG) private config: IAppConfig,        
        private formBuilder: FormBuilder, 
        private userService: UserService,
        private notificationsService: NotificationsService
    ) { }

    ngOnInit() {

        this.notificationOptions = this.config.toastNotificationOptions;
        
        this.formGroup = this.formBuilder.group({
            username: new FormControl({ value: '', disabled: true }),
            id: new FormControl({ value: '', disabled: true }),
            firstname: [''],
            lastname: [''],
            email: [''],
            dateOfBirth: [''],
            sex: [''],
            height: [''],
            weight: this.formBuilder.group({
                weightInKgs: [''],
                dateTime: [new Date()]
            })
        });

        var heightSelector = $(".height-slider");
        heightSelector.ionRangeSlider({
            values: this.generateHeightValues(),
            onFinish: (function (data) {
                var value = data.from_value;
                value = value.substring(0, value.indexOf("cm"));
                value = value.trim();
                this.formGroup.controls["height"].setValue(value);
            }).bind(this)
        })

        var weightSelector = $(".weight-slider");
        weightSelector.ionRangeSlider({
            values: this.generateWeightValues(),
            onFinish: (function (data) {
                var value = data.from_value;
                value = value.substring(0, value.indexOf("kgs"));
                value = value.trim();
                this.formGroup.patchValue({
                    weight: {
                        weightInKgs: value
                    }
                });
            }).bind(this)
        });

        $('.datepicker').datepicker({
            startView: 2,
            format: "dd/mm/yyyy"
        }).on("changeDate", (event) => {
            this.formGroup.controls['dateOfBirth'].setValue(this.getDateFormat(event.date));
        })


        this.callValues();

    }

    callValues() {
        this.userService.getThisUser().subscribe(
            data => {
                this.applyValues(data);
            }
        );
    }

    applyValues(data) {

        this.formGroup.controls["username"].setValue(data.username);
        this.formGroup.controls["id"].setValue(data.id);
        this.formGroup.controls["firstname"].setValue(data.firstname);
        this.formGroup.controls["lastname"].setValue(data.lastname);
        this.formGroup.controls["email"].setValue(data.email);

        if (data.isUserProfileSet) {
            var dob: Date = new Date(data.userProfile.dateOfBirth * 1000);
            this.formGroup.controls["dateOfBirth"].setValue(this.getDateFormat(dob));
            var sex: string = data.userProfile.sex;
            sex = sex.toLocaleLowerCase();
            sex = sex.charAt(0).toUpperCase() + sex.slice(1)   
            this.formGroup.controls["sex"].setValue(sex);

            this.formGroup.controls["height"].setValue(data.userProfile.height);
            $(".height-slider").data("ionRangeSlider").update({
                from: data.userProfile.height - 1
            });

            let userWeight = this.getCurrentWeight(data.userProfile.weightLog)
            this.formGroup.patchValue({
                weight: {
                    weightInKgs: userWeight
                }
            });
            $(".weight-slider").data("ionRangeSlider").update({
                from: (userWeight * 10) - 10
            });
        }
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

    getCurrentWeight(weightList: Array<any>){

        if(weightList == null || weightList.length < 1)
            return 0;

        let mostRecentWeight: any = weightList[0];
        for(var i = 1; i < weightList.length; i ++) {
            let mostRecentDate: Date =  new Date(mostRecentWeight.dateTime);
            let entryDate : Date =  new Date(weightList[i].dateTime);

            if(entryDate > mostRecentDate){
                mostRecentWeight = weightList[i];
            }
        }
        return mostRecentWeight.weightInKgs;
    }

    save(data) {
        this.userService.edit(data.value).subscribe(
            res => {
                console.log(res);
                this.successNotification();
            }
        );
    }
    
    successNotification() {
        this.notificationsService.success(
            'User settings saved',
            'Time: ' + new Date().toLocaleTimeString(),
            {
                showProgressBar: true,
                pauseOnHover: false,
                clickToClose: true,
                maxLength: 10
            }
        )
    }

}