package com.calmeter.core.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.UserEvent;
import com.calmeter.core.account.model.UserEventType;

@Controller
@RequestMapping("api/user")
public class UserService {

	@Autowired
	EventBusFactory eventBusFactory;

	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public ResponseEntity<String> create() {
		logger.debug("User service called: create");
		
		UserEvent userEvent = new UserEvent();
		userEvent.setUserEventType(UserEventType.CREATE);
		eventBusFactory.getInstance().post(userEvent);
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/get")
	public ResponseEntity<String> get() {
		logger.debug("User service called: get");
		
		UserEvent userEvent = new UserEvent();
		userEvent.setUserEventType(UserEventType.READ);
		eventBusFactory.getInstance().post(userEvent);
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/modify")
	public ResponseEntity<String> modify() {
		logger.debug("User service called: modify");
		
		UserEvent userEvent = new UserEvent();
		userEvent.setUserEventType(UserEventType.UPDATE);
		eventBusFactory.getInstance().post(userEvent);
		return ResponseEntity.ok("");
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/delete")
	public ResponseEntity<String> delete() {
		logger.debug("User service called: delete");
		
		UserEvent userEvent = new UserEvent();
		userEvent.setUserEventType(UserEventType.DELETE);
		eventBusFactory.getInstance().post(userEvent);
		return ResponseEntity.ok("");
	}

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

}
