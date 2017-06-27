import { Injectable, Inject } from '@angular/core';
import { Response, RequestOptionsArgs, RequestOptions, URLSearchParams  } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class FoodService {

    constructor(@Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'food-item';

    getAllFood(): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}/allFoodItems`)
            .map((res: Response) => res.json())
            //.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    getAllFoodByUser(): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}/allFoodItems`)
            .map((res: Response) => res.json())
            //.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    getAllFoodUsingQuery(query, foodSource): Observable<Response[]> {

        let params: URLSearchParams = new URLSearchParams();
        params.set('query', query);
        params.set('foodSource', foodSource);

        let requestOptions = new RequestOptions();
        requestOptions.params = params;

        return this.authHttpService.get(`${this.apiUrl}/searchFood`, requestOptions)
            .map((res: Response) => res.json())
            //.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    getFood(body: Object): Observable<Response[]> {
        let bodyString = JSON.stringify(body);
        return this.authHttpService.get(`${this.apiUrl}/${bodyString['id']}`)
            .map((res: Response) => res.json())
            //.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    addFood(body: Object): Observable<Response[]> {
        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(this.apiUrl, bodyString)
            .map((res: Response) => res.json())
            //.catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

}