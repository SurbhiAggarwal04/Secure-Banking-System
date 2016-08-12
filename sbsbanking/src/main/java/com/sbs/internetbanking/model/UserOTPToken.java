package com.sbs.internetbanking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "username_to_otp", schema = "bot_bank")
public class UserOTPToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5354682810838190685L;
	private String username;
	private Date expiryTimestamp;
	private String otp;
	private int numOfOTPGenerated;

	@Id
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "latest_otp")
	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Column(name = "number_of_otp_generated")
	public int getNumOfOTPGenerated() {
		return numOfOTPGenerated;
	}
	
	public void setNumOfOTPGenerated(int numOfOTPGenerated) {
		this.numOfOTPGenerated = numOfOTPGenerated;
	}

	@Column(name = "EXPIRY_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpiryTimestamp() {
		return expiryTimestamp;
	}

	public void setExpiryTimestamp(Date expiryTimestamp) {
		this.expiryTimestamp = expiryTimestamp;
	}

}