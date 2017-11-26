package com.calmeter.core.food.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.calmeter.core.diary.model.DiaryEntry;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.utils.FoodItemHelper;

@Entity
@Table(name = "food_item_entry")
public class FoodItemEntry {

    private Long id;

    private Double weightInGrams;

    private NutritionalInformation computedNutritionalInformation;

    private FoodItem foodItemReference;

    private Meal mealReference;

    private DiaryEntry diaryEntry;

    private Meal meal;

    public FoodItemEntry() {
        this.computedNutritionalInformation = new NutritionalInformation(NutritionalInfoType.READ_ONLY);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeightInGrams() {
        return weightInGrams;
    }

    public void setWeightInGrams(Double weightInGrams) {
        this.weightInGrams = weightInGrams;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "meal_reference_id")
    public Meal getMealReference() {
        return mealReference;
    }

    public void setMealReference(Meal mealReference) {
        this.mealReference = mealReference;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "food_item_reference_id")
    public FoodItem getFoodItemReference() {
        return foodItemReference;
    }

    public void setFoodItemReference(FoodItem foodItemReference) {
        this.foodItemReference = foodItemReference;
    }

    @Transient
    public NutritionalInformation getComputedNutritionalInformation() {
        return computedNutritionalInformation;
    }

    public void setComputedNutritionalInformation(NutritionalInformation computedNutritionalInformation) {
        this.computedNutritionalInformation = computedNutritionalInformation;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_entry_id")
    public DiaryEntry getDiaryEntry() {
        return diaryEntry;
    }

    public void setDiaryEntry(DiaryEntry diaryEntry) {
        this.diaryEntry = diaryEntry;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id")
    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "FoodItemEntry{" +
                "id=" + id +
                ", weightInGrams=" + weightInGrams +
                ", computedNutritionalInformation=" + computedNutritionalInformation +
                ", foodItemReference=" + foodItemReference +
                ", mealReference=" + mealReference +
                ", diaryEntry=" + diaryEntry +
                ", meal=" + meal +
                '}';
    }

    public void applyServingModifier() {
        FoodItemHelper.applyServingModifierToValues(this);
    }

}
