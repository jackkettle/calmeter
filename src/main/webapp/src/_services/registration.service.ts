import { Injectable, Inject } from '@angular/core';
import { Response, Http } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class RegistrationService {

    constructor( @Inject(APP_CONFIG) private config: IAppConfig, private http: Http) { }

    private apiUrl = this.config.apiEndpoint + 'auth/registration';

    register(model: any): Observable<Response[]> {
        return this.http.post(`${this.apiUrl}`, model)
            .map(this.extractData)
    }

    private extractData(res: Response) {        
        return res.text() ? res.json() : {}; ;
    }

}