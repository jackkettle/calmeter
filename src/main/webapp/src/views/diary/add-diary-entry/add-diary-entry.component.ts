import { Component, OnInit } from '@angular/core';

import * as $ from "jquery";

@Component({
    selector: 'addDiaryEntry',
    templateUrl: 'add-diary-entry.template.html'
})
export class AddDiaryEntryComponent implements OnInit {

    public currentDate: Date;

    ngOnInit() {

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

}