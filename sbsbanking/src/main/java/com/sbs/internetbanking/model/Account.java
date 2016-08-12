package com.sbs.internetbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ACCOUNT", schema = "bot_bank")
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountNumber;
    private String userName;
    private String accountType;
    private Date lastUpdateTimestamp;
    private String accountStatus;
    private Date accountCreationTimestamp;
    private Double balance;

    @Id
    @Column(name = "ACCOUNTNUM")
    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Column(name = "USERNAME")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "ACCOUNT_TYPE")
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    @Column(name = "LASTUPDATE")
    public Date getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    @Column(name = "ACCOUNT_STATUS")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column(name = "ACCOUNT_CREATION_TIMESTAMP")
    public Date getAccountCreationTimestamp() {
        return accountCreationTimestamp;
    }

    public void setAccountCreationTimestamp(Date accountCreationTimestamp) {
        this.accountCreationTimestamp = accountCreationTimestamp;
    }

    @Column(name = "BALANCE")
    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
