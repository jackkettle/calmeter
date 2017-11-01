import { OpaqueToken } from "@angular/core";

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
    apiEndpoint: "http://localhost:8080/api/",
    jwtAccessTokenKey: "id_token",
    jwtRefreshTokenKey: "refresh_token",
    urlDateTimeFormat: "YYYY-MM-DD HH:mm",
    urlDateFormat: "YYYY-MM-DD",
    toastNotificationOptions: {
        position: ["top", "right"],
        lastOnBottom: true,
        timeOut: 3000
    }
};