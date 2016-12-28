package com.calmeter.core.account;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.UserEvent;
import com.google.common.eventbus.Subscribe;

@Component
public class UserListener {

	@Autowired
	EventBusFactory eventBusFactory;

	@PostConstruct
	public void init() {
		logger.info("UserListener init");
		eventBusFactory.getInstance().register(this);

	}

	@Subscribe
	public void handleUserEvent(UserEvent e) {
		logger.info("Record event");
	}

	private static Logger logger = LoggerFactory.getLogger(UserListener.class);

}
