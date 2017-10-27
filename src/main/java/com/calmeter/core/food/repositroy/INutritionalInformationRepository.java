package com.calmeter.core.food.repositroy;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.food.model.nutrient.NutritionalInfoType;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;

public interface INutritionalInformationRepository extends JpaRepository<NutritionalInformation, Long> {
	
	public List<NutritionalInformation> findByType(NutritionalInfoType type);
	
	public Optional<NutritionalInformation> findById(Long id);


}