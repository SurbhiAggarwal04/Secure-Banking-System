package com.sbs.internetbanking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TRANSACTIONS", schema = "bot_bank")
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionId;
    private String transactionType;
    private String transactionFromAccountNum;
    private String transactionToAccountNum;
    private String transactionState;
    private Timestamp transactionUpdateTimestamp;
    private String transactionDetails;
    private String transactionRole;
    private double transactionAmount;
    private String transactionAprrovedBy;
    private String transactionContent;
    
    @Id
    @Column(name = "TRANSACTION_ID")
    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Column(name = "TRANSACTION_TYPE")
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Column(name = "TRANSACTION_FROM_USER")
    public String getTransactionFromAccountNum() {
        return transactionFromAccountNum;
    }

    public void setTransactionFromAccountNum(String transactionFromAccountNum) {
        this.transactionFromAccountNum = transactionFromAccountNum;
    }

    @Column(name = "TRANSACTION_TO_USER")
    public String getTransactionToAccountNum() {
        return transactionToAccountNum;
    }

    public void setTransactionToAccountNum(String transactionToAccountNum) {
        this.transactionToAccountNum = transactionToAccountNum;
    }

    @Column(name = "STATE")
    public String getTransactionState() {
        return transactionState;
    }

    public void setTransactionState(String transactionState) {
        this.transactionState = transactionState;
    }

    @Column(name = "TRANSACTION_UPDATE_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getTransactionUpdateTimestamp() {
        return transactionUpdateTimestamp;
    }

    public void setTransactionUpdateTimestamp(Timestamp transactionUpdateTimestamp) {
        this.transactionUpdateTimestamp = transactionUpdateTimestamp;
    }

    @Column(name = "TRANSACTION_DETAILS")
    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    @Column(name = "TRANSACTION_ROLE")
    public String getTransactionRole() {
        return transactionRole;
    }

    public void setTransactionRole(String transactionRole) {
        this.transactionRole = transactionRole;
    }

    @Column(name = "TRANSACTION_AMOUNT")
    public double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Column(name = "TRANSACTION_APPROVED_BY")
    public String getTransactionAprrovedBy() {
        return transactionAprrovedBy;
    }

    public void setTransactionAprrovedBy(String transactionAprrovedBy) {
        this.transactionAprrovedBy = transactionAprrovedBy;
    }

    @Column(name="TRANSACTION_CONTENT")
	public String getTransactionContent() {
		return transactionContent;
	}

	public void setTransactionContent(String transactionContent) {
		this.transactionContent = transactionContent;
	}

}
