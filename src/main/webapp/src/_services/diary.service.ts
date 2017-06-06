import { Injectable, Inject } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class DiaryService {

    constructor(private http: Http, @Inject(APP_CONFIG) private config: IAppConfig) { }

    private apiUrl = this.config.apiEndpoint + 'food';

    getEntries(): Observable<Response[]> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.get(`${this.apiUrl}/allEntries`, options)
            .map((res: Response) => res.json());
    }

    addEntry(body: Object): Observable<Response[]> {

        let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(`${this.apiUrl}/createEntry`, body, options)
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }

}