package com.calmeter.core.account.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserEvent;
import com.calmeter.core.account.model.UserEventType;
import com.calmeter.core.account.service.ISecurityService;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.account.service.UserValidator;

@Controller
@SessionAttributes
public class UserSpringController {

	@Autowired
	EventBusFactory eventBusFactory;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("userForm", new User());
		return "registration";
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult,
			Model model) {

		logger.info("registration service called");

		model.addAttribute("userForm", new User());
		userValidator.validate(userForm, bindingResult);

		logger.info("Errors present: {}", bindingResult.hasErrors());

		if (bindingResult.hasErrors()) {
			return "registration";
		}

		logger.info("Registering username: {}", userForm.getUsername());

		UserEvent userevent = new UserEvent();
		userevent.setUser(userForm);
		userevent.setUserEventType(UserEventType.CREATE);
		eventBusFactory.getInstance().post(userevent);

		userService.save(userForm);
		securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/welcome";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "login";
	}

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcome(Model model) {
		return "welcome";
	}

	private static Logger logger = LoggerFactory.getLogger(UserSpringController.class);

}
