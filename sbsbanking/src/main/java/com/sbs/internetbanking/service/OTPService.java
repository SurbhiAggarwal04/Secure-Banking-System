package com.sbs.internetbanking.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.sbs.internetbanking.exceptions.OTPGenerationException;
import com.sbs.internetbanking.exceptions.OTPStatus;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.model.UserOTPToken;
import com.sbs.internetbanking.persistence.UserManager;

/**
 * 
 * @author
 *
 */
public class OTPService {

	private static int OTP_EXPIRATION_MINUTES = 5;
	private static int OTP_ATTEMPTS = 5;

	private final String MESSAGE_OTP_ATTEMPTS_EXCEEDED = "MESSAGE_OTP_ATTEMPTS_EXCEEDED";
	private final String MESSAGE_OTP_EXPIRED = "The last sent OTPhas Expied, generate a new one";

	private static final Logger logger = LoggerFactory.getLogger(OTPService.class);
	private static int[] dd = new int[] { 0, 2, 4, 6, 8, 1, 3, 5, 7, 9 };

	private final String otpSubject;

	@Autowired
	private EmailService emailService;

	@Autowired
	UserManager userManager;

	public OTPService(String otpSubject) {
		this.otpSubject = otpSubject;
	}

	public static String truncate(String value, int length) {
		if (value != null && value.length() > length)
			value = value.substring(0, length);
		return value;
	}

	private static String FormatOTP(byte[] hmac) {
		int offset = hmac[19] & 0xf;
		int bin_code = (hmac[offset] & 0x7f) << 24 | (hmac[offset + 1] & 0xff) << 16 | (hmac[offset + 2] & 0xff) << 8
				| (hmac[offset + 3] & 0xff);
		int Code_Digits = bin_code % 10000000;
		int csum = checksum(Code_Digits);
		int OTP = Code_Digits * 10 + csum;
		// System.out.println(OTP);
		return String.valueOf(OTP);
	}

	private static int checksum(int Code_Digits) {
		int d1 = (Code_Digits / 1000000) % 10;
		int d2 = (Code_Digits / 100000) % 10;
		int d3 = (Code_Digits / 10000) % 10;
		int d4 = (Code_Digits / 1000) % 10;
		int d5 = (Code_Digits / 100) % 10;
		int d6 = (Code_Digits / 10) % 10;
		int d7 = Code_Digits % 10;
		return (10 - ((dd[d1] + d2 + dd[d3] + d4 + dd[d5] + d6 + dd[d7]) % 10)) % 10;
	}

	/**
	 * This method sends the generated otp via mail.
	 * 
	 * @param user
	 * @return
	 * @throws OTPGenerationException
	 */
	public String sendOTPViaMail(User user) throws OTPGenerationException {
		String key = String.valueOf(System.currentTimeMillis());
		String message = user.getUsername() + user.getUserProfileInfo().getPhone().toString();
		String otp = "";
		Mac sha256_HMAC;
		try {
			sha256_HMAC = Mac.getInstance("HmacSHA1");
			SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA1");
			sha256_HMAC.init(secret_key);
			byte[] rawHmac = sha256_HMAC.doFinal(message.getBytes());
			otp = FormatOTP(rawHmac);
			otp = truncate(otp, 6);
			String bodyText = "<b><i style=\"font-size:14px;color:blue;\">Dear Customer,</b><br><br>"
					+ "<b>This is your One Time Password: </b>"+otp;
			emailService.sendMail(user.getUserProfileInfo().getEmailId(), otpSubject, bodyText);
			updateUserOTPToken(otp, user.getUsername());
			logger.info("Sending an email for otp verification");
		} catch (InvalidKeyException | NoSuchAlgorithmException otpException) {
			logger.error("problem generating the otp: {0}", otpException.getCause());
			throw new OTPGenerationException("Problem generating OTP", otpException);
		} catch (Exception exp) {
			logger.error("Something went wrong : {0}", exp.getCause());
		}
		return otp;
	}

	private void updateUserOTPToken(String otp, String username) {
		UserOTPToken token = userManager.getOTPForUser(username);
		if (token == null) {
			UserOTPToken userOTPToken = new UserOTPToken();
			userOTPToken.setOtp(otp);
			userOTPToken.setNumOfOTPGenerated(1);
			userOTPToken.setUsername(username);
			userOTPToken.setExpiryTimestamp(getOTPExpirationTime());
			userManager.saveOTPForUser(userOTPToken);
		} else {
			token.setExpiryTimestamp(getOTPExpirationTime());
			token.setNumOfOTPGenerated(token.getNumOfOTPGenerated() + 1);
			token.setOtp(otp);
			userManager.saveOTPForUser(token);
		}
	}

	private Date getOTPExpirationTime() {
		Date dNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dNow);
		cal.add(Calendar.MINUTE, OTP_EXPIRATION_MINUTES);
		return cal.getTime();
	}

	/**
	 * 
	 * @return
	 */
	private java.util.Date getCurrentTime() {
		Date dNow = new Date();
		return new Timestamp(dNow.getTime());
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	private boolean isCurrentOTPExpired(UserOTPToken token) {
		boolean result = token.getExpiryTimestamp().before(getCurrentTime());
		System.out.println(result);
		return result;
	}

	/**
	 * 
	 * @param token
	 * @return
	 */
	private boolean hasMoreAttempts(UserOTPToken token) {
		return (token.getNumOfOTPGenerated() > OTP_ATTEMPTS) ? false : true;
	}

	public boolean hasMoreAttempts(String username) {
		UserOTPToken token = userManager.getOTPForUser(username);
		return (token.getNumOfOTPGenerated() > OTP_ATTEMPTS) ? false : true;
	}

	/**
	 * 
	 * @param otp
	 * @param username
	 * @return
	 */
	public OTPStatus verifyOTP(String otp, String username) {

		OTPStatus otpStatus = new OTPStatus();
		UserOTPToken token = userManager.getOTPForUser(username);
		if (!hasMoreAttempts(token)) {
			otpStatus.setOtpStatus(OTPStatus.STATUS_ATTEMPTS_EXCEEDED);
			otpStatus.setOtpStatusMessage(OTPStatus.MESSAGE_OTP_ATTEMPTS_EXCEEDED);
			userManager.lockAccount(username);
			return otpStatus;
		}
		if (isCurrentOTPExpired(token)) {
			otpStatus.setOtpStatus(OTPStatus.STATUS_CURRENT_TOKEN_EXPIRED);
			otpStatus.setOtpStatusMessage(OTPStatus.MESSAGE_CURRENT_OTP_EXPIRED);
			return otpStatus;
		}

		if (otp.equals(token.getOtp())) {
			otpStatus.setOtpStatus(OTPStatus.STATUS_VERIFIED);
			otpStatus.setOtpStatusMessage(OTPStatus.MESSAGE_OTP_VERIFIED);
			return otpStatus;
		} else {
			otpStatus.setOtpStatus(OTPStatus.STATUS_NOT_VERIFIED);
			otpStatus.setOtpStatusMessage(OTPStatus.MESSAGE_OTP_NOT_VERIFIED);
			return otpStatus;
		}
	}
}