import {Component, OnInit} from '@angular/core';
import {UserService} from "../../_services/";

declare var $: any;

@Component({
    selector: 'user-selector',
    templateUrl: 'user.template.html',
    providers: [UserService]
})

export class UserComponent implements OnInit {

    private userModel: any;
    private currentWeight: any;

    constructor(private userService: UserService) {
        this.currentWeight = 0;
    }

    ngOnInit() {
        this.callValues();
    }

    callValues() {
        this.userService.getThisUser().subscribe(
            data => {
                console.log(data);
                this.userModel = data;
                this.initSparkLine(this.formatWeightValues(data));
            }
        );
    }

    formatWeightValues(data: any): Array<any> {

        let weightArray: Array<any> = data.userProfile.weightLog;
        weightArray.sort((left, right): number => {
            if (new Date(left.dateTime) < new Date(right.dateTime)) return -1;
            if (new Date(left.dateTime) > new Date(right.dateTime)) return -1;
            return 0;
        });

        this.currentWeight = weightArray[weightArray.length - 1].weightInKgs;

        let sparkLineValues = [];
        weightArray.forEach(function (element) {
            sparkLineValues.push({
                'value': element.weightInKgs,
                'label': element.weightInKgs + ' kgs - ' + new Date(element.dateTime).toLocaleDateString()
            });
        });

        return sparkLineValues;
    }

    initSparkLine(data: Array<any>) {

        let labels: any = [];
        let values = [];
        data.forEach(function (element) {
            values.push(element.value);
            labels.push(element.label);
        });

        $("#sparkline1").sparkline(values, {
            type: 'line',
            width: '100%',
            height: '50',
            lineColor: '#1ab394',
            fillColor: "transparent",
            tooltipFormat: '{{offset:offset}} {{value}}',
            tooltipValueLookups: {
                'offset': labels
            },
        });
    }
}