package com.calmeter.core.goal.service;

import java.util.List;
import java.util.Optional;

import com.calmeter.core.account.model.User;
import com.calmeter.core.goal.model.NutritionalRatio;

public interface INutritionalRatioService {
	
	Optional<NutritionalRatio> getRatio(Long id);

	List<NutritionalRatio> getGlobalRatios();

	List<NutritionalRatio> getUserRatios(User user);
	
}
