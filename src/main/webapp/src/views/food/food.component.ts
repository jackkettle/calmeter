import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {FoodService, MealService} from '../../_services/';

import 'rxjs/Rx';

@Component({
    selector: 'mianView',
    templateUrl: 'food.template.html',
    providers: [FoodService, MealService]
})
export class FoodComponent implements OnInit {

    public foodItems: Array<any>;
    public meals: Array<any>;

    public pieChartType: string = 'pie';
    public pieChartLegend: boolean = false;
    public pieChartLabels: string[] = ['Protein', 'Fat', 'Carbs'];

    public foodChartMap: Map<number, number[]>;
    public mealChartMap: Map<number, number[]>;

    public toDeleteID: number;
    public toDeleteType: string;

    constructor(private foodService: FoodService,
                private mealService: MealService,
                private router: Router) {

        this.foodChartMap = new Map<number, number[]>()
        this.mealChartMap = new Map<number, number[]>()
    }

    ngOnInit() {
        this.loadFoodValues();
        this.loadMealValues();
    }

    delete(id: number) {

        if (this.toDeleteType === 'foodItem') {
            this.foodService.delete(id)
                .subscribe(
                    () => {
                        this.router.navigate(['/food']);
                        this.loadFoodValues();
                    },
                );
        }else if(this.toDeleteType === 'meal'){
            this.mealService.delete(id)
                .subscribe(
                    () => {
                        this.router.navigate(['/food']);
                        this.loadFoodValues();
                    },
                );
        }
    }

    populateChartMap(data: any, chartMap: Map<number, number[]>) {
        if (data === null)
            return;

        for (let entry of data) {
            let carbs: number = entry.nutritionalInformation.consolidatedCarbs.total;
            let fats: number = entry.nutritionalInformation.consolidatedFats.totalFat;
            let protein: number = entry.nutritionalInformation.consolidatedProteins.protein;
            let chartData: number[] = [protein, fats, carbs];
            chartMap.set(entry.id, chartData);
        }
    }

    removeDisabledItems(data: Array<any>) {
        return data.filter(item => !item.disabled);
    }

    loadFoodValues() {
        this.foodService.getAll()
            .subscribe(data => {
                data = this.removeDisabledItems(data);
                this.foodItems = data;
                this.populateChartMap(data, this.foodChartMap)
            });
    }

    loadMealValues() {
        this.mealService.getAll()
            .subscribe(data => {
                data = this.removeDisabledItems(data);
                this.meals = data;
                this.populateChartMap(data, this.mealChartMap)
            });
    }
}
