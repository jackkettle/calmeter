package com.calmeter.core.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.calmeter.core.account.model.User;

@Component
public class UserValidator
		implements Validator {

	@Autowired
	private IUserService userService;

	public boolean supports (Class<?> aClass) {
		return User.class.equals (aClass);
	}

	public void validate (Object o, Errors errors) {
		User user = (User)o;

		ValidationUtils.rejectIfEmptyOrWhitespace (errors, "username", "NotEmpty");
		if (user.getUsername ().length () < 6 || user.getUsername ().length () > 32) {
			errors.rejectValue ("username", "Size.userForm.username");
		}
		if (userService.findByUsername (user.getUsername ()) != null) {
			errors.rejectValue ("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace (errors, "password", "NotEmpty");
		if (user.getPassword ().length () < 8 || user.getPassword ().length () > 32) {
			errors.rejectValue ("password", "Size.userForm.password");
		}

		if (!user.getPasswordConfirm ().equals (user.getPassword ())) {
			errors.rejectValue ("passwordConfirm", "Diff.userForm.passwordConfirm");
		}

		if (!isValidEmailAddress (user.getEmail ())) {
			errors.rejectValue ("email", "Invalid.userForm.email");
		}
	}

	public boolean isValidEmailAddress (String email) {
		String ePattern =
				"^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile (ePattern);
		java.util.regex.Matcher m = p.matcher (email);
		return m.matches ();
	}

}
