import { Injectable, Inject } from '@angular/core';
import { Router } from '@angular/router';
import { Http, Headers, RequestOptions, URLSearchParams } from '@angular/http';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { tokenNotExpired, JwtHelper } from 'angular2-jwt';
import { Subject, Observable } from 'rxjs';

@Injectable()
export class AuthService {

  tokenEndpoint = this.config.apiEndpoint + 'auth';
  requireLoginSubject: Subject<boolean>;
  tokenIsBeingRefreshed: Subject<boolean>;
  lastUrl: string;
  jwtHelper: JwtHelper = new JwtHelper();

  constructor(private http: Http, private router: Router, @Inject(APP_CONFIG) private config: IAppConfig) {
    this.requireLoginSubject = new Subject<boolean>();
    this.tokenIsBeingRefreshed = new Subject<boolean>();
    this.tokenIsBeingRefreshed.next(false);
    this.lastUrl = "/";
  }

  isUserAuthenticated() {

    if (this.loggedIn()) {
      this.requireLoginSubject.next(false);
      return true;
    } else {
      return false;
    }
  }

  login(username: string, password: string) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json');
    headers.append('X-Requested-With', 'XMLHttpRequest');
    let options = new RequestOptions({ headers: headers });

    let body = JSON.stringify({
      username: username,
      password: password,
      client_id: '099153c2625149bc8ecb3e85e03f0022',
      grant_type: 'password'
    })

    return this.http.post(`${this.tokenEndpoint}/login`, body, options).map(res => res.json());
  }

  loggedIn() {
    return tokenNotExpired('id_token');
  }

  addTokens(accessToken: string, refreshToken: string) {
    localStorage.setItem(this.config.jwtAccessTokenKey, accessToken);
    localStorage.setItem(this.config.jwtRefreshTokenKey, refreshToken);
  }

  getRefreshTokenExpirationDate() {
    var token = localStorage.getItem('id_token');
    if (token) {
      let tokenExpDate = this.jwtHelper.getTokenExpirationDate(token);
      let sessionExpDate = new Date(tokenExpDate.getTime() + 4 * 60000);
      if (new Date() > sessionExpDate) {
        this.logout();
      }
      return sessionExpDate;
    }
    return null;
  }

  hasRefreshToken() {
    let refToken = localStorage.getItem('refresh_token');
    if (refToken == null) {
      this.logout();
    }
    return refToken != null;
  }

  refreshTokenSuccessHandler(data) {
    if (data.error) {
      console.log("Removing tokens.");
      this.logout();
      this.requireLoginSubject.next(true);
      this.tokenIsBeingRefreshed.next(false);
      this.router.navigateByUrl('/unauthorized');
      return false;
    } else {
      this.addTokens(data.access_token, data.refresh_token);
      this.requireLoginSubject.next(false);
      this.tokenIsBeingRefreshed.next(false);
      console.log("Refreshed user token");
    }
  }

  refreshTokenErrorHandler(error) {
    this.requireLoginSubject.next(true);
    this.logout();
    this.tokenIsBeingRefreshed.next(false);
    this.router.navigate(['/sessiontimeout']);
    console.log(error);
  }

  refreshToken() {
    let refToken = localStorage.getItem('refresh_token');
    //let refTokenId = this.jwtHelper.decodeToken(refToken).refreshTokenId;
    let headers = new Headers({ 'Content-Type': 'application/x-www-form-urlencoded' });
    let options = new RequestOptions({ headers: headers });
    let body = new URLSearchParams();
    body.set('client_id', '099153c2625149bc8ecb3e85e03f0022');
    body.set('grant_type', 'refresh_token');
    body.set('refresh_token', refToken);

    return this.http.post(this.tokenEndpoint, body, options)
      .map(res => res.json());
  }

  tokenRequiresRefresh(): boolean {
    if (!this.loggedIn()) {
      console.log("123 Token refresh is required");
    }

    return !this.loggedIn();
  }

  logout() {
    localStorage.removeItem('id_token');
    localStorage.removeItem('refresh_token');
    this.requireLoginSubject.next(true);
  }
}