import { Injectable } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class DiaryService {

    constructor(private http: Http) { }

    private diaryApiUrl = 'http://localhost:8080/api/diary';

    getEntries(): Observable<Response[]> {
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.get(`${this.diaryApiUrl}/allEntries`, options)
            .map((res: Response) => res.json());
    }

    addEntry(body: Object): Observable<Response[]> {

        let bodyString = JSON.stringify(body);
        let headers = new Headers({ 'Content-Type': 'application/json' });
        let options = new RequestOptions({ headers: headers });

        return this.http.post(`${this.diaryApiUrl}/createEntry`, body, options)
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }

}