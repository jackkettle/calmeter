package com.calmeter.core.food.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.model.nutrient.NutritionalInformation;

public interface INutritionalInformationRepository extends JpaRepository<NutritionalInformation, Long> {

}