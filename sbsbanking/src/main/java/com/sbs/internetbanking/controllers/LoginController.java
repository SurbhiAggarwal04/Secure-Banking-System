package com.sbs.internetbanking.controllers;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.persistence.RequestManager;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.utilities.RequestGenerator;

@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserManager userManager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RequestManager requestManager;

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public ModelAndView forgotPassword() {
		ModelAndView model = new ModelAndView();
		model.setViewName("forgotpasswordFirst");
		return model;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		User userForm = new User();
		model.addAttribute("userForm", userForm);
		return "signup";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(@ModelAttribute("userForm") User user, HttpServletRequest request) {

		String remoteAddr = request.getRemoteAddr();
		ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
		reCaptcha.setPrivateKey("6LcXwgwTAAAAAHjcMU1XH7l0flAcXNED79v4Yl56");

		String challenge = request.getParameter("recaptcha_challenge_field");

		String uresponse = request.getParameter("recaptcha_response_field");

		ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);
		ModelAndView model = new ModelAndView();

		if (reCaptchaResponse.isValid()) {

			model.setViewName("login");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setAnswer1(passwordEncoder.encode(user.getAnswer1()));
			user.setAnswer2(passwordEncoder.encode(user.getAnswer2()));
			user.getUserProfileInfo().setSsn(passwordEncoder.encode(user.getUserProfileInfo().getSsn()));
			user.getUserProfileInfo().getAddress().setUsername(user.getUsername());
			user.getUserProfileInfo().setUsername(user.getUsername());
			user.setAccountStatus(AccountStatus.PENDING_APPROVAL.toString());
			user.setUsername(user.getUserProfileInfo().getUsername());
			user.setRole("4");
			userManager.saveUser(user);
			requestManager.saveRequest(RequestGenerator.newAccountApprovalRequest(user));
			return model;
		} else {
			model.setViewName("signup");
			model.addObject("recaptchaMessage", "Wrong captcha value");
			return model;
		}

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {
		ModelAndView model = new ModelAndView();
		model.addObject("message", "WELCOME ADMIN");
		model.setViewName("admin");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", handleErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
		}
		if (logout != null) {
			model.addObject("msg", messageSource.getMessage("message.logout", null, Locale.US));
		}
		model.setViewName("login");
		return model;
	}

	private String handleErrorMessage(HttpServletRequest request, String key) {
		Exception exception = (Exception) request.getSession().getAttribute(key);
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = messageSource.getMessage("error.badcredentials", null, Locale.US);
		} else if (exception instanceof LockedException) {
			error = messageSource.getMessage("error.accountlocked", null, Locale.US);
		} else {
			error = messageSource.getMessage("error.accountNotEnabled", null, Locale.US);
		}
		return error;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		ModelAndView model = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			System.out.println(userDetail);
			model.addObject("username", userDetail.getUsername());
		}
		model.setViewName("403");
		return model;
	}

}