package com.sbs.internetbanking.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.PasswordResetToken;
import com.sbs.internetbanking.model.Role;
import com.sbs.internetbanking.model.SecurityQuestion;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.model.UserOTPToken;

public class UserManager {

	@Autowired
	SessionFactory sessionFactory;

	private int allowedAttempts = 0;

	public UserManager(int allowedAttempts) {
		this.allowedAttempts = allowedAttempts;
	}

	@Transactional
	public void saveUser(User user) throws HibernateException{
		Session session = sessionFactory.getCurrentSession();
		session.persist(user);
	}

	@Transactional
	public void deleteUser(User user) throws HibernateException{
		Session session = sessionFactory.getCurrentSession();
		session.delete(user);
	}

	@Transactional
	public void updateUser(User user) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		session.update(user);
	}

	@Transactional
	public User getUserByUserName(String userName) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		User usr = session.get(User.class, userName);
		return usr;
	}

	@Transactional
	public void updateLoginAttempts(String userName) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		User usr = session.get(User.class, userName);
		int attempts = usr.getAttempts();
		if (attempts < allowedAttempts - 1) {
			usr.setAttempts(attempts + 1);
			session.update("LOGIN_ATTEMPTS", usr);
		} else {
			usr.setAccountStatus(AccountStatus.LOCKED.toString());
		}
	}

	@Transactional
	public void resetLoginAttempts(String userName) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, userName);
		user.setAttempts(0);
		session.update("LOGIN_ATTEMPTS", user);
	}

	@Transactional
	public List<SecurityQuestion> getUserSecurityQuestions(User user) throws HibernateException {
		List<SecurityQuestion> questions = new ArrayList<SecurityQuestion>();
		Session session = sessionFactory.getCurrentSession();
		questions.add(session.get(SecurityQuestion.class, user.getQuestion1()));
		questions.add(session.get(SecurityQuestion.class, user.getQuestion2()));
		return questions;
	}

	@Transactional
	public Role getRoleName(String role) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		Role userRole = (Role) session.get(Role.class, role);
		return userRole;
	}

	@Transactional
	public List getUserByEmail(String emailid) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from UserProfileInfo where EMAILID = :id ");
			query.setParameter("id", emailid);
			List list = query.list();
			return list;
		} catch (HibernateException he) {
			System.out.println(he);
			return null;
		}
	}

	@Transactional
	public void resetPassword(String newPswd, String username) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		User user = session.get(User.class, username);
		user.setPassword(newPswd);
		session.update(user);
		Query query = session.createQuery("update PasswordResetToken set IS_VALID = :isValid" + " where USERNAME = :username");
		query.setParameter("isValid", false);
		query.setParameter("username", username);
		query.executeUpdate();
	}

	@Transactional
	public PasswordResetToken getPasswordResetToken(String username) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		PasswordResetToken userToken = session.get(PasswordResetToken.class, username);
		return userToken;
	}

	@Transactional
	public void savePasswordResetToken(PasswordResetToken passwordResetToken) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		passwordResetToken.setValid(true);
		session.saveOrUpdate(passwordResetToken);
	}

	@Transactional
	public UserOTPToken getOTPForUser(String username) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		UserOTPToken otpToken = session.get(UserOTPToken.class, username);
		return otpToken;
	}

	@Transactional
	public void saveOTPForUser(UserOTPToken otpToken) throws HibernateException {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(otpToken);
	}

	@Transactional
	public void lockAccount(String username) {
		User user = getUserByUserName(username);
		Session session = sessionFactory.getCurrentSession();
		user.setAccountStatus("LOCKED");
		session.update(user);
		
	}
	
	@Transactional
	public void resetOTPToken(String username){
		UserOTPToken token = getOTPForUser(username);
		token.setNumOfOTPGenerated(0);
		Session session = sessionFactory.getCurrentSession();
		session.update(token);
	}
	
	@Transactional
	public String getUserNameByAccount(String transactionFromAccountNum) {
		Session session = sessionFactory.getCurrentSession();
		
		try{
			
		
			Query query = session.createQuery("from Account where ACCOUNTNUM = :id ");			
			query.setParameter("id", transactionFromAccountNum);
			List<Account> list = query.list();
			String userName= "";
			if(list!=null)
				return list.get(0).getUserName();	
		}catch(HibernateException he){
			System.out.println(he);
			
	}
		return null;
	}
}