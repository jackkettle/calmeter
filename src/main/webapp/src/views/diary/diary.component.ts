import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'diary',
    templateUrl: 'diary.template.html'
})
export class DiaryComponent implements OnInit {

    public currentDate: Date;

    public barChartLabels: string[] = ['2006', '2007', '2008', '2009', '2010', '2011'];
    public barChartType: string = 'bar';
    public barChartLegend: boolean = false;

    public successChartColor: object = {
        backgroundColor: 'rgb(28, 132, 198)',
        borderColor: 'rgb(28, 132, 198)',
        pointBackgroundColor: 'rgba(148,159,177,1)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }

    public warningChartColor: object = {
        backgroundColor: 'rgb(248, 172, 89) ',
        borderColor: 'rgb(248, 172, 89)',
        pointBackgroundColor: 'rgb(248, 172, 89)',
        pointBorderColor: '#fff',
        pointHoverBackgroundColor: '#fff',
        pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    }

    public barChartColors: Array<any> = [
        {
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

    public barChartOptions: any = {
        scaleShowVerticalLines: false,
        responsive: true,
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        }
    };

    public barChartData: any[] = [
        { data: [65, 59, 80, 81, 56, 55, 56] }
    ];

    ngOnInit() {
        this.currentDate = new Date();
    };

}