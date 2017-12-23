package com.calmeter.core.utils;

import com.calmeter.core.Constants;
import org.springframework.stereotype.Component;

@Component
public class ConversionHelper {

    public static Double convertKjToKcals(Double input){
        return DoubleHelper.round(input / Constants.KJ_IN_KCAL, 1);
    }

}
