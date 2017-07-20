import { Component, OnInit } from '@angular/core';
import { NotificationsService } from 'angular2-notifications';
import { DiaryService, GoalsService } from '../../_services';
import { SharedData } from '../../_providers/shared-data.provider';


@Component({
    selector: 'diary',
    templateUrl: 'diary.template.html',
    providers: [DiaryService, GoalsService]
})
export class DiaryComponent implements OnInit {

    public diaryEntries: Array<any>;
    public goalProfile: any;
    public isGoalProfileSet: boolean = false;;

    public currentDate: Date;

    public barChartLabels: string[] = ['2006', '2007', '2008', '2009', '2010', '2011'];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = false;

    public pieChartType: string = 'pie';
    public pieChartLegend: boolean = false;
    public pieChartLabels: string[] = ['Carbs', 'Fat', 'Protein'];
    public chartMap: Map<number, number[]>;

    public notificationOptions: any = {
        position: ["top", "right"],
        lastOnBottom: true,
        timeOut: 3000,
    };

    public barChartColors: Array<any> = [
        {
            // Success color: 'rgb(248, 172, 89) '
            // Warning color: 'rgb(248, 172, 89)'
            backgroundColor: [
                'rgba(255, 99, 132, 0.4)',
                'rgba(54, 162, 235, 0.4)',
                'rgba(255, 206, 86, 0.4)',
                'rgba(0, 255, 0, 0.4)',
                'rgba(102, 0, 204, 0.4)',
                'rgba(255, 128, 0, 0.4)'
            ]
        }
    ];

    public barChartData: any[] = [
        { data: [65, 59, 80, 81, 56, 55, 56] },
        { data: [69, 79, 84, 55, 56, 55, 56] }
    ];

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
        private diaryService: DiaryService,
        private goalService: GoalsService,
        private notificationsService: NotificationsService,
        private sharedData: SharedData) {

        this.chartMap = new Map<number, number[]>();
        this.goalProfile = {};
    }

    ngOnInit() {
        this.currentDate = new Date();
        //this.loadDiaryEntriesTotal();
        this.loadDiaryEntries(this.currentDate);
        this.loadNutritionGoalValues();

        setTimeout(() => {
            this.processNotifications();
        }, 500);
    };

    loadNutritionGoalValues() {
        this.goalService.getGoalProfile().subscribe(
            data => {
                this.goalProfile = data;
                this.isGoalProfileSet = true;
            },
            error => {
                console.log("error");
                this.isGoalProfileSet = false;
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

    loadDiaryEntries(date: Date) {
        this.diaryService.getEntriesByDate(date).subscribe(
            data => {
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
        }
    }

    test() {
        console.log("click");
        this.notificationsService.success(
            'Diary entry successfully added',
            'Time: ',
            {
                showProgressBar: true,
                pauseOnHover: false,
                clickToClose: true,
                maxLength: 10
            }
        )
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

}