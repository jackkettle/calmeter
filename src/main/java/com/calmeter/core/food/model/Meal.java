package com.calmeter.core.food.model;

import java.util.List;

import javax.persistence.*;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.controller.MealDeserializer;
import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.utils.MealHelper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "meal")
@JsonDeserialize(using = MealDeserializer.class)
public class Meal implements IFood {

    private Long id;

    private String name;

    private String description;

    private List<FoodItemEntry> foodItemEntries;

    private NutritionalInformation nutritionalInformation;

    private User creator;

    private boolean disabled;

    public Meal() {
        this.setDisabled(false);
        this.nutritionalInformation = new NutritionalInformation(NutritionalInfoType.MEAL);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", length = 2000)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "meal")
    public List<FoodItemEntry> getFoodItemEntries() {
        return foodItemEntries;
    }

    public void setFoodItemEntries(List<FoodItemEntry> foodItemEntries) {
        this.foodItemEntries = foodItemEntries;
    }

    @ManyToOne
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "nutritional_info_id")
    public NutritionalInformation getNutritionalInformation() {
        return nutritionalInformation;
    }

    public void setNutritionalInformation(NutritionalInformation computedNutritionalInformation) {
        this.nutritionalInformation = computedNutritionalInformation;
    }

    public void computeNutritionalInformation() {
        this.setNutritionalInformation(MealHelper.computeNutritionalInformation(this));
    }
}
