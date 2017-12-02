import { Injectable, Inject } from '@angular/core';
import { Response, RequestOptionsArgs, RequestOptions, URLSearchParams } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class MealService {

    constructor( @Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'meal';

    get(id: number): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}/${id}`)
            .map((res: Response) => res.json())
    }

    getAll(): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}/getAll`)
            .map((res: Response) => res.json())
    }

    getAllUsingQuery(query, foodSource): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        params.set('query', query);
        params.set('foodSource', foodSource);

        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/searchFood`, requestOptions)
            .map((res: Response) => res.json())
    }

    delete(id: number): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.delete(`${this.apiUrl}/delete/${id}`, requestOptions)
            .catch((error: any) => Observable.throw(error || 'Server error'));

    }

    create(body: Object): Observable<Response[]> {
        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(`${this.apiUrl}/create`, bodyString)
    }

    update(id: number, body: Object): Observable<Response[]> {
        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(`${this.apiUrl}/update/${id}`, bodyString)
    }

}