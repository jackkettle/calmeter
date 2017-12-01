import {Component, OnInit} from '@angular/core';
import {UserService} from "../../_services/";

@Component({
    selector: 'user-selector',
    templateUrl: 'user.template.html',
    providers: [UserService]
})

export class UserComponent implements OnInit {

    private userModel: any;

    constructor(
        private userService: UserService
    ) {

    }

    ngOnInit() {
        this.callValues();
    }

    callValues() {
        this.userService.getThisUser().subscribe(
            data => {
                this.userModel = data;
            }
        );
    }
}