package com.sbs.internetbanking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;

@Embeddable
public class AccountAccessPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -176782926065270722L;
	private String accessFor;
	private String accessTo;
	
	public String getAccessFor() {
		return accessFor;
	}
	public void setAccessFor(String accessFor) {
		this.accessFor = accessFor;
	}
	public String getAccessTo() {
		return accessTo;
	}
	public void setAccessTo(String accessTo) {
		this.accessTo = accessTo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessFor == null) ? 0 : accessFor.hashCode());
		result = prime * result + ((accessTo == null) ? 0 : accessTo.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountAccessPK other = (AccountAccessPK) obj;
		if (accessFor == null) {
			if (other.accessFor != null)
				return false;
		} else if (!accessFor.equals(other.accessFor))
			return false;
		if (accessTo == null) {
			if (other.accessTo != null)
				return false;
		} else if (!accessTo.equals(other.accessTo))
			return false;
		return true;
	}
}