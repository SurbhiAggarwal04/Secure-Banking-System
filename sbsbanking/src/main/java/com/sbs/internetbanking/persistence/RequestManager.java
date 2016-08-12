package com.sbs.internetbanking.persistence;

import com.google.gson.Gson;
import com.sbs.internetbanking.enums.RequestStatus;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.enums.TransactionStatus;
import com.sbs.internetbanking.enums.TransactionType;
import com.sbs.internetbanking.model.*;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class RequestManager {

	@Autowired
	SessionFactory sessionFactory;

	@Autowired
	UserManager userManager;

	@Transactional
	public void saveRequest(Request request) {
		Session session = sessionFactory.getCurrentSession();
		session.persist(request);
	}

	@Transactional
	public void deleteRequest(Request request) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(request);
	}

	@Transactional
	public void updateRequest(Request request) {
		Session session = sessionFactory.getCurrentSession();
		session.update(request);
	}

	// @Transactional
	public void declineRequest(Request requestId, RequestType reqType) {

	}

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
	public String getRequestId(String requestTypeId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from RequestType where REQUEST_TYPE = :id ");
		query.setParameter("id", requestTypeId);
		List<RequestType> list = query.list();
		System.out.println(list.get(0).getReuest_type_id());
		return list.get(0).getReuest_type_id();
		 
		
	}
	
	@Transactional
	public List<RequestType> getRequestType() {
		Session session = sessionFactory.getCurrentSession();
		return session.createQuery("FROM RequestType").list();
		
	}
	
	@Transactional
	public List getRequestList(User user) {
		Session session = sessionFactory.getCurrentSession();
		com.sbs.internetbanking.model.Role role=userManager.getRoleName(user.getRole());
		Query query= session.createQuery("FROM Request where REQ_FOR =:role AND REQ_STATUS= :status");
		query.setParameter("role", role.getRoleName());
		query.setParameter("status", RequestStatus.PENDING_APPROVAL.toString());
		List<Object> reqList= query.list();		
		
		//String role=Role.EMPLOYEE.toString();
		query = session.createQuery("FROM Transaction where TRANSACTION_APPROVED_BY=:role AND STATE= :state");
		query.setParameter("role", role);
		query.setParameter("state", TransactionStatus.PENDING_APPROVAL.toString());
		List<Object> transactionsList = query.list();
		System.out.println("Transactions"+transactionsList.size());
		reqList.addAll(transactionsList);
		return reqList;
	}

	@Transactional
	public List getRequestList(String userName) {
		Session session = sessionFactory.getCurrentSession();
		User user = userManager.getUserByUserName(userName);
		String role = Roles.ROLE_EMPLOYEE.name();
		Query query = session.createQuery("FROM Request where REQ_FOR =:role");
		query.setParameter("role", role);
		List<Object> reqList = query.list();

		//String role=Role.EMPLOYEE.toString();
		query = session.createQuery("FROM Transaction where TRANSACTION_APPROVED_BY=:role");
		query.setParameter("role", role);
		List<Object> transactionsList = query.list();
		System.out.println("Transactions" + transactionsList.size());
		reqList.addAll(transactionsList);
		return reqList;
	}
	
	@Transactional
	public List<Transaction> getExternalTransactionRequests(User user) {
		Session session = sessionFactory.getCurrentSession();
		Role role=userManager.getRoleName(user.getRole());
		
		//String role=Role.EMPLOYEE.toString();
		Query query = session.createQuery("FROM Transaction where (TRANSACTION_APPROVED_BY=:role) AND (STATE= :state OR STATE= :state1)");
		query.setParameter("role", role.getRoleName());
		query.setParameter("state", TransactionStatus.PENDING_APPROVAL.toString());
		query.setParameter("state1", TransactionStatus.PROCESSING.toString());
		List<Transaction> transactionsList = query.list();
	
		return transactionsList;
	}
	
	@Transactional
	public List<Request> getInternalRequests(User user) {
		Session session = sessionFactory.getCurrentSession();
		Role role=userManager.getRoleName(user.getRole());
		Query query= session.createQuery("FROM Request where REQ_FOR =:role AND REQ_STATUS= :status");
		query.setParameter("role", role.getRoleName());
		query.setParameter("status", RequestStatus.PENDING_APPROVAL.toString());
		List<Request> reqList=query.list();
		return reqList;
	}
	
	 @Transactional
	public void approveRequest(String requestId, User user) {
		Session session = sessionFactory.getCurrentSession();
		Query query= session.createQuery("FROM Request where REQ_ID =:requestId");
		query.setParameter("requestId", requestId);
		Request request= (Request) query.list().get(0);
		request.setRequestStatus(com.sbs.internetbanking.enums.RequestStatus.APPROVED.toString());
		request.setRequestApprovedBy(user.getUsername());
		session.update(request);
		
//		switch (reqType) {
//		case "CREATE_ACCOUNT":
////			String userName = request.getRequestFromAccountNum();
////			User user = userManager.getUserByUserName(userName);
////			System.out.println(user.getUsername());
////			String[] attachments=CertGen.certificateGeneration(user.getUsername());
////			System.out.println("attachments in reqman"+attachments[0]);
////			user.setAccountStatus(AccountStatus.ACTIVE.toString());
////			userManager.updateUser(user);
////			CertGen cert= new CertGen();
////			cert.sendNotificationEmail(user.getUsername(), attachments);
//			break;
//		case "DEBIT":
//			break;
//		case "CREDIT":
//			break;
//		}
	}
	 @Transactional
		public void declineRequest(String requestId, String reqType) {
			Session session = sessionFactory.getCurrentSession();
			Query query= session.createQuery("FROM Request where REQ_ID =:requestId");
			query.setParameter("requestId", requestId);
			Request request= (Request) query.list().get(0);
			System.out.println("request type"+request);
			request.setRequestStatus("DECLINED");
			session.update(request);

		}
	 @Transactional
		public Request getRequestById(String requestId) {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Request where REQ_ID =:requestId AND REQ_STATUS= :state");
			query.setParameter("requestId", requestId);
			query.setParameter("state", RequestStatus.PENDING_APPROVAL.name());
			List<Request> reqList= query.list();
			System.out.println(reqList.get(0).getRequestContent());
			return reqList.get(0);
		}
	 
	 
	 @Transactional
		public List<Transaction> getRequestedPayments(String accountNum) {
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Transaction where TRANSACTION_TO_USER =:accountNum AND STATE= :state AND TRANSACTION_TYPE= :type");
			query.setParameter("accountNum", accountNum);
			query.setParameter("state", RequestStatus.PENDING_APPROVAL.name());
			query.setParameter("type", TransactionType.PAYMENT.name());
			List<Transaction> reqList= query.list();
			
			return reqList;
		}
	
}
