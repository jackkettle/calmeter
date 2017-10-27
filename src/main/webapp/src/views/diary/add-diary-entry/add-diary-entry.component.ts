import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from "@angular/router";
import { Observable } from 'rxjs/Rx';
import { NotificationsService } from 'angular2-notifications';
import { SharedData } from '../../../_providers/shared-data.provider';
import { FoodService } from '../../../_services/food.service';
import { DiaryService } from '../../../_services/diary.service';

import * as $ from "jquery";

@Component({
    selector: 'addDiaryEntry',
    templateUrl: 'add-diary-entry.template.html',
    providers: [FoodService, DiaryService]
})
export class AddDiaryEntryComponent implements OnInit {

    public currentDate: Date;

    public searchOption: String;

    public searchField: FormControl;

    public rows: Array<any>;

    public page;

    public selected: Array<any>;

    public searchOptions: Array<any>;

    public cssClasses;

    public loadingIndicator: boolean = true;

    public notificationOptions: any;

    formGroup: FormGroup;

    constructor(
        private router: Router,
        private sharedData: SharedData,
        private formBuilder: FormBuilder,
        private foodService: FoodService,
        private diaryService: DiaryService,
        private notificationsService: NotificationsService) {

        this.page = {
            pageNumber: 0,
            size: 20,
            totalElements: 0
        }

        this.notificationOptions = {
            position: ["top", "right"],
            timeOut: 5000,
            lastOnBottom: true,
        };

        this.cssClasses = {
            'sortAscending': 'fa fa-fw fa-sort-desc',
            'sortDescending': 'fa fa-fw fa-sort-asc',
            'pagerLeftArrow': 'fa fa-angle-left',
            'pagerRightArrow': 'fa fa-angle-right',
            'pagerPrevious': 'fa fa-angle-double-left',
            'pagerNext': 'fa fa-angle-double-right'
        }

        this.searchOptions = [
            { name: 'food', label: 'My Food', sourceID: 1 },
            { name: 'recipes', label: 'My Recipes', sourceID: 2 },
            { name: 'tesco', label: 'Tesco', sourceID: 3 }
        ]
    }

    ngOnInit() {
        this.searchOption = "food";
        this.selected = new Array();
        let dateObject = new Date();

        this.formGroup = this.formBuilder.group({
            date: [this.getDateFormat(dateObject)],
            time: [this.getTimeFormat(dateObject)],
            description: [''],
            foodItemFormArray: this.formBuilder.array([]),
        });

        this.setPage({ offset: 0 });

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

        this.searchField = new FormControl();
        this.searchField.valueChanges
            .debounceTime(700)
            .distinctUntilChanged()
            .subscribe(term => {
                console.log(term);
                this.loadingIndicator = true;
                this.getRowDataUsingQuery(term);
            });
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

    getRowDataUsingQuery(query: string) {
        this.foodService.getAllFoodUsingQuery(query, this.searchOption)
            .subscribe(response => {
                this.rows = this.transformData(response);
                this.page.totalElements = this.rows.length;
                this.loadingIndicator = false;
            });
    }

    assignSearchOption(searchOption) {
        this.searchOption = searchOption;
    }

    getRowData(): void {
        this.loadingIndicator = true;
        this.foodService.getAllFoodByUser().subscribe(
            response => {
                this.rows = this.transformData(response)
                this.loadingIndicator = false;
            }
        );
    }

    transformData(data) {
        let dataRows: Array<any> = []
        for (let entry of data) {
            let rowData = {
                'id': entry.externalId,
                'name': entry.name,
                'label': entry.name,
                'servingSize': entry.nutritionalInformation.servingSize + ' g',
                'source': this.searchOption
            }
            if (this.searchOption === "food")
                rowData.id = entry.id

            if (this.searchOption === "tesco")
                rowData.id = entry.externalId

            dataRows.push(rowData);
        }
        return dataRows;
    }

    setPage(pageInfo) {
        this.page.pageNumber = pageInfo.offset;
        this.foodService.getAllFoodByUser().subscribe(response => {
            this.rows = this.transformData(response);
            this.page.totalElements = this.rows.length;
            this.loadingIndicator = false;
        });
    }

    onSelect(selected) { // either add or remove
        selected = selected.selected;
        let controls = <FormArray>this.formGroup.controls['foodItemFormArray'];

        if (controls.length < selected.length) { // Add
            let entry = selected[selected.length - 1];;
            let row = this.formBuilder.group({
                ngxIndex: [entry.$$index, Validators.required],
                name: [entry.name, Validators.required],
                id: [entry.id, Validators.required],
                source: [entry.source, Validators.required],
                servingSize: [entry.servingSize, Validators.required],
                servings: [1.0, Validators.required],
                weight: ['', Validators.required]
            });
            controls.push(row);

        } else { // Remove

            var foundNgxIds = Array();
            for (let entry of selected) {
                foundNgxIds.push(entry.$$index);
            }

            for (var i = 0; i < controls.length; i++) {
                let myFormGroup: FormGroup = <FormGroup>controls.controls[i];
                var ngxValue = myFormGroup.controls["ngxIndex"].value;

                if (!foundNgxIds.includes(ngxValue)) {
                    controls.removeAt(i);
                    return;
                }
            }
        }
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
}