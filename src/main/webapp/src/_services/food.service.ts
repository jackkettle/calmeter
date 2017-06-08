import { Injectable, Inject } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthenticationService } from './authentication.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class FoodService {

    constructor(private http: Http, @Inject(APP_CONFIG) private config: IAppConfig, private authenticationService: AuthenticationService) { }

    private apiUrl = this.config.apiEndpoint + 'food';

    getAllFood(): Observable<Response[]> {
        let options = new RequestOptions({ headers: this.createHeader() });

        return this.http.get(this.apiUrl, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    getFood(body: Object): Observable<Response[]> {
        let options = new RequestOptions({ headers: this.createHeader() });

        return this.http.get(`${this.apiUrl}/${body['id']}`, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }

    addFood(body: Object): Observable<Response[]> {

        console.log("yay!");

        let bodyString = JSON.stringify(body);
        let options = new RequestOptions({ headers: this.createHeader() });

        console.log(body);

        return this.http.post(this.apiUrl, body, options)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error.json().error || 'Server error'));
    }


    createHeader() {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('X-Authorization', 'Bearer ' + this.authenticationService.token);
        return headers;
    }

}