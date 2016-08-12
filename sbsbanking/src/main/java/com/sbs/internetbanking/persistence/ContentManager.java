package com.sbs.internetbanking.persistence;

import com.sbs.internetbanking.model.SecurityQuestion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class ContentManager {

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public List<SecurityQuestion> getSecurityQuestions() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM SecurityQuestion").list();
    }

    @Transactional
    public List<SecurityQuestion> getStates() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM State").list();
    }


}
