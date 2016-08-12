package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNT_ACCESS", schema = "bot_bank")
@IdClass(value=AccountAccessPK.class)
public class AccountAccess {

	private String accessFor;
	private String accessTo;
	private String modifyAccess;
	private String deleteAccess;
	private String viewAccess;
	private String createAccess;

	
	@Id
	@Column(name="ACCESS_FOR")
	public String getAccessFor() {
		return accessFor;
	}

	@Id
	@Column(name="ACCESS_TO")
	public String getAccessTo() {
		return accessTo;
	}

	@Column(name="MODIFY_ACCESS")
	public String getModifyAccess() {
		return modifyAccess;
	}

	@Column(name="DELETE_ACCESS")
	public String getDeleteAccess() {
		return deleteAccess;
	}

	@Column(name="VIEW_ACCESS")
	public String getViewAccess() {
		return viewAccess;
	}

	public void setViewAccess(String viewAccess) {
		this.viewAccess = viewAccess;
	}

	@Column(name="CREATE_ACCESS")
	public String getCreateAccess() {
		return createAccess;
	}

	public void setCreateAccess(String createAccess) {
		this.createAccess = createAccess;
	}

	public void setDeleteAccess(String deleteAccess) {
		this.deleteAccess = deleteAccess;
	}

	public void setModifyAccess(String modifyAccess) {
		this.modifyAccess = modifyAccess;
	}

	public void setAccessTo(String accessTo) {
		this.accessTo = accessTo;
	}

	public void setAccessFor(String accessFor) {
		this.accessFor = accessFor;
	}
}