import {OpaqueToken} from "@angular/core";

export let APP_CONFIG = new OpaqueToken("app.config");

export interface IAppConfig {
    apiEndpoint: string;
    jwtAccessTokenKey: string;
    jwtRefreshTokenKey: string;
    urlDateTimeFormat: string;
    urlDateFormat: string;
    toastNotificationOptions: any;
}

export const AppConfig: IAppConfig = {
    apiEndpoint: "http://localhost:8888/api/",
    jwtAccessTokenKey: "id_token",
    jwtRefreshTokenKey: "refresh_token",
    urlDateTimeFormat: "yyyy-MM-dd HH:mm",
    urlDateFormat: "yyyy-MM-dd",
    toastNotificationOptions: {
        position: ["top", "right"],
        lastOnBottom: false,
        timeOut: 2000
    }
};