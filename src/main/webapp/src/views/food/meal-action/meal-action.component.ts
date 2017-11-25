import {Component, OnInit, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormArray, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {MealService} from '../../../_services/';

@Component({
    selector: 'mealAction',
    templateUrl: 'meal-action.template.html',
    providers: [MealService]
})
export class MealActionComponent implements OnInit {
    constructor(private mealService: MealService,
                private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private location: Location) {
    }

    private action: string;
    private id: number;
    private diaryId: number;

    private formGroup: FormGroup;

    ngOnInit() {

        this.route.params.subscribe(params => {
            this.action = params['action'];
            this.id = params['id'];
            this.diaryId = params['id'];
        });

        // this.formGroup = this.formBuilder.group({
        //     name: ['', [Validators.required, Validators.minLength(2)]],
        //     description: [''],
        //     foodItemFormArray: this.formBuilder.array([]),
        // });

        this.formGroup = this.formBuilder.group({
            name: ['', [Validators.required, Validators.minLength(2)]],
            description: [''],
            foodItemFormArray: this.formBuilder.array([], Validators.required),
        });
    }

    handleFoodUpdated(food: any) {
        this.formGroup.setControl('foodItemFormArray', food);
    }

    backClicked() {
        this.location.back();
    }

    save(model) {

        console.log(model.value);
        this.mealService.create(model.value).subscribe(
            res => {
                console.log(res);
            }, err => {
                console.log(err);
            }
        );
    }
}