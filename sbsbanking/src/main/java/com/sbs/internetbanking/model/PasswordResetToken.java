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
@Table(name = "paswdd_to_paswdtoken", schema = "bot_bank")
public class PasswordResetToken implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 549634509842085409L;

	private static final int EXPIRATION = 60 * 24;

	private String username;
	private String token;
	private Date expiryDate;
	private boolean isValid;

	@Id
	public String getUsername() {
		return username;
	}

	@Column(name = "PASSWORD_TOKEN")
	public String getToken() {
		return token;
	}

	@Column(name = "EXPIRE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getExpiryDate() {
		return expiryDate;
	}

	@Column(name = "IS_VALID")
	public boolean isValid() {
		return isValid;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}