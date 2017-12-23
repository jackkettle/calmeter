import {OpaqueToken} from "@angular/core";

export let APP_CONFIG = new OpaqueToken("app.config");

export interface IAppConfig {
    apiEndpoint: string;
    jwtAccessTokenKey: string;
    jwtRefreshTokenKey: string;
    urlDateTimeFormat: string;
    urlDateFormat: string;
    toastNotificationOptions: any;
    errorCodes: any;
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
    },
    errorCodes: {
        meals: {
            errorTitle: "Meal update error",
            mealAlreadyExistsError: "A meal already exists with that name",
            unknownError: "Unable to add meal for unknown reason"
        },
        foodSelection: {
            barcodeErrorTitle: "Barcode reader error",
            failedToReadBarcodeError: "Failed to read barcode from file",
            failedToFindFoodItemFromBarcodeError: "Failed to find foodItem from valid barcode"
        }
    }
};