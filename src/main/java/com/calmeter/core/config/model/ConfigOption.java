package com.calmeter.core.config.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "config_option")
public class ConfigOption {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private ConfigKey configKey;

	private Class<?> configType;

	private String configValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Enumerated(EnumType.STRING)
	public ConfigKey getConfigKey() {
		return configKey;
	}

	public void setConfigKey(ConfigKey configKey) {
		this.configKey = configKey;
	}

	public Class<?> getConfigType() {
		return configType;
	}

	public void setConfigType(Class<?> configType) {
		this.configType = configType;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

}
