import {Component, OnInit, Inject} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, FormArray, Validators} from '@angular/forms';
import {ActivatedRoute} from '@angular/router';
import {NotificationsService} from 'angular2-notifications';
import {Location} from '@angular/common';
import {MealService} from '../../../_services/';
import {APP_CONFIG, IAppConfig} from "../../../_app/app.config";

@Component({
    selector: 'mealAction',
    templateUrl: 'meal-action.template.html',
    providers: [MealService]
})
export class MealActionComponent implements OnInit {

    public notificationOptions: any;

    private action: string;
    private id: number;
    private diaryId: number;

    private formGroup: FormGroup;

    constructor(@Inject(APP_CONFIG) private config: IAppConfig,
                private mealService: MealService,
                private formBuilder: FormBuilder,
                private route: ActivatedRoute,
                private location: Location,
                private notificationsService: NotificationsService) {
    }

    ngOnInit() {

        this.notificationOptions = this.config.toastNotificationOptions;

        this.route.params.subscribe(params => {
            this.action = params['action'];
            this.id = params['id'];
            this.diaryId = params['id'];
        });

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
        this.mealService.create(model.value).subscribe(
            res => {
                this.successNotification();
            }, err => {
                if(err.status == 409){
                    this.errorNotification("A meal already exists with that name");
                }
                else{
                    this.errorNotification("Unable to add meal");
                }
            }
        );
    }

    errorNotification(title: string) {
        this.notificationsService.error(
            title,
            'Time: ' + new Date().toLocaleTimeString(),
            {
                showProgressBar: true,
                pauseOnHover: false,
                clickToClose: true,
                maxLength: 10
            }
        )
    }

    successNotification() {
        this.notificationsService.success(
            'Meal item added',
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