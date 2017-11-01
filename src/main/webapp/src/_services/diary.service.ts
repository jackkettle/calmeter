import { Injectable, Inject } from '@angular/core';
import { Response, RequestOptionsArgs, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class DiaryService {

    constructor( @Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'diary';

    get(id: number) {

        let params: URLSearchParams = new URLSearchParams();
        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/${id}`, requestOptions)
            .map((res: Response) => res.json());
    }


    getLast7Days(date: Date) {
        let params: URLSearchParams = new URLSearchParams();
        params.set('date', date.toISOString());

        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/getLast7Days`, requestOptions)
            .map((res: Response) => res.json());
    }

    getTotalDiaryNutritionByDate(date: Date): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        params.set('date', date.toISOString());

        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/getTotalDiaryNutritionByDate`, requestOptions)
            .map((res: Response) => res.json());
    }

    getEntriesByDate(date: Date): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        params.set('date', date.toISOString());

        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/getEntriesByDate`, requestOptions)
            .map((res: Response) => res.json());
    }

    addEntry(body: Object): Observable<Response[]> {

        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(`${this.apiUrl}/createEntry`, bodyString)
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }

    deleteEntry(id: number): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.delete(`${this.apiUrl}/deleteEntry/${id}`, requestOptions)
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }


}