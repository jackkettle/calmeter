import { Injectable, Inject } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class DiaryService {

    constructor(@Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'diary';

    getEntries(): Observable<Response[]> {

        return this.authHttpService.get(`${this.apiUrl}/allEntries`)
            .map((res: Response) => res.json());
    }

    addEntry(body: Object): Observable<Response[]> {

        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(`${this.apiUrl}/createEntry`, bodyString)
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }

}