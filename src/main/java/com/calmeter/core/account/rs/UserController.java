package com.calmeter.core.account.rs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.calmeter.core.EventBusFactory;
import com.calmeter.core.account.model.User;
import com.calmeter.core.account.model.UserEvent;
import com.calmeter.core.account.model.UserEventType;
import com.calmeter.core.account.service.ISecurityService;
import com.calmeter.core.account.service.IUserService;
import com.calmeter.core.account.service.UserValidator;

@Controller
@RequestMapping("api/user")
public class UserController {

	@Autowired
	EventBusFactory eventBusFactory;
	
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityService securityService;

    @Autowired
    private UserValidator userValidator;

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
	
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

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

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }

	private static Logger logger = LoggerFactory.getLogger(UserController.class);

}
