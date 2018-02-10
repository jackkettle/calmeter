import {Component, OnInit} from '@angular/core';
import {AuthService} from "../_services/auth.service";

import {correctHeight, detectBody} from './app.helpers';

// Core vendor styles
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import '../../node_modules/font-awesome/css/font-awesome.css'
import '../../node_modules/animate.css/animate.min.css'

declare var jQuery: any;

@Component({
    selector: 'app',
    templateUrl: 'app.component.html',
    providers: [AuthService]
})

export class AppComponent {

    ngOnInit() {
    }

    ngAfterViewInit() {
        // Run correctHeight function on load and resize window event
        jQuery(window).bind("load resize", function () {
            correctHeight();
            detectBody();
        });

        // Correct height of wrapper after metisMenu animation.
        jQuery('.metismenu a').click(() => {
            setTimeout(() => {
                correctHeight();
            }, 300)
        });
    }

}
