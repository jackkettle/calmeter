import { Component, OnInit } from '@angular/core';
import { GoalsService } from '../../_services';


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


    constructor(private goalsService: GoalsService) { }

    ngOnInit() {

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
        }

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
            data => {
                console.log("success");
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
}