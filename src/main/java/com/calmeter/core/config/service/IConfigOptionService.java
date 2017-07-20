package com.calmeter.core.config.service;

import java.util.Optional;

import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.model.ConfigOption;

public interface IConfigOptionService {

	Optional<ConfigOption> findByConfigKey(ConfigKey configKey);

	ConfigOption setConfigKey(ConfigKey configKey, String configValue);
	
}
