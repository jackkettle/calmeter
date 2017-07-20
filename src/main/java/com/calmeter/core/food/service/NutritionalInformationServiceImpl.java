package com.calmeter.core.food.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.model.ConfigOption;
import com.calmeter.core.config.service.IConfigOptionService;
import com.calmeter.core.food.model.nutrient.NutritionalInformation;
import com.calmeter.core.food.repositroy.INutritionalInformationRepository;

@Component("nutritionalInformationService")
@Transactional
public class NutritionalInformationServiceImpl implements INutritionalInformationService {

	@Autowired
	IConfigOptionService configOptionService;

	@Autowired
	INutritionalInformationRepository nutritionalInformationRepository;

	@Override
	public NutritionalInformation getBaseline() {

		Optional<ConfigOption> configOptionWrapper = configOptionService
				.findByConfigKey(ConfigKey.NUTRIONAL_BASELINE_ID);
		if (!configOptionWrapper.isPresent()) {
			return null;
		}

		Long id = Long.valueOf(configOptionWrapper.get().getConfigValue());

		return nutritionalInformationRepository.getOne(id);
	}

}
