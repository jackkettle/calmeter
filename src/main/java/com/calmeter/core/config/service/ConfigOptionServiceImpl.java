package com.calmeter.core.config.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.model.ConfigOption;
import com.calmeter.core.config.repository.IConfigOptionRepository;

@Component("configOptionService")
@Transactional
public class ConfigOptionServiceImpl implements IConfigOptionService {

	@Autowired
	IConfigOptionRepository configOptionRepository;

	@Override
	public Optional<ConfigOption> findByConfigKey(ConfigKey configKey) {
		return configOptionRepository.findByConfigKey(configKey);
	}

	@Override
	public ConfigOption setConfigKey(ConfigKey configKey, String configValue) {

		Optional<ConfigOption> configOptionWrapper = configOptionRepository.findByConfigKey(configKey);
		if (configOptionWrapper.isPresent()) {
			ConfigOption configOption = configOptionWrapper.get();
			configOption.setConfigValue(configValue);
			return configOptionRepository.save(configOption);
		}

		ConfigOption configOption = new ConfigOption();
		configOption.setConfigKey(configKey);
		configOption.setConfigValue(configValue);

		return configOptionRepository.save(configOption);
	}

}
