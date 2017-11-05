import { Component, OnInit, Inject, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FoodService } from '../../../_services/food.service';

import * as $ from "jquery";

@Component({
    selector: 'food-selection',
    templateUrl: 'food-selection.template.html',
    providers: [FoodService]
})
export class FoodSelectionComponent implements OnInit {

    @Output()
    public formData = new EventEmitter();

    public formGroup: FormGroup;

    public searchOption: String;
    public searchField: FormControl;
    public rows: Array<any>;
    public page: any;
    public selected: Array<any>;
    public searchOptions: Array<any>;
    public cssClasses: any;
    public loadingIndicator: boolean;

    constructor(
        private formBuilder: FormBuilder,
        private foodService: FoodService
    ) {

        this.loadingIndicator = true;
        this.searchOption = "food";
        this.selected = new Array();

        this.page = {
            pageNumber: 0,
            size: 20,
            totalElements: 0
        }

        this.searchOptions = [
            { name: 'food', label: 'My Food', sourceID: 1 },
            { name: 'recipes', label: 'My Recipes', sourceID: 2 },
            { name: 'tesco', label: 'Tesco', sourceID: 3 }
        ]

        this.cssClasses = {
            'sortAscending': 'fa fa-fw fa-sort-desc',
            'sortDescending': 'fa fa-fw fa-sort-asc',
            'pagerLeftArrow': 'fa fa-angle-left',
            'pagerRightArrow': 'fa fa-angle-right',
            'pagerPrevious': 'fa fa-angle-double-left',
            'pagerNext': 'fa fa-angle-double-right'
        }
    }

    ngOnInit() {
        this.formGroup = this.formBuilder.group({
            foodItemFormArray: this.formBuilder.array([]),
        });
        this.setPage({ offset: 0 });
        this.searchField = new FormControl();
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

    setPage(pageInfo) {
        this.page.pageNumber = pageInfo.offset;
        this.foodService.getAllFoodUsingQuery("", this.searchOption).subscribe(response => {
            this.rows = this.transformData(response);
            this.page.totalElements = this.rows.length;
            this.loadingIndicator = false;
        });
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
            this.updateOutput();

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
                    this.updateOutput();
                    return;
                }
            }
        }
    }

    updateOutput() {
        this.formData.emit(this.formGroup.controls["foodItemFormArray"]);
    }

    updateItems() {
        let term = this.searchField.value;
        this.loadingIndicator = true;
        this.getRowDataUsingQuery(term);
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
}