package com.sbs.internetbanking.controllers;

import java.awt.Font;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.HibernateException;
import org.ocpsoft.common.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.internetbanking.exceptions.OTPGenerationException;
import com.sbs.internetbanking.exceptions.OTPStatus;
import com.sbs.internetbanking.model.PasswordResetToken;
import com.sbs.internetbanking.model.SecurityQuestion;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.service.EmailService;
import com.sbs.internetbanking.service.OTPService;
import com.sbs.internetbanking.utilities.PasswordResetTokenGenerator;

@Controller
public class ForgotPasswordController {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	UserManager userManager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	OTPService otpService;

	@Autowired
	EmailService emailService;

	@Autowired
	UserDetailsService userDetailsService;

	@RequestMapping(value = "/getUserSecQuestions", method = RequestMethod.POST)
	public ModelAndView getSecurityQuestions(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		String username = request.getParameter("username");
		User user = userManager.getUserByUserName(username);
		if (user != null) {
			request.getSession().setAttribute("user", user);
			List<SecurityQuestion> questionList = userManager.getUserSecurityQuestions(user);
			if (questionList.size() != 0) {
				model.addObject("questionList", questionList);
				model.addObject("username", username);
				model.setViewName("forgotPasswordSecond");
			} else {
				model.addObject("securityQuestionfetchError", "Could not fetch security question");
				model.setViewName("forgotpasswordFirst");
			}

		} else {
			model.addObject("usernameFetchError", "Username not found");
			model.setViewName("forgotpasswordFirst");
		}

		return model;

	}

	@RequestMapping(value = "/verifyAnswersAndGenerateOTP", method = RequestMethod.POST)
	public ModelAndView verifyAnswersAndGenerateOTP(@RequestParam(name = "username") String username,
			@RequestParam(name = "answer1") String answer1, @RequestParam(name = "answer2") String answer2,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		if (!Strings.isNullOrEmpty(answer1.trim()) && !Strings.isNullOrEmpty(answer2.trim())) {
			if (passwordEncoder.matches(answer1, user.getAnswer1()) && passwordEncoder.matches(answer2, user.getAnswer2())) {
				try {
					if (otpService.hasMoreAttempts(user.getUsername())) {
						otpService.sendOTPViaMail(user);
						model.setViewName("verifyOTP");
					} else {
						model.setViewName("login");
						model.addObject("locked", "Your account is locked or not active, Contact customer care for support.");
					}
				} catch (OTPGenerationException e) {
					logger.error("Error generating OTP");
				}
			} else {
				List<SecurityQuestion> securityQuestion = userManager.getUserSecurityQuestions(user);
				model.addObject("answersIncorrectError", "Answers didn't match");
				model.addObject("questionList", securityQuestion);
				model.setViewName("forgotPasswordSecond");

			}
		} else {
			model.addObject("emptyAnswers", "Answers required");
			model.setViewName("forgotPasswordSecond");
		}
		return model;
	}

	@RequestMapping(value = "/getOTP", method = RequestMethod.POST)
	public ModelAndView getOTP(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		User user = (User) request.getSession().getAttribute("user");
		try {
			if (otpService.hasMoreAttempts(user.getUsername())) {
				otpService.sendOTPViaMail(user);
				int attempts = userManager.getOTPForUser(user.getUsername()).getNumOfOTPGenerated();
				if (attempts == 5) {
					model.addObject("lastAttemptWarning",
							"*This is your last chance to verify you identity else your account will be locked");
					model.addObject("otpResend", false);
					model.setViewName("verifyOTP");
				} else {
					model.addObject("otpAttempts", "OTP Verification Attempt:" + attempts);
					model.addObject("otpResend", true);
					model.setViewName("verifyOTP");
				}
			} else {
				SecurityContextHolder.clearContext();
				model.setViewName("verifyOTP");
				model.addObject("locked", "User account locked or not enabled.");
				model.setViewName("login");
			}

		} catch (OTPGenerationException otpgexp) {
			logger.error("Error generating otp : {0}", otpgexp);
		}
		return model;
	}

