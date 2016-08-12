package com.sbs.internetbanking.exceptions;

public class OTPStatus {

	
	public static int STATUS_CURRENT_TOKEN_EXPIRED = 0;
	public static int STATUS_ATTEMPTS_EXCEEDED = 1;
	public static int STATUS_VERIFIED = 2;
	public static int STATUS_NOT_VERIFIED = 3;
	public static String MESSAGE_CURRENT_OTP_EXPIRED = "Last otp token expired, but new can be generated";
	public static String MESSAGE_OTP_ATTEMPTS_EXCEEDED = "All attempts to generate OTP has expired";
	public static String MESSAGE_OTP_VERIFIED = "Verified";
	public static String MESSAGE_OTP_NOT_VERIFIED = "Wrong OTP";
	
	
	private int otpStatus;
	private String otpStatusMessage;
	public int getOtpStatus() {
		return otpStatus;
	}
	public void setOtpStatus(int otpStatus) {
		this.otpStatus = otpStatus;
	}
	public String getOtpStatusMessage() {
		return otpStatusMessage;
	}
	public void setOtpStatusMessage(String otpStatusMessage) {
		this.otpStatusMessage = otpStatusMessage;
	}
	
}
