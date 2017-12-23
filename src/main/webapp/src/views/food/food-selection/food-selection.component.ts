import {Component, OnInit, Input, Output, EventEmitter, ViewChild, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormArray, Validators} from '@angular/forms';
import {FoodService} from '../../../_services';
import {APP_CONFIG, IAppConfig} from "../../../_app/app.config";

declare var $: any;

@Component({
    selector: 'food-selection',
    templateUrl: 'food-selection.template.html',
    providers: [FoodService]
})
export class FoodSelectionComponent implements OnInit {

    @ViewChild("fileInput") fileInput;
    public file: File;

    @Output()
    public formData = new EventEmitter();

    @Output()
    public reportData = new EventEmitter();

    @Input()
    public hasError: boolean;

    public formGroup: FormGroup;

    public searchOption: String;
    public searchField: FormControl;
    public rows: Array<any>;
    public page: any;
    public selected: Array<any>;
    public searchOptions: Array<any>;
    public cssClasses: any;
    public loadingIndicator: boolean;
    public barcodeLoadingIndicator: boolean;
    public fileLoaded: boolean;

    constructor(@Inject(APP_CONFIG) private config: IAppConfig,
                private formBuilder: FormBuilder,
                private foodService: FoodService) {
        this.fileLoaded = false;
        this.loadingIndicator = true;
        this.barcodeLoadingIndicator = false;
        this.searchOption = "food";
        this.selected = [];

        this.page = {
            pageNumber: 0,
            size: 20,
            totalElements: 0
        };

        this.searchOptions = [
            {name: 'food', label: 'My Food', sourceID: 1},
            {name: 'tesco', label: 'Tesco', sourceID: 3},
            {name: 'open-food-facts', label: 'OpenFoodFacts', sourceID: 4}
        ];

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
            foodItemFormArray: this.formBuilder.array([], Validators.required),
        });
        this.setPage({offset: 0});
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
        this.foodService.getAllUsingQuery("", this.searchOption).subscribe(response => {
            this.rows = this.transformData(response);
            this.page.totalElements = this.rows.length;
            this.loadingIndicator = false;
        });
    }

    transformData(data) {
        let dataRows: Array<any> = [];
        for (let entry of data) {
            let rowData = {
                'id': entry.externalId,
                'name': entry.name,
                'label': entry.name,
                'servingSize': entry.nutritionalInformation.servingSize + ' g',
                'calories': entry.nutritionalInformation.calories + ' kcal',
                'source': this.searchOption
            };

            if (this.searchOption === "food")
                rowData.id = entry.id;

            if (this.searchOption === "tesco")
                rowData.id = entry.gtin;

            if (this.searchOption === "open-food-facts")
                rowData.id = entry.gtin;

            dataRows.push(rowData);
        }
        return dataRows;
    }

    onSelect(selected) { // either add or remove
        selected = selected.selected;
        let controls = <FormArray>this.formGroup.controls['foodItemFormArray'];
        let updatedControls = this.formBuilder.array([]);

        for (let i = 0; i < controls.length; i++) {
            let myFormGroup: FormGroup = <FormGroup>controls.controls[i];
            let barcodeValue = myFormGroup.controls["barcodeItem"].value;

            if (barcodeValue == false) {
                updatedControls.push(myFormGroup);
            }
        }

        if (updatedControls.length < selected.length) { // Add
            let entry = selected[selected.length - 1];
            let row = this.getRow(entry.$$index, entry.name, entry.id, entry.source, entry.servingSize, 1.0, '', false);
            controls.push(row);
            this.updateOutput();

        } else { // Remove

            let foundNgxIds = Array();
            for (let entry of selected) {
                foundNgxIds.push(entry.$$index);
            }

            for (let i = 0; i < controls.length; i++) {
                let myFormGroup: FormGroup = <FormGroup>controls.controls[i];

                let barcodeValue = myFormGroup.controls["barcodeItem"].value;
                if (barcodeValue == true) {
                    continue;
                }

                let ngxValue = myFormGroup.controls["ngxIndex"].value;
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
        this.foodService.getAllUsingQuery(query, this.searchOption)
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
        this.foodService.getAll().subscribe(
            response => {
                this.rows = this.transformData(response);
                this.loadingIndicator = false;
            }
        );
    }

    fileChange(files: any) {

        this.barcodeLoadingIndicator = true;
        let file = this.fileInput.nativeElement.files[0];
        let formData = new FormData();
        formData.append('uploadFile', file, file.name);

        this.foodService.getFoodItemFromBarcodeImage(formData).subscribe(
            response => {
                this.barcodeLoadingIndicator = false;
                console.log("Success from barcode");
                console.log(response);
                this.handleBarcodeSuccess(response);
            },
            err => {
                this.barcodeLoadingIndicator = false;
                this.reportData.emit(this.getReportFromBarcodeResponse(err, "Barcode reader error"));
            }
        );

        this.fileInput.nativeElement.value = "";
    }

    getReportFromBarcodeResponse(errorResponse: any, title: string) {
        let report = {
            "type": "error",
            "title": title,
            "content": ""
        };
        if (errorResponse.status == 400) {
            report.content = this.config.errorCodes.foodSelection.failedToReadBarcodeError;
        } else if (errorResponse.status == 404) {
            report.content = this.config.errorCodes.foodSelection.failedToFindFoodItemFromBarcodeError;
        }
        return report;
    }

    handleBarcodeSuccess(response) {
        let controls = <FormArray>this.formGroup.controls['foodItemFormArray'];
        let row = this.getRow(null, response.name, response.gtin, response.foodItemType, response.nutritionalInformation.servingSize, 1.0, '', true);

        for (let i = 0; i < controls.length; i++) {
            let myFormGroup: FormGroup = <FormGroup>controls.controls[i];
            let idValue = myFormGroup.controls["id"].value;
            if (response.gtin === idValue) {
                return;
            }
        }

        controls.push(row);
        this.updateOutput();
    }

    getRow(ngxIndex, name, id, source, servingSize, servings, weight, barcodeItem): FormGroup {
        return this.formBuilder.group({
            ngxIndex: [ngxIndex],
            name: [name],
            id: [id],
            source: [source],
            servingSize: [servingSize],
            servings: [servings],
            weight: [weight],
            barcodeItem: [barcodeItem]
        });
    }

    remove(index: number) {

        let controls = <FormArray>this.formGroup.controls['foodItemFormArray'];
        controls.removeAt(index);
        this.updateOutput();

    }
}