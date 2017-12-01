package com.calmeter.core.goal.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import com.calmeter.core.account.model.WeightLogEntry;
import com.calmeter.core.account.utils.UserHelper;
import com.calmeter.core.utils.ClassHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.account.model.Sex;
import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.service.INutritionalInformationService;
import com.calmeter.core.goal.model.ActivityLevel;
import com.calmeter.core.goal.model.GoalProfile;
import com.calmeter.core.goal.service.IGoalProfileService;
import com.calmeter.core.utils.DoubleHelper;

@Component
public class GoalProfileHelper {

    private IGoalProfileService goalProfileService;
    private INutritionalInformationService nutritionalInformationService;
    private UserHelper userHelper;

    @Autowired
    public GoalProfileHelper(IGoalProfileService goalProfileService, INutritionalInformationService nutritionalInformationService, UserHelper userHelper) {
        this.goalProfileService = goalProfileService;
        this.nutritionalInformationService = nutritionalInformationService;
        this.userHelper = userHelper;
    }

    public int getUserBMR(User user) {

        if (!user.getIsUserProfileSet()) {
            return 0;
        }

        Optional<WeightLogEntry> weightWrapper = userHelper.getCurrentWeight(user);

        Double height = user.getUserProfile().getHeight();
        Double weight = 0.0;
        if (weightWrapper.isPresent())
            weight = weightWrapper.get().getWeightInKgs();

        int age = Period.between(user.getUserProfile().getDateOfBirth(), LocalDate.now()).getYears();

        if (user.getUserProfile().getSex().equals(Sex.MALE)) {
            return (int) Math.round((10 * weight) + (6.25 * height) - (5 * age) + 5);
        } else {
            return (int) Math.round((10 * weight) + (6.25 * height) - (5 * age) - 161);
        }

    }

    public int getDailyCalories(User user) {

        if (!user.getIsUserProfileSet()) {
            return 0;
        }

        Optional<GoalProfile> goalProfileWrapper = goalProfileService.get(user);
        if (!goalProfileWrapper.isPresent()) {
            return 0;
        }

        return (int) Math
                .round(this.getUserBMR(user) * goalProfileWrapper.get().getActivityLevel().getActivityFactor());
    }

    public int getDailyCalories(User user, ActivityLevel activityLevel) {

        if (!user.getIsUserProfileSet()) {
            return 0;
        }

        return (int) Math.round(this.getUserBMR(user) * activityLevel.getActivityFactor());
    }

    public NutritionalInformation getModifiedNutritionalInfo(GoalProfile goalProfile) {
        NutritionalInformation baselineNutritionalInformation = nutritionalInformationService.getBaseline();
        NutritionalInformation ratioNutritionalInformation = goalProfile.getNutritionalRatio()
                .getNutritionalInformation();

        NutritionalInformation modifiedNutritionalInformation = goalProfile.getNutritionalInformation();

        ClassHelper.addObjects(baselineNutritionalInformation, modifiedNutritionalInformation);

        Integer dailyCals = goalProfile.getDailyCalories();
        modifiedNutritionalInformation.setCalories(dailyCals.doubleValue());

        Double carbPercentage = ratioNutritionalInformation.getConsolidatedCarbs().getTotal();
        Double proteinPercentage = ratioNutritionalInformation.getConsolidatedProteins().getProtein();
        Double fatPercentage = ratioNutritionalInformation.getConsolidatedFats().getTotalFat();

        Double carbValue = DoubleHelper.round(((dailyCals * (carbPercentage / 100)) / 4), 2);
        Double proteinValue = DoubleHelper.round(((dailyCals * (proteinPercentage / 100)) / 4), 2);
        Double fatValue = DoubleHelper.round(((dailyCals * (fatPercentage / 100)) / 9), 2);

        modifiedNutritionalInformation.getConsolidatedCarbs().setTotal(carbValue);
        modifiedNutritionalInformation.getConsolidatedProteins().setProtein(proteinValue);
        modifiedNutritionalInformation.getConsolidatedFats().setTotalFat(fatValue);

        return modifiedNutritionalInformation;
    }

}
