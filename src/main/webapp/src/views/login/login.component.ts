import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { AuthService } from '../../_services/auth.service';
import 'rxjs/add/operator/catch';

@Component({
    selector: 'login',
    templateUrl: 'login.template.html',
    providers: [AuthService]
})

export class LoginComponent implements OnInit {

    model: any = {};
    loading = false;
    error = '';

    constructor(
        private router: Router,
        private authService: AuthService) { }

    ngOnInit() {
        this.authService.logout();
    }

    login() {
        this.loading = true;
        this.authService.login(this.model.username, this.model.password).subscribe(
            result => {
                console.log(result);
                this.authService.addTokens(result.token, result.refreshToken);
                this.router.navigate(['/dashboard']);
            },
            (error) => {
                this.error = 'Username or password is incorrect';
                this.loading = false;
            });
    }
}