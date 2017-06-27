import { Injectable, Inject } from '@angular/core';
import { Router, CanActivate } from '@angular/router';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthService } from '../_services/';


@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router, @Inject(APP_CONFIG) private config: IAppConfig, private authService: AuthService) { }

    canActivate() {
        if (localStorage.getItem(this.config.jwtAccessTokenKey)) {
            if (this.authService.loggedIn())
                return true;
        }

        this.router.navigate(['/login']);
        return false;
    }
}