package com.sbs.internetbanking.auth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.sbs.internetbanking.context.OTPSessionContext;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.exceptions.OTPGenerationException;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.service.OTPService;

@Component("authenticationSuccessHandler")
public class SBSAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(SBSAuthenticationSuccessHandler.class);

	@Autowired
	private UserManager userManager;

	@Autowired
	private OTPService otpService;

	@SuppressWarnings("unchecked")
	@Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = httpServletRequest.getSession();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// List<GrantedAuthority> authorities = (List<GrantedAuthority>)
		// SecurityContextHolder.getContext().getAuthentication()
		// .getAuthorities();
		// if (authorities!= null &&
		// authorities.get(0).getAuthority().equals(Role.ROLE_CUSTOMER.name()))
		// {
		// List<GrantedAuthority> changedAuthorities = new
		// ArrayList<GrantedAuthority>();
		// authorities.add(new
		// SimpleGrantedAuthority(Role.ROLE_PREOTP_USER.name()));
		// Authentication newAuth = new
		// UsernamePasswordAuthenticationToken(auth.getPrincipal(),
		// auth.getCredentials(),
		// changedAuthorities);
		// SecurityContextHolder.getContext().setAuthentication(newAuth);
		// }
		String otp = "";
		User authUser = (User) auth.getPrincipal();
		String username = authUser.getUsername();
		com.sbs.internetbanking.model.User user = userManager.getUserByUserName(username);
		userManager.resetLoginAttempts(authUser.getUsername());
		session.setAttribute("user", user);
		String role = authentication.getAuthorities().iterator().next().getAuthority();
		session.setAttribute("authorities", authentication.getAuthorities());
		session.setMaxInactiveInterval(300);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);
		OTPSessionContext otpContext = new OTPSessionContext();
		if (role.equals(Roles.ROLE_PREOTP_USER.name())) {
			try {
				otp = otpService.sendOTPViaMail(user);
				otpContext.setCurrentOTP(otp);
				otpContext.setAttempts(1);
				session.setAttribute("otp", otpContext);
			} catch (OTPGenerationException exp) {
				logger.error("Error Generating OTP : {0}", exp);
				httpServletResponse.sendRedirect("/internetbanking/login?logout");
			}
		}
		String encodedURL = httpServletResponse.encodeURL(setRedirectUrl(role));
		httpServletResponse.sendRedirect(encodedURL);
	}

	private String setRedirectUrl(String role) {
		String URL = "/internetbanking/welcome";
		if (!role.equals(Roles.ROLE_PREOTP_USER.name() )) {
			return "/internetbanking/dashboard";
		} 
		return URL;
	}

}