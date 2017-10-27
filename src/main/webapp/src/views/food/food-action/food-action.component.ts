import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { FoodService } from '../../../_services/food.service';


import * as $ from "jquery";


@Component({
    selector: 'foodAction',
    providers: [FoodService],
    templateUrl: 'food-action.template.html'

})
export class FoodActionComponent implements OnInit {

    id: number;
    action: string;

    formGroup: FormGroup;

    vitaminFormArray: FormArray;
    mineralFormArray: FormArray;

    vitaminSelectArrayRemoved: Array<any> = [];;
    mineralSelectArrayRemoved: Array<any> = [];;

    vitaminArray: Array<any> = [];
    mineralArray: Array<any> = [];

    vitaminSelectArray: Array<any> = [];
    mineralSelectArray: Array<any> = [];

    constructor(
        private formBuilder: FormBuilder,
        private foodService: FoodService,
        private route: ActivatedRoute) { }

    ngOnInit() {

        this.route.params.subscribe(params => {
            this.action = params['action'];
            this.id = +params['id'];

            if (this.action === "edit") {
                this.loadInitFormValues(this.id);
            }
        });

        $(".touchspinWhole").TouchSpin({
            min: 0,
            max: 5000,
            step: 1,
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });

        $(".touchspin2Decimal").TouchSpin({
            min: 0,
            max: 5000,
            step: .01,
            decimals: 2,
            buttondown_class: 'btn btn-white',
            buttonup_class: 'btn btn-white'
        });

        this.vitaminArray = [
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
        this.mineralArray = [
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

        let opts = new Array(this.vitaminArray.length);
        for (let i = 0; i < this.vitaminArray.length; i++) {
            opts[i] = {
                value: this.vitaminArray[i],
                label: this.vitaminArray[i]
            };
        }
        this.vitaminSelectArray = opts.slice(0);

        opts = new Array(this.mineralArray.length);
        for (let i = 0; i < this.mineralArray.length; i++) {
            opts[i] = {
                value: this.mineralArray[i],
                label: this.mineralArray[i]
            };
        }
        this.mineralSelectArray = opts.slice(0);

        this.initForm();

    }

    loadInitFormValues(id: number) {
        this.foodService.getFood(id).subscribe(
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

        this.formGroup = this.formBuilder.group({
            name: [data.name, [Validators.required, Validators.minLength(2)]],
            calsPer100: [data.nutritionalInformation.calories],
            servingSize: [data.nutritionalInformation.servingSize],
            totalfat: [data.nutritionalInformation.consolidatedFats.totalFat],
            saturatedFat: [data.nutritionalInformation.consolidatedFats.saturatedFat],
            polyUnsaturatedFat: [data.nutritionalInformation.consolidatedFats.polyUnsaturatedFat],
            monoUnsaturatedFat: [data.nutritionalInformation.consolidatedFats.monoUnsaturatedFat],
            cholesterol: [data.nutritionalInformation.consolidatedFats.cholesterol],
            totalCarbs: [data.nutritionalInformation.consolidatedCarbs.total],
            fiber: [data.nutritionalInformation.consolidatedCarbs.fiber],
            sugar: [data.nutritionalInformation.consolidatedCarbs.sugar],
            protein: [data.nutritionalInformation.consolidatedProteins.protein],
            sodium: [data.nutritionalInformation.mineralMap.SODIUM],
            vitaminFormArray: this.formBuilder.array([
                this.initRow()
            ]),
            mineralFormArray: this.formBuilder.array([
                this.initRow()
            ])
        });
    }

    initForm() {
        this.formGroup = this.formBuilder.group({
            name: ['', [Validators.required, Validators.minLength(2)]],
            calsPer100: [''],
            servingSize: [''],
            totalfat: [''],
            saturatedFat: [''],
            polyUnsaturatedFat: [''],
            monoUnsaturatedFat: [''],
            cholesterol: [''],
            totalCarbs: [''],
            fiber: [''],
            sugar: [''],
            protein: [''],
            sodium: [''],
            vitaminFormArray: this.formBuilder.array([
                this.initRow()
            ]),
            mineralFormArray: this.formBuilder.array([
                this.initRow()
            ])
        });
    }

    initRow() {
        return this.formBuilder.group({
            name: ['', Validators.required],
            value: ['', Validators.required]
        });
    }

    addMineralRow() {
        const control = <FormArray>this.formGroup.controls['mineralFormArray'];
        control.push(this.initRow());
    }

    addVitaminRow() {
        const control = <FormArray>this.formGroup.controls['vitaminFormArray'];
        control.push(this.initRow());
    }

    save(model) {

        if (this.action === "edit") {
            this.foodService.updateFoodItem(this.id, model).subscribe(
                res => {
                    console.log(res);
                }, err => {
                    console.log(err);
                }
            );
        }

        if (this.action === "add") {
            this.foodService.createFoodItem(model).subscribe(
                res => {
                    console.log(res);
                }, err => {
                    console.log(err);
                }
            );
        }

    }

    onSelected(item) {

        if (this.vitaminArray.includes(item.value)) {
            var index = this.getIndexToRemove(item.value, this.vitaminSelectArray);
            if (index < 0)
                return;

            this.vitaminSelectArrayRemoved.push(this.vitaminSelectArray[index]);
            this.vitaminSelectArray.splice(index, 1);
            this.addVitaminRow();

        }

        if (this.mineralArray.includes(item.value)) {
            var index = this.getIndexToRemove(item.value, this.mineralSelectArray);
            if (index < 0)
                return;

            this.mineralSelectArrayRemoved.push(this.mineralSelectArray[index]);
            this.mineralSelectArray.splice(index, 1);
            this.addMineralRow();
        }

    }

    onDeselected(item) {

    }

    getIndexToRemove(item, array: Array<any>) {
        var index = -1;
        for (let i = 0; i < array.length; i++) {
            if (item === array[i].value) {
                index = i;
                break;
            }
        }
        return index;
    }

}
