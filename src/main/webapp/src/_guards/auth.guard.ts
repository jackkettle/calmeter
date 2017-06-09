import { Injectable, Inject } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';

@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router, @Inject(APP_CONFIG) private config: IAppConfig) { }

    canActivate() {
        if (localStorage.getItem(this.config.jwtAccessTokenKey)) {
            // logged in so return true
            return true;
        }

        // not logged in so redirect to login page
        this.router.navigate(['/login']);
        return false;
    }
}