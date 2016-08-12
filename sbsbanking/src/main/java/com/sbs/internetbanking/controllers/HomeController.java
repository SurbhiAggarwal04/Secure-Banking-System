package com.sbs.internetbanking.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.exceptions.OTPStatus;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.persistence.ContentManager;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.service.EmailService;
import com.sbs.internetbanking.service.OTPService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	UserManager userManager;

	@Autowired
	ContentManager contentManager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	EmailService emailService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OTPService otpService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "welcome";
	}

	@RequestMapping(value = { "/dashboard" }, method = RequestMethod.GET)
	public String viewDashboard(Locale locale, Model model) {
		return "dashboard";
	}

	@RequestMapping(value = { "/verifyOTPandPassword" }, method = RequestMethod.POST)
	public ModelAndView verifyOTPandPassword(@RequestParam(name = "password") String password,
			@RequestParam(name = "verificationCode") String otpCode, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		if (user != null) {
			OTPStatus otpStatus = otpService.verifyOTP(otpCode, user.getUsername());
			switch (otpStatus.getOtpStatus()) {
			case 0:
				model.setViewName("welcome");
				model.addObject("otpResend", "your otp password has expired, regenerate otp");
				return model;
			case 1:
				SecurityContextHolder.clearContext();
				model.setViewName("login");
				model.addObject("otpMessage",
						"Your otp attempts have expired, your account is now locked. Contact customer care for help");
				return model;
			case 2:
				model.setViewName("dashboard");
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(auth.getAuthorities());
				authorities.remove(0);
				authorities.add(new SimpleGrantedAuthority(Roles.ROLE_CUSTOMER.name()));
				Authentication newAuth = new UsernamePasswordAuthenticationToken(auth.getPrincipal(), auth.getCredentials(),
						authorities);
				SecurityContextHolder.getContext().setAuthentication(newAuth);
				userManager.resetOTPToken(user.getUsername());
				return model;
			case 3:
				model.setViewName("welcome");
				model.addObject("otpResend", "Wrong password");
				return model;
			}

		}
		model.setViewName("welcome");
		model.addObject("otperror", "service down");
		return model;
	}

	@RequestMapping(value = { "/about" }, method = RequestMethod.GET)
	public String about(Locale locale, Model model) {
		System.out.println("about");
		return "about";
	}

	@RequestMapping(value = { "/contactUs" }, method = RequestMethod.GET)
	public String contactUs(Locale locale, Model model) {
		return "contactus";
	}

	@RequestMapping(value = "/myaccount", method = RequestMethod.GET)
	public String myAccount(Locale locale, Model model) {
		model.addAttribute("welcome", messageSource.getMessage("message.welcome", null, Locale.US));
		return "home";
	}

	@RequestMapping(value = { "/getSecurityQuestions" }, method = RequestMethod.GET)
	public @ResponseBody String getSecurityQuestions() {
		Gson gson = new Gson();
		return (gson.toJson(contentManager.getSecurityQuestions()).toString());
	}

	@RequestMapping(value = { "/getStates" }, method = RequestMethod.GET)
	public @ResponseBody String getStates() {
		Gson gson = new Gson();
		return (gson.toJson(contentManager.getStates()).toString());
	}

	@RequestMapping(value = { "/getUser" }, method = RequestMethod.GET)
	public @ResponseBody String getUser(@RequestParam(name = "username") String username) {
		Gson gson = new Gson();
		System.out.println("HELLO");
		System.out.println(username);
		User user = userManager.getUserByUserName(username);
		if (user == null) {

			return gson.toJson(true);
		}
		return null;
	}

	@RequestMapping(value = { "/getEmail" }, method = RequestMethod.GET)
	public @ResponseBody String getEmail(@RequestParam(name = "emailid") String emailid) {
		Gson gson = new Gson();

		List userpiiList = userManager.getUserByEmail(emailid);
		if (userpiiList == null || userpiiList.size() == 0) {

			return gson.toJson(true);
		}
		return null;
	}

}