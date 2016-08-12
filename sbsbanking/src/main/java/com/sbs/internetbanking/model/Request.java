package com.sbs.internetbanking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "REQUESTS", schema = "bot_bank")
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    private String requestId;
    private String requestType;
    private String requestFromAccountNum;
    private String requestApprovedBy;
    private Date creationTimeStamp;
    private Date approvedTimeStamp;
    private String requestContent;
    private String requestStatus;
    private String requestFor;
    private String requestUsername;
    private String requestTransactionId;
    private String requestAccountNum;
    private String requestAmount;

    @Id
    @Column(name = "REQ_ID")
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Column(name = "REQ_FROM_USER")
    public String getRequestFromAccountNum() {
        return requestFromAccountNum;
    }

    public void setRequestFromAccountNum(String requestFromAccountNum) {
        this.requestFromAccountNum = requestFromAccountNum;
    }

    @Column(name = "REQ_APPROVED_BY")
    public String getRequestApprovedBy() {
        return requestApprovedBy;
    }

    public void setRequestApprovedBy(String requestApprovedBy) {
        this.requestApprovedBy = requestApprovedBy;
    }

    @Column(name = "CREATION_TIMESTAMP")
    public Date getCreationTimeStamp() {
        return new Date();
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    @Column(name = "APPROVE_TIMESTAMP")
    public Date getApprovedTimeStamp() {
        return new Date();
    }

    public void setApprovedTimeStamp(Timestamp approvedTimeStamp) {
        this.approvedTimeStamp = approvedTimeStamp;
    }

    @Column(name = "REQ_CONTENT")
    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    @Column(name = "REQ_STATUS")
    public String getRequestStatus() {
        return requestStatus;
    }

    @Column
    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Column(name = "REQ_TYPE")
    @JoinColumn(name = "REQUEST_TYPE_ID")
    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    @Column(name = "REQ_FOR")
    public String getRequestFor() {
        return requestFor;
    }

    public void setRequestFor(String requestFor) {
        this.requestFor = requestFor;
    }

    @Column(name = "REQ_USERNAME")
    public String getRequestUsername() {
        return requestUsername;
    }

    public void setRequestUsername(String requestUsername) {
        this.requestUsername = requestUsername;
    }

    @Column(name = "REQ_TID")
    public String getRequestTransactionId() {
        return requestTransactionId;
    }

    public void setRequestTransactionId(String requestTransactionId) {
        this.requestTransactionId = requestTransactionId;
    }

    @Column(name = "REQ_ACCOUNTNUM")
    public String getRequestAccountNum() {
        return requestAccountNum;
    }

    public void setRequestAccountNum(String requestAccountNum) {
        this.requestAccountNum = requestAccountNum;
    }

  @Column(name = "REQ_AMOUNT")
    public String getRequestAmount() {
        return requestAmount;
    }

    public void setRequestAmount(String requestAmount) {
        this.requestAmount = requestAmount;
    }
}