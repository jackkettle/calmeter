import { Component, OnInit } from '@angular/core';
import { FoodService } from '../../food/food.service';


import * as $ from "jquery";

@Component({
    selector: 'addDiaryEntry',
    templateUrl: 'add-diary-entry.template.html',
    providers: [FoodService]
})
export class AddDiaryEntryComponent implements OnInit {

    public currentDate: Date;

    public searchOption: String;

    rows = [
        { name: 'Austin', gender: 'Male', company: 'Swimlane' },
        { name: 'Dany', gender: 'Male', company: 'KFC' },
        { name: 'Molly', gender: 'Female', company: 'Burger King' },
    ];
    columns = [
        { prop: 'name' },
        { name: 'Gender' },
        { name: 'Company' }
    ];

    searchOptions = [
        { name: 'food', label: 'My Food'},
        { name: 'recipes', label: 'My Recipes'}
    ]

    ngOnInit() {

        this.searchOption = "food";

        this.currentDate = new Date();
        var formattedDate = this.currentDate.getHours() + ":" + this.currentDate.getMinutes();

        $('.datepicker').datepicker({
            todayBtn: true,
            todayHighlight: true
        });

        $('.timepicker-input').timepicker({
            showInputs: false,
            defaultTime: formattedDate
        });

    }

    assignSearchOption(searchOption){
        this.searchOption = searchOption;
    }

}