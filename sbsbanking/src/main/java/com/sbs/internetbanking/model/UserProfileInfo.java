package com.sbs.internetbanking.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "USERPII", schema = "bot_bank")
public class UserProfileInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //	@Size(min=8, max=15, message="Your username should be between 8 - 15 characters.")
//	@NotNull(message="Please select a username")
    private String username;
    //@Size(min=8, max=15, message="Your username should be between 8 - 15 characters.")
    @NotNull(message = "Please select a username")
    private String firstname;
    //@Size(min=8, max=15, message="Your username should be between 8 - 15 characters.")
    @NotNull(message = "Please select a username")
    private String lastname;
    //@Pattern(regexp="(^$|[0-9]{10})")
    @NotNull(message = "Please enter your 10 digit phone number")
    private String phone;
    @NotNull(message = "Please provide date of birth")
    private String dob;
    @NotNull(message = "enter your emailId")
    private String emailId;
    //@Size(min=9, max =9 , message="enter a valid 9 digit SSN")
    @NotNull(message = "enter your SSN")
    private String ssn;
    private String idProof;
    private String idType;
    private Address address;


    @Column(name = "dob")
    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    @Id
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "FIRSTNAME")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "LASTNAME")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "EMAILID")
    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    @Column(name = "SSN")
    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    @Column(name = "ID_PROOF")
    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    @Column(name = "ID_TYPE")
    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USERNAME")
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
