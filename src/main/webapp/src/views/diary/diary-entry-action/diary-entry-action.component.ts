import { Component, OnInit, Injectable, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from "@angular/router";
import { Location } from '@angular/common';
import { Observable } from 'rxjs/Rx';
import { NotificationsService } from 'angular2-notifications';
import { APP_CONFIG, IAppConfig } from '../../../_app/app.config';
import { SharedData } from '../../../_providers/shared-data.provider';
import { DiaryService } from '../../../_services/diary.service';

import * as $ from "jquery";
import * as moment from "moment";


@Component({
    selector: 'diaryEntryAction',
    templateUrl: 'diary-entry-action.template.html',
    providers: [DiaryService]
})
export class DiaryEntryActionComponent implements OnInit {


    public id: number;
    public date: string;

    public currentDate: Date;
    public activeDate: Date;

    public notificationOptions: any;

    public formGroup: FormGroup;

    constructor(
        private router: Router,
        private sharedData: SharedData,
        private formBuilder: FormBuilder,
        private diaryService: DiaryService,
        private notificationsService: NotificationsService,
        private route: ActivatedRoute,
        private location: Location
    ) {

        this.notificationOptions = {
            position: ["top", "right"],
            timeOut: 5000,
            lastOnBottom: true,
        };
    }

    ngOnInit() {

        var editAction = false;
        if (this.router.url.includes('edit'))
            editAction = true;

        this.route.params.subscribe(params => {

            let date = params['date'];
            if (date)
                this.activeDate = new Date(date);
            else
                this.activeDate = new Date();

            this.id = +params['id'];
            if (editAction) {
                this.loadInitFormValues(this.id);
            }
        });

        let dateObject = this.activeDate;

        this.formGroup = this.formBuilder.group({
            date: [this.getDateFormat(dateObject)],
            time: [this.getTimeFormat(dateObject)],
            description: [''],
            foodItemFormArray: this.formBuilder.array([], Validators.required),
        });

        $('.datepicker').datepicker({
            todayBtn: true,
            todayHighlight: true,
            format: 'dd/mm/yyyy'
        }).on("changeDate", (event) => {
            this.formGroup.controls['date'].setValue(this.getDateFormat(event.date));
        });

        $('.timepicker-input').timepicker({
            showInputs: false
        });
    }

    loadInitFormValues(id: number) {
        this.diaryService.get(id).subscribe(
            data => {
                this.applyData(data);
            },
            error => {
                console.log(error);
            }
        );
    }

    applyData(data: any) {

        console.log(data);

        // this.formGroup = this.formBuilder.group({

        //     date: [this.getDateFormat(data.date)],

        //     // name: [data.name, [Validators.required, Validators.minLength(2)]],
        //     // calsPer100: [data.nutritionalInformation.calories],
        //     // servingSize: [data.nutritionalInformation.servingSize],
        //     // totalfat: [data.nutritionalInformation.consolidatedFats.totalFat],
        //     // saturatedFat: [data.nutritionalInformation.consolidatedFats.saturatedFat],
        //     // polyUnsaturatedFat: [data.nutritionalInformation.consolidatedFats.polyUnsaturatedFat],
        //     // monoUnsaturatedFat: [data.nutritionalInformation.consolidatedFats.monoUnsaturatedFat],
        //     // cholesterol: [data.nutritionalInformation.consolidatedFats.cholesterol],
        //     // totalCarbs: [data.nutritionalInformation.consolidatedCarbs.total],
        //     // fiber: [data.nutritionalInformation.consolidatedCarbs.fiber],
        //     // sugar: [data.nutritionalInformation.consolidatedCarbs.sugar],
        //     // protein: [data.nutritionalInformation.consolidatedProteins.protein],
        //     // sodium: [data.nutritionalInformation.mineralMap.SODIUM],
        //     // vitaminFormArray: this.formBuilder.array([
        //     //     this.initRow()
        //     // ]),
        //     // mineralFormArray: this.formBuilder.array([
        //     //     this.initRow()
        //     // ])
        // });
    }

    handleFoodUpdated(food: any) {
        this.formGroup.setControl('foodItemFormArray', food);
    }

    initTouchSpin(index: number) {
        var query = "table tr:eq(" + (index + 1) + ") input.touchspin2Decimal";
        $(query).TouchSpin({
            min: 0,
            max: 200,
            step: .1,
            decimals: 2,
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });
    }

    save(model) {
        console.log("Saving!");
        console.log(model.value);
        this.diaryService.addEntry(model.value).subscribe(
            res => {
                this.sharedData.diaryNotificationQueue.push({ "type": "success", "time": model.value.time });
                this.router.navigate(["/diary"]);
            }, err => {
                console.log(err);
            }
        );
    }

    getTimeFormat(date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        var strTime = hours + ':' + minutes + ' ' + ampm;
        return strTime;
    }

    getDateFormat(date: Date) {
        var days = date.getDate() < 10 ? '0' + date.getDate() : date.getDate();
        var month = (date.getMonth() + 1) < 10 ? '0' + (date.getMonth() + 1) : (date.getMonth() + 1);
        var dateFormat = days + "/" + month + "/" + date.getFullYear();
        return dateFormat
    }

    backClicked() {
        this.location.back();
    }
}