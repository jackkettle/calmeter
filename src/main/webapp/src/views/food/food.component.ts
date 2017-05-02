import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { FoodService } from './food.service';

import 'rxjs/Rx';

@Component({
    selector: 'mianView',
    templateUrl: 'food.template.html',
    providers: [FoodService]
})
export class FoodComponent implements OnInit {

    public foodItems: Array<any>;

    public pieChartType: string = 'pie';
    public pieChartLegend: boolean = false;
    public pieChartLabels: string[] = ['Carbs', 'Fat', 'Protein'];
    public pieChartData: number[] = [300, 500, 100];
    public chartMap: Map<number, number[]>;

    public toDeleteID: number;

    constructor(private foodService: FoodService, private router: Router) {
        this.chartMap = new Map<number, number[]>()
    }

    ngOnInit() {
        this.loadFoodValues();
    }

    delete(id: number) {
        this.foodService.delete(id)
            .subscribe(
            () => { this.router.navigate(['/food']); this.loadFoodValues(); },
        );
    }

    populateChartMap(data: any) {
        if (data === null)
            return;

        for (let entry of data) {
            let carbs: number = entry.nutritionalInformation.consolidatedCarbs.total;
            let fats: number = entry.nutritionalInformation.consolidatedFats.totalFat;
            let protein: number = entry.nutritionalInformation.consolidatedProteins.protein;
            let chartData: number[] = [carbs, fats, protein];
            this.chartMap.set(entry.id, chartData);
        }
    }

    loadFoodValues() {
        this.foodService.getAllFood()
            .subscribe(data => { this.foodItems = data; this.populateChartMap(data) });
    }
}
