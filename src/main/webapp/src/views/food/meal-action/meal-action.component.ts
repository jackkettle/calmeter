import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, FormArray, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { MealService } from '../../../_services/';

@Component({
    selector: 'mealAction',
    templateUrl: 'meal-action.template.html'
})
export class MealActionComponent implements OnInit {
    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private location: Location
    ) { }

    private action: string;
    private id: number;
    private diaryId: number;

    ngOnInit() {

        this.route.params.subscribe(params => {
            this.action = params['action'];
            this.id = params['id'];
            this.diaryId = params['id'];
        });
     }

     backClicked() {
        this.location.back();
    }
}