package com.sbs.internetbanking.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.sbs.internetbanking.persistence.UserManager;

@Component("authenticationProvider")
public class LoginAttemptsAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	UserManager userManager;

	@Autowired
	@Qualifier("userDetailsService")
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {
			Authentication auth = super.authenticate(authentication);
			userManager.resetLoginAttempts(authentication.getName());
			return auth;

		} catch (BadCredentialsException e) {
			userManager.updateLoginAttempts(authentication.getName());
			throw e;
		} catch (LockedException e) {
			throw new LockedException("LOCKED");
		}
	}
}