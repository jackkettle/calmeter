package com.calmeter.core.food.utils;

import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.model.nutrient.micro.MineralLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NutritionalInformationHelper {

    public static boolean isValid(NutritionalInformation nutritionalInformation) {

        Double cals = nutritionalInformation.getCalories();
        if (cals == 0) {
            logger.error("Invalid due to cals");
            return false;
        }

        Double servingSize = nutritionalInformation.getServingSize();
        if (servingSize == null || servingSize == 0) {
            logger.error("Invalid due to servingsize");
            return false;
        }

        Double totalCarbs = nutritionalInformation.getConsolidatedCarbs().getTotal();
        if (totalCarbs == null) {
            logger.error("Invalid due to total carbs");
            return false;
        }

        Double totalFats = nutritionalInformation.getConsolidatedFats().getTotalFat();
        if (totalFats == null) {
            logger.error("Invalid due to total fats");
            return false;
        }

        Double saturatedFat = nutritionalInformation.getConsolidatedFats().getSaturatedFat();
        if (saturatedFat == null) {
            logger.error("Invalid due to saturated fats");
            return false;
        }

        Double protein = nutritionalInformation.getConsolidatedProteins().getProtein();
        if (protein == null) {
            logger.error("Invalid due to proteins");
            return false;
        }

        Double sugar = nutritionalInformation.getConsolidatedCarbs().getSugar();
        if (sugar == null) {
            logger.error("Invalid due to sugars");
            return false;
        }

        if (nutritionalInformation.getMineralMap().get(MineralLabel.SODIUM) == null) {
            logger.error("Invalid due to sodium");
            return false;
        }

        return true;
    }

    private static Logger logger = LoggerFactory.getLogger(NutritionalInformationHelper.class);

}
