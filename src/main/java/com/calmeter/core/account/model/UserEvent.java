package com.calmeter.core.account.model;

import org.springframework.stereotype.Component;

@Component
public class UserEvent {

	UserEventType userEventType;

	User user;

	public UserEventType getUserEventType() {
		return userEventType;
	}

	public void setUserEventType(UserEventType userEventType) {
		this.userEventType = userEventType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
