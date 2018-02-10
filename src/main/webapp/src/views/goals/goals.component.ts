import {Component, Inject, OnInit} from '@angular/core';
import {GoalsService} from '../../_services';
import {NotificationsService} from "angular2-notifications/index";
import {APP_CONFIG, IAppConfig} from "../../_app/app.config";


@Component({
    selector: 'goals',
    templateUrl: './goals.template.html',
    providers: [GoalsService]
})
export class GoalsComponent implements OnInit {

    private activityLevels: Array<any>;
    private weightGoals: Array<any>;
    private userBMR: any;
    private activityLevel: any;
    private weightGoal: any;
    private ratios: Array<any>;
    private chosenRatio: any;
    private goalProfile: any;

    public notificationOptions: any;


    constructor(@Inject(APP_CONFIG) private config: IAppConfig,
                private goalsService: GoalsService,
                private notificationsService: NotificationsService) {
    }

    ngOnInit() {

        this.notificationOptions = this.config.toastNotificationOptions;

        this.activityLevel = {
            id: '',
            text: ''
        };

        this.weightGoal = {
            id: '',
            text: ''
        };

        this.chosenRatio = {
            id: 0
        };

        this.chosenRatio = {
            id: 0
        };

        this.getUserBMR();
        this.getActivityLevels();
        this.getWeightGoals();
        this.getRatios();

        this.loadUserGoals();
    }

    save() {
        console.log(this.weightGoal);
        console.log(this.activityLevel);
        console.log(this.chosenRatio);

        let payload = {
            userBMR: this.userBMR,
            activityLevel: this.activityLevel.text,
            weightGoal: this.weightGoal.text,
            ratio: this.chosenRatio.id
        };

        this.goalsService.setGoalProfile(payload).subscribe(
            () => {
                this.successNotification();
            },
            () => {
                this.errorNotification();
            }
        );
    }

    getUserBMR() {
        this.goalsService.getUserBMR().subscribe(
            data => {
                console.log("BMR: " + data);
                this.userBMR = data;
            }
        );
    }

    choseRatio(ratio) {
        this.chosenRatio = ratio;
    }

    loadUserGoals() {
        this.goalsService.getGoalProfile().subscribe(
            data => {
                this.applyUserValues(data);
                this.goalProfile = data;
            }
        )
    }

    applyUserValues(data) {
        this.activityLevel = {
            id: data.activityLevel,
            text: data.activityLevel
        };
        this.weightGoal = {
            id: data.weightGoal,
            text: data.weightGoal
        };
        this.chosenRatio = data.nutritionalRatio;

    }

    getActivityLevels() {

        this.goalsService.getActivityLevels().subscribe(
            data => {
                this.activityLevels = this.formatOptions(data);
            }
        );
    }

    getWeightGoals() {

        this.goalsService.getWeightGoals().subscribe(
            data => {
                this.weightGoals = this.formatOptions(data);
            }
        );
    }

    getRatios() {
        this.goalsService.getRatios().subscribe(
            data => {
                this.ratios = data;
            }
        );
    }

    formatOptions(data): Array<any> {
        let opts = new Array(data.length);
        for (let i = 0; i < data.length; i++) {
            let label: string = data[i];
            let value: string = data[i].toUpperCase().replace(" ", "_");
            opts[i] = {
                id: value,
                text: label
            };
        }
        return opts;
    }

    onActivitySelected(data) {
        this.activityLevel = data;
    }

    onWeightSelected(data) {
        this.weightGoal = data;
    }

    successNotification() {
        this.notificationsService.success(
            'Goal settings saved',
            'Time: ' + new Date().toLocaleTimeString(),
            {
                showProgressBar: true,
                pauseOnHover: false,
                clickToClose: true,
                maxLength: 10
            }
        )
    }

    errorNotification() {
        this.notificationsService.error(
            'Unable to save goal settings',
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