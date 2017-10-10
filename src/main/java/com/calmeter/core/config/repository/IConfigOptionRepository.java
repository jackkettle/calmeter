package com.calmeter.core.config.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calmeter.core.config.model.ConfigKey;
import com.calmeter.core.config.model.ConfigOption;

public interface IConfigOptionRepository extends JpaRepository<ConfigOption, Long> {

	Optional<ConfigOption> findByConfigKey(ConfigKey configKey);
	
}
