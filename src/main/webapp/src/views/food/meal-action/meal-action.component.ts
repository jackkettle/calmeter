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

    handleFormDataUpdated(food: any) {
        this.formGroup.setControl('foodItemFormArray', food);
    }

    handleReportDataUpdated(report: any) {
        if (report && report.type === "error") {
            this.errorNotification(report.title, report.content);
        }
    }

    backClicked() {
        this.location.back();
    }

    save(model) {
        this.mealService.create(model.value).subscribe(
            res => {
                this.successNotification();
            }, err => {
                let content = "";
                if (err.status == 409) {
                    content = this.config.errorCodes.meals.mealAlreadyExistsError;
                }
                else {
                    content = this.config.errorCodes.meals.unknownError;
                }
                this.errorNotification(this.config.errorCodes.meals.errorTitle, content);

            }
        );
    }

    errorNotification(title: string, content?: string) {

        var contentTemp;
        if (content == null)
            contentTemp = 'Time: ' + new Date().toLocaleTimeString();
        else
            contentTemp = content;

        this.notificationsService.error(
            title,
            contentTemp,
            {
                showProgressBar: true,
                pauseOnHover: true,
                clickToClose: false,
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
            }
        )
    }

}