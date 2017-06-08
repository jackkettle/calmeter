import { Injectable, Inject } from '@angular/core';
import { Http, Response, Headers, RequestOptions } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import 'rxjs/add/operator/map'
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthenticationService {
    public token: string;

    private apiUrl = this.config.apiEndpoint + 'auth';

    constructor(private http: Http, @Inject(APP_CONFIG) private config: IAppConfig) {
        var currentUser = JSON.parse(localStorage.getItem('currentUser'));
        this.token = currentUser && currentUser.token;
    }

    createHeader() {
        var headers = new Headers();
        headers.append('Content-Type', 'application/json');
        headers.append('X-Requested-With', 'XMLHttpRequest');
        return headers;
    }

    login(username: string, password: string): Observable<boolean> {

        console.log("AuthenticationService.login");
        console.log("username: " + username);
        console.log("password: " + password);

        let options = new RequestOptions({ headers: this.createHeader() });
        let body = JSON.stringify({ username: username, password: password });

        return this.http.post(`${this.apiUrl}/login`, body, options)
            .map((response: Response) => {
                let token = response.json() && response.json().token;
                if (token) {
                    this.token = token;

                    var jsonAuthData = JSON.stringify({ username: username, token: token });
                    console.log(jsonAuthData);
                    localStorage.setItem('currentUser', jsonAuthData);
                    return true;
                } else {
                    return false;
                }
            })
            .catch(e => {
                if (e.status === 401) {
                    return Observable.throw('Unauthorized');
                }
            });
    }

    logout(): void {
        this.token = null;
        localStorage.removeItem('currentUser');
    }
}