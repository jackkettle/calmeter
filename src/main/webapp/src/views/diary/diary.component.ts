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

    ngOnInit() {
        this.currentDate = new Date();
    };

}