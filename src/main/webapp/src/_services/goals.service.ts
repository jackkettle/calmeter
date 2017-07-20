import { Injectable, Inject } from '@angular/core';
import { Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { APP_CONFIG, IAppConfig } from '../_app/app.config';
import { AuthHttpService } from './auth-http.service';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class GoalsService {

    constructor( @Inject(APP_CONFIG) private config: IAppConfig, private authHttpService: AuthHttpService) { }

    private apiUrl = this.config.apiEndpoint + 'goals';

    getUserBMR() {
        return this.authHttpService.get(`${this.apiUrl}/getUserBMR`)
            .map((res: Response) => res.json())
    }

    getActivityLevels() {
        return this.authHttpService.get(`${this.apiUrl}/getActivityLevels`)
            .map((res: Response) => res.json())
    }

    getWeightGoals() {
        return this.authHttpService.get(`${this.apiUrl}/getWeightGoals`)
            .map((res: Response) => res.json())
    }

    getRatios() {
        return this.authHttpService.get(`${this.apiUrl}/getRatios`)
            .map((res: Response) => res.json())
    }

    getGoalProfile() {
        return this.authHttpService.get(`${this.apiUrl}/getGoalProfile`)
            .map((res: Response) => res.json())
    }

    setGoalProfile(data: any) {
        return this.authHttpService.post(`${this.apiUrl}/setGoalProfile`, data)
            .map((res: Response) => res.json())
    }

}