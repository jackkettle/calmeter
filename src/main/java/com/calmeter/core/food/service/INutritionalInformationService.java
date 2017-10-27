package com.calmeter.core.food.service;

import java.util.Optional;

import com.calmeter.core.food.model.nutrient.NutritionalInformation;

public interface INutritionalInformationService {

	NutritionalInformation getBaseline();
	
	NutritionalInformation save(NutritionalInformation nutritionalInformation);
	
	Optional<NutritionalInformation> get(Long id);

}
