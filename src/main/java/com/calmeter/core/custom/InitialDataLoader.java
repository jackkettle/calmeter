package com.calmeter.core.custom;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean setUp = false;

	@Autowired
	TestValueLoader testValueLoader;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (setUp)
			return;

		testValueLoader.loadValues();

		setUp = true;
	}
}
