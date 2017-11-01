import { Component, OnInit, Injectable, Inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'
import { NotificationsService } from 'angular2-notifications';
import { APP_CONFIG, IAppConfig } from '../../_app/app.config';
import { DiaryService, GoalsService } from '../../_services';
import { SharedData } from '../../_providers/shared-data.provider';

import * as moment from 'moment';

@Component({
    selector: 'diary',
    templateUrl: 'diary.template.html',
    providers: [DiaryService, GoalsService]
})
export class DiaryComponent implements OnInit {

    public diaryEntries: Array<any>;
    public totalDiaryNutrition: any;
    public goalProfile: any;

    public urlDateFormat: string;
    public urlDateTimeFormat: string;

    public currentDate: Date;
    public activeDate: Date;

    public nextDate: Date;
    public prevDate: Date;

    public barChartLabels: string[] = [];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = false;

    public toDeleteID: number;
    public notificationOptions: any;

    public pieChartType: string = 'pie';
    public pieChartLegend: boolean = false;
    public pieChartLabels: string[] = ['Carbs', 'Fat', 'Protein'];
    public pieChartColors: any;
    public chartMap: Map<number, number[]>;
    public chartPercentageMap: Map<number, number[]>;

    public barChartColors: Array<any> = [
        {
            backgroundColor: [
                'rgb(0,188,212)',
                'rgb(0,188,212)',
                'rgb(0,188,212)',
                'rgb(0,188,212)',
                'rgb(0,188,212)',
                'rgb(0,188,212)',
                'rgb(0,188,212)'
            ]
        }
    ];

    public barChartData: any[];

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        scales: {
            xAxes: [{
                stacked: true,
                gridLines: { display: false },
            }],
            yAxes: [{
                stacked: true,
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    constructor(
        @Inject(APP_CONFIG) private config: IAppConfig,
        private diaryService: DiaryService,
        private goalService: GoalsService,
        private notificationsService: NotificationsService,
        private sharedData: SharedData,
        private route: ActivatedRoute,
        private router: Router) {

        this.chartMap = new Map<number, number[]>();
        this.chartPercentageMap = new Map<number, number[]>();
        this.goalProfile = {};
    }

    ngOnInit() {

        this.notificationOptions = this.config.toastNotificationOptions;

        this.urlDateFormat = this.config.urlDateFormat;
        this.urlDateTimeFormat = this.config.urlDateTimeFormat;

        this.route.params.subscribe(params => {

            this.clearCharts();

            this.currentDate = new Date();
            this.activeDate = new Date();

            let paramDate = params['date'];

            console.log(paramDate)

            if (paramDate == null) {
                this.activeDate = new Date();

            } else if (paramDate === 'add') {

                var date = new Date();
                var formattedDate = moment(date).format(this.urlDateTimeFormat);

                console.log(formattedDate)
                setTimeout(() => {
                    this.router.navigate(["./" + formattedDate], { relativeTo: this.route });
                }, 50);
                
            } else {

                let timestamp = new Date(paramDate);
                if (!this.isValidDate(timestamp))
                    timestamp = new Date();

                this.activeDate = timestamp
            }

            this.init(this.activeDate);
        });
    };

    init(date: Date) {

        this.nextDate = new Date();
        this.nextDate.setDate(date.getDate() + 1)
        this.prevDate = new Date();
        this.prevDate.setDate(date.getDate() - 1)

        this.barChartData = [
            { data: [] },
            { data: [] }
        ];

        this.loadTotalDiaryNutrition(date);
        this.loadDiaryEntries(date);
        this.loadNutritionGoalValues();

        setTimeout(() => {
            this.processNotifications();
        }, 500);

        setTimeout(() => {
            this.formatBarLabels(date);
        }, 5);

        this.pieChartColors = [{
            backgroundColor: ['#FBDD97', '#FA9DB0', '#83C3EF']
        }]
    }

    formatBarLabels(date: Date) {

        var newDate = new Date(date.getTime());
        this.barChartLabels = [];

        for (var i = 1; i <= 7; i++) {

            let labelString = newDate.toLocaleDateString("en-IE", { weekday: 'short', day: 'numeric' });
            this.barChartLabels.unshift(labelString);
            newDate.setDate(newDate.getDate() - 1);

        }
    }

    loadLast7DaysHistory(date: Date) {
        this.diaryService.getLast7Days(date).subscribe(
            data => {
                this.barChartData = this.formatBarData(data);
            }
        );
    }

    formatBarData(data) {

        var targetCals = this.goalProfile.nutritionalInformation.calories;
        var returnData = [
            { data: [] },
            { data: [] }
        ];

        Object.keys(data).map(key => {

            let item = data[key];
            let calsLeft = targetCals - item.calories;
            let calsLabel = new Number(item.calories).toFixed(0)
            let calsLeftLabel;

            if (calsLeft > 0) {
                calsLeftLabel = new Number(calsLeft).toFixed(0)
            } else {
                calsLeftLabel = new Number(0);
            }

            returnData[0].data.unshift(calsLabel);
            returnData[1].data.unshift(calsLeftLabel);
        })

        return returnData;

    }

    loadNutritionGoalValues() {
        this.goalService.getGoalProfile().subscribe(
            data => {
                this.goalProfile = data;
                this.loadLast7DaysHistory(this.activeDate);
            },
            error => {
                console.log("error");
            }
        );
    }

    processNotifications() {
        while (this.sharedData.diaryNotificationQueue.length > 0) {
            var notification = this.sharedData.diaryNotificationQueue.pop();
            if (notification.type === "success") {
                this.successNotification(notification);
            }
        }
    }


    loadTotalDiaryNutrition(date: Date) {
        this.diaryService.getTotalDiaryNutritionByDate(date).subscribe(
            data => {
                this.totalDiaryNutrition = data;
            }
        );
    }

    loadDiaryEntries(date: Date) {
        this.diaryService.getEntriesByDate(date).subscribe(
            data => {
                if (data != null)
                    data = data.reverse();

                this.diaryEntries = data;
                this.populateChartMap(data);
            }
        );
    }

    populateChartMap(data: any) {
        if (data === null)
            return;

        for (let entry of data) {
            let carbs: number = entry.totalNutrionalnformation.consolidatedCarbs.total;
            let fats: number = entry.totalNutrionalnformation.consolidatedFats.totalFat;
            let protein: number = entry.totalNutrionalnformation.consolidatedProteins.protein;
            let chartData: number[] = [carbs, fats, protein];
            this.chartMap.set(entry.id, chartData);

            let total = carbs + fats + protein;
            let carbsPercentage: number = (carbs / total) * 100;
            let fatsPercentage: number = (fats / total) * 100;
            let proteinPercentage: number = (protein / total) * 100;
            let chartPercentageData: number[] = [carbsPercentage, fatsPercentage, proteinPercentage];
            this.chartPercentageMap.set(entry.id, chartPercentageData);
        }
    }

    successNotification(notification) {
        this.notificationsService.success(
            'Diary entry successfully added',
            'Time: ' + notification.time,
            {
                showProgressBar: true,
                pauseOnHover: false,
                clickToClose: true,
                maxLength: 10
            }
        )
    }

    clearCharts() {
        this.barChartLabels = [];
    }

    delete(id: number) {
        this.diaryService.deleteEntry(id).subscribe(
            () => {
                console.log("deleted: " + id);
                this.ngOnInit();
            }
        );
    }

    isValidDate(date: Date) {
        if (Object.prototype.toString.call(date) === "[object Date]") {
            if (isNaN(date.getTime())) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
    }

}