import {Injectable, Inject} from '@angular/core';
import {Response} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {APP_CONFIG, IAppConfig} from '../_app/app.config';
import {AuthHttpService} from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class UserService {

    constructor(@Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) {
    }

    private apiUrl = this.config.apiEndpoint + 'user';

    getThisUser(): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}`)
            .map((res: Response) => res.json())
            .catch((error: any) => Observable.throw(error || 'Server error'));
    }

    getTaskList(): Observable<Response[]> {
        return this.authHttpService.get(`${this.apiUrl}/taskList`)
            .map((res: Response) => res.json())}


    edit(body: Object): Observable<Response[]> {
        let bodyString = JSON.stringify(body);
        return this.authHttpService.post(`${this.apiUrl}/edit`, bodyString);
    }

}