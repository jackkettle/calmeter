package com.calmeter.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig
		extends WebSecurityConfigurerAdapter {

	@Autowired
	@Qualifier("authenticationProvider")
	AuthenticationProvider authenticationProvider;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
	        .authorizeRequests()
	            .antMatchers("/registration", "/resources/**").permitAll()
	            .anyRequest().authenticated()
	            .and()
	        .formLogin()
	            .loginPage("/login")
	            .permitAll()
	            .and()
	        .logout()
	            .permitAll();
    }

	@Autowired
	public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);		
	}
}
