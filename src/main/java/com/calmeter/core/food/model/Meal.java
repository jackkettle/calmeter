package com.calmeter.core.food.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.controller.MealDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "meal")
@JsonDeserialize(using = MealDeserializer.class)
public class Meal implements IFood {

    private Long id;

    private String name;

    private String description;

    private List<FoodItemEntry> foodItemEntries;

    private User creator;

    private boolean disabled;

    public Meal() {
        this.setDisabled(false);
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

    @ManyToMany
    @JoinTable(name = "meal_ingredient", joinColumns = @JoinColumn(name = "meal_id"), inverseJoinColumns = @JoinColumn(name = "food_item_entry_id"))
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

}
