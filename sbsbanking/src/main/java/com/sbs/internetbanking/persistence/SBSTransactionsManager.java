package com.sbs.internetbanking.persistence;

import com.google.gson.Gson;
import com.sbs.internetbanking.enums.TransactionStatus;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.Request;
import com.sbs.internetbanking.model.Transaction;
import com.sbs.internetbanking.model.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SBSTransactionsManager {
    @Autowired
    UserManager userManager;

    @Autowired
    SessionFactory sessionFactory;

    @Transactional
    public void saveTransaction(Transaction request) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(request);
    }

    @Transactional
    public void deleteTransaction(Transaction request) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(request);
    }

    @Transactional
    public void updateTransaction(Transaction request) {
        Session session = sessionFactory.getCurrentSession();
        session.update(request);
    }

	@Transactional
	public List getTransactions(String accountNumber){
		System.out.println(accountNumber);
		List<Transaction> transactionList=new ArrayList<Transaction>();
		Session session = sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM Transaction where TRANSACTION_FROM_USER LIKE :id OR TRANSACTION_TO_USER LIKE :id");
		query.setParameter("id","%"+accountNumber);
		System.out.println("size"+query.list().size());
		transactionList.addAll(query.list());
		return transactionList;
	}

    /* @Transactional
        public void approveTransaction(String transactionId, String transactionType) {
            Session session = sessionFactory.getCurrentSession();
            Query query= session.createQuery("FROM Request where TRANSACTION_ID =:transactionId");
            query.setParameter("transactionId", transactionId);
            Transaction transaction= (Transaction) query.list().get(0);
            //should we create an enum
            transaction.setTransactionState(com.sbs.internetbanking.enums.RequestType.APPROVED.toString());
            session.update(transaction);
            System.out.println("transactiontype"+transactionType);
            switch (transactionType) {

            }
     }*/
    @Transactional
    private void approveAccount(Request requestId) {
        Session session = sessionFactory.getCurrentSession();
        Request request = session.get(Request.class, requestId);
        Gson gson = new Gson();
        User usr = gson.fromJson(request.getRequestContent(), User.class);
        userManager.saveUser(usr);
        //	request.setRequestType(RequestType.APPROVED.toString());
    }

    @Transactional
    public String getTransactionType(String transactionTypeId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("from RequestType where REQUEST_TYPE = :id ");
        query.setParameter("id", transactionTypeId);
        List<Transaction> list = query.list();
        System.out.println(list.get(0).getTransactionId());
        return list.get(0).getTransactionId();

    }

    @Transactional
    public Transaction getTransactionById(String transactionId) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Transaction where TRANSACTION_ID =:transactionId");
        query.setParameter("transactionId", transactionId);
        List<Transaction> transactionList = query.list();
        System.out.println(transactionList.get(0).getTransactionId());
        return transactionList.get(0);
    }

    @Transactional
    public List<Transaction> getTransactionsByRole(String role) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Transaction where TRANSACTION_ROLE =" + role);

        return (List<Transaction>) query.list();
    }

    @Transactional
    public List<Transaction> getApprovedTransactions(String accountNumber) {
        List<Transaction> transactionList = new ArrayList<Transaction>();
        Session session = sessionFactory.getCurrentSession();
        System.out.println(accountNumber);
        Query query = session.createQuery("FROM Transaction where (TRANSACTION_FROM_USER LIKE :id OR TRANSACTION_TO_USER LIKE :id) AND STATE= :stateid");
        query.setParameter("id", "%" + accountNumber);
        query.setParameter("stateid", TransactionStatus.APPROVED.toString());
        System.out.println("size" + query.list().size());
        transactionList.addAll(query.list());
        return transactionList;
    }

    @Transactional
    public List<Transaction> getAllTransactions(List<Account> accounts) {
        Session session = sessionFactory.getCurrentSession();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        if (accounts.size() == 1) {
            Query query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id OR TRANSACTION_TO_USER= :id");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            System.out.println("size" + query.list().size());
            transactionList.addAll(query.list());
        } else {
            Query query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id OR TRANSACTION_FROM_USER= :id1 AND TRANSACTION_FROM_USER <> TRANSACTION_TO_USER");
            query.setParameter("id", accounts.get(1).getAccountNumber());
            query.setParameter("id1", accounts.get(0).getAccountNumber());
            transactionList.addAll(query.list());
            query = session.createQuery("FROM Transaction where TRANSACTION_TO_USER= :id OR TRANSACTION_TO_USER= :id1 AND TRANSACTION_FROM_USER <> TRANSACTION_TO_USER");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            query.setParameter("id1", accounts.get(1).getAccountNumber());
            transactionList.addAll(query.list());

            query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id AND TRANSACTION_TO_USER= :id");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            transactionList.addAll(query.list());
            query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id AND TRANSACTION_TO_USER= :id");
            query.setParameter("id", accounts.get(1).getAccountNumber());

            transactionList.addAll(query.list());
            System.out.println("size" + transactionList.size());

        }
        return transactionList;
    }

    @Transactional
    public List<Transaction> getAllApprovedTransactions(List<Account> accounts) {
        Session session = sessionFactory.getCurrentSession();
        List<Transaction> transactionList = new ArrayList<Transaction>();
        if (accounts.size() == 1) {
            Query query = session.createQuery("FROM Transaction where (TRANSACTION_FROM_USER= :id OR TRANSACTION_TO_USER= :id) AND STATE= :stateid");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            query.setParameter("stateid", TransactionStatus.APPROVED.toString());
            System.out.println("size" + query.list().size());
            transactionList.addAll(query.list());
        } else {
            Query query = session.createQuery("FROM Transaction where (TRANSACTION_FROM_USER= :id OR TRANSACTION_FROM_USER= :id1) AND TRANSACTION_FROM_USER <> TRANSACTION_TO_USER AND STATE= :stateid");
            query.setParameter("id", accounts.get(1).getAccountNumber());
            query.setParameter("id1", accounts.get(0).getAccountNumber());
            query.setParameter("stateid", TransactionStatus.APPROVED.toString());
            transactionList.addAll(query.list());
            query = session.createQuery("FROM Transaction where (TRANSACTION_TO_USER= :id OR TRANSACTION_TO_USER= :id1) AND TRANSACTION_FROM_USER <> TRANSACTION_TO_USER AND STATE= :stateid");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            query.setParameter("id1", accounts.get(1).getAccountNumber());
            query.setParameter("stateid", TransactionStatus.APPROVED.toString());
            transactionList.addAll(query.list());

            query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id AND TRANSACTION_TO_USER= :id AND STATE= :stateid");
            query.setParameter("id", accounts.get(0).getAccountNumber());
            query.setParameter("stateid", TransactionStatus.APPROVED.toString());
            transactionList.addAll(query.list());
            query = session.createQuery("FROM Transaction where TRANSACTION_FROM_USER= :id AND TRANSACTION_TO_USER= :id AND STATE= :stateid");
            query.setParameter("id", accounts.get(1).getAccountNumber());
            query.setParameter("stateid", TransactionStatus.APPROVED.toString());
            transactionList.addAll(query.list());
            System.out.println("size" + transactionList.size());

        }
        return transactionList;
    }

    public void approveTransaction(String transactionid, String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Transaction where TRANSACTION_ID =:transactionId");
        query.setParameter("transactionId", transactionid);
        Transaction transaction = (Transaction) query.list().get(0);
        transaction.setTransactionState(com.sbs.internetbanking.enums.TransactionStatus.APPROVED.toString());
        transaction.setTransactionAprrovedBy(username);
        transaction.setTransactionUpdateTimestamp(new Timestamp(new Date().getTime()));
        session.update(transaction);

    }

    public void rejectTransaction(String transactionid, String username) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM Transaction where TRANSACTION_ID =:transactionId");
        query.setParameter("transactionId", transactionid);
        Transaction transaction = (Transaction) query.list().get(0);
        transaction.setTransactionState(com.sbs.internetbanking.enums.TransactionStatus.DECLINED.toString());
        transaction.setTransactionAprrovedBy(username);
        transaction.setTransactionUpdateTimestamp(new Timestamp(new Date().getTime()));
        session.update(transaction);

    }

    public void deleteTransactionByTransactionId(String transactionId) {
        Session session = sessionFactory.getCurrentSession();
        List<Transaction> transactionList =
                session.createQuery("FROM Transaction where TRANSACTION_ID=" + transactionId).list();
        for(Transaction transaction : transactionList){
            deleteTransaction(transaction);
        }
    }
}