	@RequestMapping(value = "/verifyOTPAndResetPassword", method = RequestMethod.POST)
	public ModelAndView verifyAndResetPasswd(@RequestParam(name = "verificationCode") String otpCode, HttpServletRequest request,
			Locale locale) {
		ModelAndView model = new ModelAndView();
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			OTPStatus otpStatus = otpService.verifyOTP(otpCode, user.getUsername());
			switch (otpStatus.getOtpStatus()) {
			case 0:
				model.setViewName("welcome");
				model.addObject("otpResend", true);
				return model;
			case 1:
				SecurityContextHolder.clearContext();
				model.setViewName("login");
				model.addObject("otpMessage",
						"Your otp attempts have expired, your account is now locked. Contact customer care for help");
				model.addObject("otpResend", false);
				return model;
			case 2:
				PasswordResetTokenGenerator.getPasswordResetTokenForUser(user);
				String appUrl = "https://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
				String token = PasswordResetTokenGenerator.getPasswordResetTokenForUser(user);
				try {
					emailService.sendPasswordResetTokenMail(appUrl, locale, token, user);
					PasswordResetToken resetToken = new PasswordResetToken();
					resetToken.setToken(token);
					resetToken.setUsername(user.getUsername());
					resetToken.setValid(true);
					userManager.savePasswordResetToken(resetToken);
					model.setViewName("login");
					SecurityContextHolder.clearContext();
					model.addObject("message", "An email with password reset link has been sent to your registered email id");
				} catch (MessagingException me) {
					logger.error("Failed to send password reset token: {0}", me);
				} catch (HibernateException he) {
					logger.error("Something went wrong: {0}", he);
				}
				return model;
			case 3:
				model.setViewName("verifyOTP");
				model.addObject("otpResend", true);
				return model;
			}
		}
		return model;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public ModelAndView changePassword(HttpServletRequest request, Locale locale, Model model,
			@RequestParam("username") String id, @RequestParam("token") String token) {
		ModelAndView modelView = new ModelAndView();
		String message = "";
		PasswordResetToken userToken = userManager.getPasswordResetToken(id);
		User user = userManager.getUserByUserName(id);
		if (user == null || user.getUsername() != id) {
			message = messageSource.getMessage("auth.message.invalidUsername", null, locale);
			modelView.addObject("message", message);
			modelView.setViewName("login");
			return modelView;
		}

		if (userToken != null) {
			if (!token.equals(userToken.getToken()) || (!userToken.isValid())) {
				message = messageSource.getMessage("auth.message.invalidToken", null, locale);
				modelView.addObject("message", message);
				modelView.setViewName("login");
				return modelView;
			}
		} else {
			message = messageSource.getMessage("auth.message.invalidToken", null, locale);
			modelView.addObject("message", message);
			modelView.setViewName("login");
			return modelView;
		}

		Calendar cal = Calendar.getInstance();
		long h = cal.getTime().getTime() - userToken.getExpiryDate().getTime();
		System.out.println(h);
		if ((cal.getTime().getTime() - userToken.getExpiryDate().getTime()) > 2 * 60 * 60 * 1000) {
			message = messageSource.getMessage("auth.message.expired", null, Locale.ENGLISH);
			model.addAttribute("message", message);
			modelView.addObject("message", message);
			modelView.setViewName("login");
			return modelView;
		}

		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, userDetailsService.loadUserByUsername(
				user.getUsername()).getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
		request.getSession().setAttribute("user", user);
		modelView.setViewName("updatePassword");
		return modelView;
	}

	@RequestMapping(value = "/savePassword", method = RequestMethod.POST)
	public ModelAndView savePassword(Locale locale, @RequestParam("password") String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User authUser = (User) auth.getPrincipal();
		ModelAndView model = new ModelAndView();
		String pass = StringUtils.trimAllWhitespace(password);
		if (!Strings.isNullOrEmpty(pass)) {
			userManager.resetPassword(passwordEncoder.encode(pass), authUser.getUsername());
			try {
				Font myFont = new Font("Serif", Font.BOLD, 12);
				
				String bodyText = messageSource.getMessage("message.resetpassword.done", new Object[] {authUser.getUserProfileInfo().getFirstname()}, Locale.ENGLISH);
				emailService.sendMail(authUser.getUserProfileInfo().getEmailId(), "BOT: Password Reset Confirmation",bodyText);
			} catch (NoSuchMessageException | MessagingException e) {
				logger.error("error sending password reset confirmation");
			}
			model.setViewName("login");
			SecurityContextHolder.clearContext();
			model.addObject("message", messageSource.getMessage("message.resetPasswordSuc", null, locale));
		} else {
			model.addObject("message", messageSource.getMessage("message.resetPasswordError", null, locale));
			model.setViewName("updatepassword");
		}
		return model;
	}

	@RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
	public ModelAndView showUpdatePasswordPage(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		model.setViewName("updatePassword");
		return model;
	}

}