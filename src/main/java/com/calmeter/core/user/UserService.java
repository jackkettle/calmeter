package com.calmeter.core.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.calmeter.core.EventBusFactory;

@Controller
@RequestMapping("api/user")
public class UserService {

	@Autowired
	EventBusFactory eventBusFactory;

	@RequestMapping(method = RequestMethod.GET, value = "/welcome")
	public ResponseEntity<String> welcome() {
		logger.info("Welcome request");
		UserEvent userEvent = new UserEvent();
		eventBusFactory.getInstance().post(userEvent);
		return ResponseEntity.ok("Welcome to RestTemplate Example.");
	}

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

}
