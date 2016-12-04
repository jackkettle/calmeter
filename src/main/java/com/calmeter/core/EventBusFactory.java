package com.calmeter.core;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

@Component
@Scope(value = "singleton")
public class EventBusFactory {

	private EventBus eventBus;	
	
	public EventBusFactory(){
		this.eventBus = new EventBus();
	}
	
	public EventBus getInstance(){
		return this.eventBus;
	}
	
}
