import {AfterViewInit, Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {UserService} from "../../../_services";

declare var jQuery: any;

@Component({
    selector: 'navigation',
    templateUrl: 'navigation.template.html',
    providers: [UserService]
})

export class NavigationComponent implements AfterViewInit, OnInit {

    private userModel: any;

    constructor(private router: Router, private userService: UserService) {
    }

    ngOnInit() {
        this.userService.getThisUser().subscribe(
            data => {
                this.userModel = data;
                jQuery('#side-menu').metisMenu();
            }
        )
    }

    ngAfterViewInit() {
        jQuery('#side-menu').metisMenu();
    }

    activeRoute(routename: string): boolean {
        return this.router.url.indexOf(routename) > -1;
    }


}