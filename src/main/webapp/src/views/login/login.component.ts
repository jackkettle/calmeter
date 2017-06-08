import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { AuthenticationService } from '../../_services/authentication.service';
import 'rxjs/add/operator/catch';

@Component({
    selector: 'login',
    templateUrl: 'login.template.html',
    providers: [AuthenticationService]
})

export class LoginComponent implements OnInit {

    model: any = {};
    loading = false;
    error = '';

    constructor(
        private router: Router,
        private authenticationService: AuthenticationService) { }

    ngOnInit() {
        this.authenticationService.logout();
    }

    login() {
        this.loading = true;
        this.authenticationService.login(this.model.username, this.model.password).subscribe(
            (success) => {
                this.router.navigate(['/']);
            },
            (error) => {
                this.error = 'Username or password is incorrect';
                this.loading = false;
            });
    }
}