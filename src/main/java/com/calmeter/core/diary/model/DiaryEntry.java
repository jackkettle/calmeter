package com.calmeter.core.diary.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.calmeter.core.account.model.User;
import com.calmeter.core.food.model.FoodItem;
import com.calmeter.core.food.model.Meal;

@Entity
@Table(name = "diary_entry")
public class DiaryEntry {

	private Long id;
	
	private User user;
	
	private List<Meal> meal;
		
	private List<FoodItem> foodItem;
	
	private LocalDateTime time;
	
	private boolean eatan;
	
	private DiaryEntryFoodType diaryEntryFoodType;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.ALL)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "diary_food_item_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "meal_id"))
	public List<Meal> getMeals() {
		return this.meal;
	}

	public void setMeals(List<Meal> meal) {
		this.diaryEntryFoodType = DiaryEntryFoodType.MEAL;
		this.meal = meal;
	}
	

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "diary_meal_map", joinColumns = @JoinColumn(name = "diary_id"), inverseJoinColumns = @JoinColumn(name = "food_item_id"))
	public List<FoodItem> getFoodItems() {
		return foodItem;
	}

	public void setFoodItems(List<FoodItem> foodItem) {
		this.diaryEntryFoodType = DiaryEntryFoodType.FOODITEM;
		this.foodItem = foodItem;
	}

	@Column
	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public boolean isEatan() {
		return eatan;
	}

	public void setEatan(boolean eatan) {
		this.eatan = eatan;
	}

	@Enumerated(EnumType.STRING)
	public DiaryEntryFoodType getDiaryEntryFoodType() {
		return diaryEntryFoodType;
	}

	public void setDiaryEntryFoodType(DiaryEntryFoodType diaryEntryFoodType) {
		this.diaryEntryFoodType = diaryEntryFoodType;
	}
	
}
