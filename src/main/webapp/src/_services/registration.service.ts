import { Injectable, Inject } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class RegistrationService {

    constructor( @Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'registration';

    register(model: any): Observable<Response[]> {

        console.log("Registering user...");
        console.log(model);

        return this.authHttpService.post(`${this.apiUrl}`, model)
            .map((res: Response) => res.json())
    }
}