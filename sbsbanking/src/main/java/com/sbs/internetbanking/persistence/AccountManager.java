package com.sbs.internetbanking.persistence;

import com.sbs.internetbanking.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class AccountManager {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void saveAccount(Account request) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(request);
    }

    @Transactional
    public void deleteAccount(Account request) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(request);
    }

    @Transactional
    public int deleteAccountByAccountNumber(String accountNum) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE ACCOUNT where ACCOUNTNUM= :id");
        query.setParameter("id", accountNum);
        int result = query.executeUpdate();
        return result;
    }

    @Transactional
    public int deleteAccountsByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("DELETE ACCOUNT where USERNAME= :id");
        query.setParameter("id", username);
        int result = query.executeUpdate();
        return result;
    }


    @Transactional
    public void updateAccount(Account request) {
        Session session = sessionFactory.getCurrentSession();
        session.update(request);
    }

    @Transactional
    public List<Account> getAccounts(String userName) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Account where USERNAME = :id AND ACCOUNT_STATUS = :id1");
        query.setParameter("id", userName);
        query.setParameter("id1", "APPROVED");
        List<Account> accList = query.list();
        return accList;
    }

    @Transactional
    public Account getAccountDetails(String accountNumber) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from Account where ACCOUNTNUM = :id");
        query.setParameter("id", accountNumber);
        List<Account> accList = query.list();
        return accList.get(0);
    }

    @Transactional
    public List getAllAccountsByUserName(String userName) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("ACCOUNTNUM FROM Account where username=" + userName).list();
    }


}