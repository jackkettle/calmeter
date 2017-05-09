import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { Observable } from 'rxjs/Rx';
import { FoodService } from '../../food/food.service';
import { DiaryService } from '../diary.service';
import { DiaryEntry } from '../diary.interface';

import * as $ from "jquery";

@Component({
    selector: 'addDiaryEntry',
    templateUrl: 'add-diary-entry.template.html',
    providers: [FoodService, DiaryService]
})
export class AddDiaryEntryComponent implements OnInit {

    public currentDate: Date;

    public searchOption: String;

    public rows: Array<any>;

    public page;

    public selected: Array<any>;

    public searchOptions: Array<any>;

    public cssClasses;

    public loadingIndicator: boolean = true;

    formGroup: FormGroup;

    constructor(private formBuilder: FormBuilder, private foodService: FoodService, private diaryService: DiaryService) {
        this.page = {
            pageNumber: 0,
            size: 20,
            totalElements: 0
        }

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
            { name: 'recipes', label: 'My Recipes', sourceID: 2 }
        ]
    }

    ngOnInit() {

        this.searchOption = "food";
        this.selected = new Array();

        $('.datepicker').datepicker({
            todayBtn: true,
            todayHighlight: true
        });

        $('.timepicker-input').timepicker({
            showInputs: false
        });

        this.setPage({ offset: 0 });

        let dateObject = new Date();

        this.formGroup = this.formBuilder.group({
            date: [this.getDateFormat(dateObject)],
            time: [this.getTimeFormat(dateObject)],
            description: [''],
            foodItemFormArray: this.formBuilder.array([]),
        });

    }

    assignSearchOption(searchOption) {
        this.searchOption = searchOption;
    }

    getRowData() {
        this.foodService.getAllFood()
            .subscribe(response => { this.rows = this.transformData(response) });
    }

    transformData(data) {
        let dataRows: Array<any> = []
        for (let entry of data) {
            let rowData = {
                'id': entry.id,
                'name': entry.name,
                'label': entry.name,
                'source': this.searchOption
            }
            dataRows.push(rowData);
        }
        return dataRows;
    }

    setPage(pageInfo) {
        this.page.pageNumber = pageInfo.offset;
        this.foodService.getAllFood().subscribe(response => {
            this.rows = this.transformData(response);
            this.page.totalElements = this.rows.length;
        });
    }

    onSelect({ selected }) {
        console.log('Select Event', selected);
        console.log('1', this.selected); 
        this.selected.splice(0, this.selected.length);
        this.selected.push(...selected);

        const control = <FormArray>this.formGroup.controls['foodItemFormArray'];

        while (control.length) {
            control.removeAt(control.length - 1);
        }

        for(let entry of selected){
            let row = this.formBuilder.group({
                name: [entry.name, Validators.required],
                id: [entry.id, Validators.required],
                source: [entry.source, Validators.required],
                servings: ['', Validators.required],
                weight: ['', Validators.required]
            });
            control.push(row);
        }

        console.log(control);

    }

    save(model) {
        console.log("Saving!");
        console.log(model.value);
        this.diaryService.addEntry(model.value).subscribe(
            res => {
                console.log(res);
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