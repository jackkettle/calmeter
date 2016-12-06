package com.calmeter.core.account.service;

public interface ISecurityService {
	
	String findLoggedInUsername();
	
	void autologin(String username, String password);

}