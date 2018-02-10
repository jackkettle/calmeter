import {Component} from '@angular/core';
import {UserService} from "../../_services";
import {Http} from "@angular/http";

declare var jQuery: any;

@Component({
    selector: 'dashboard',
    templateUrl: 'dashboard.template.html',
    providers: [UserService]
})
export class DashboardComponent {

    public taskList: Array<any>;
    public userModel: any;
    public today: any;

    private feedUrl: any;
    private feedData: Array<any>;
    private todayPlus7: any;

    constructor(private userService: UserService, private http: Http) {

        this.feedUrl = "https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Ffeeds.feedburner.com%2Fanytimefitnessofficial";

        this.today = Date.now();

        var date = new Date(this.today);
        date.setDate(date.getDate() + 7);
        this.todayPlus7 = date;
    }

    ngOnInit() {
        this.userService.getThisUser().subscribe(
            data => {
                console.log(data);
                this.userModel = data;
            }
        );

        this.userService.getTaskList().subscribe(
            data => {
                console.log(data);
                this.taskList = data;
            }
        );

        this.http.get(this.feedUrl)
            .map(res => res.json())
            .subscribe(res => {
                let feedItems: Array<any> = res.items;
                this.removeSocialLinks(feedItems);
                this.feedData = feedItems;
            });
    }

    private removeSocialLinks(feedItems: Array<any>) {

        feedItems.forEach((item) => {
            let regex = "/<div class=\"[^\"]*?feedflare[^\"]*?\">(.*?)</div>\n/g";
            let stringValue: string = item.content;
            let newStringValue = stringValue.replace(regex, "");
        });


    }

}
