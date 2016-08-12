package com.sbs.internetbanking.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "USERS", schema = "bot_bank")
public class User implements Serializable, Comparable<User> {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String confirmPassword;
    private Date lastLogin;
    private String role;
    private int attempts;
    private String accountStatus;
    private String question1;
    private String answer1;
    private String question2;
    private String answer2;
    private UserProfileInfo userProfileInfo;

    @Transient
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Id
    @Column(name = "USERNAME")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USERNAME")
    public UserProfileInfo getUserProfileInfo() {
        return userProfileInfo;
    }

    public void setUserProfileInfo(UserProfileInfo userProfileInfo) {
        this.userProfileInfo = userProfileInfo;
    }

    @Column(name = "PASSWORD")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "LAST_LOGIN")
    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Column(name = "LOGIN_ATTEMPTS")
    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Column(name = "ACCOUNT_STATUS")
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column(name = "SEC_QUESTION1")
    @JoinColumn(name = "QID")
    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    @Column(name = "ANSWER1")
    public String getAnswer1() {
        return answer1;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    @Column(name = "SEC_QUESTION2")
    @JoinColumn(name = "QID")
    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    @Column(name = "ROLE")
    @JoinColumn(name = "ROLE")
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Column(name = "ANSWER2")
    public String getAnswer2() {
        return answer2;
    }

    public void setAnswer2(String answer2) {
        this.answer2 = answer2;
    }

    @Override
    public int compareTo(User user) {
        // TODO Auto-generated method stub
        if (this.getUserProfileInfo().getFirstname().equals(user.getUserProfileInfo().getFirstname())
                && this.getUserProfileInfo().getLastname().equals(user.getUserProfileInfo().getLastname())
                && this.getUserProfileInfo().getDob().equals(user.getUserProfileInfo().getDob())
                && this.getUserProfileInfo().getEmailId().equals(user.getUserProfileInfo().getEmailId())
                && this.getUserProfileInfo().getPhone().equals(user.getUserProfileInfo().getPhone())
                && this.getUserProfileInfo().getAddress().getAddressLine1()
                .equals(user.getUserProfileInfo().getAddress().getAddressLine1())
                && this.getUserProfileInfo().getAddress().getAddressLine2()
                .equals(user.getUserProfileInfo().getAddress().getAddressLine2())
                && this.getUserProfileInfo().getAddress().getAreaCode()
                .equals(user.getUserProfileInfo().getAddress().getAreaCode())
                && this.getUserProfileInfo().getAddress().getCity().equals(user.getUserProfileInfo().getAddress().getCity())
                && this.getUserProfileInfo().getAddress().getState().equals(user.getUserProfileInfo().getAddress().getState())) {
            return 1;
        }
        return 0;
    }

}