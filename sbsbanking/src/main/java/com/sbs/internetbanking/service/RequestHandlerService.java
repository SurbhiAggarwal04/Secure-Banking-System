package com.sbs.internetbanking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sbs.internetbanking.PKI.CertGen;
import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.Request;
import com.sbs.internetbanking.model.Transaction;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.persistence.AccountManager;
import com.sbs.internetbanking.persistence.RequestManager;
import com.sbs.internetbanking.persistence.SBSTransactionsManager;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.utilities.AccountGenerator;

public class RequestHandlerService {
	@Autowired
	AccountManager accountmanager;
	
	@Autowired
	UserManager userManager;
	
	@Autowired
    RequestManager requestManager;
	

	@Autowired
	SBSTransactionsManager sBSTransactionsManager;
	
	public String requestTypeOperation(Request request,User user)
	{
		
		//TODO Surbhi
	//	if(getRoleByUsername(username))
		//{
      
		if(request.getRequestType().equalsIgnoreCase("create_account"))
		{
			System.out.println("create account");
			Account account=AccountGenerator.generateAccount(request);
			if(account!=null)
			{
				
				String userName = request.getRequestFromAccountNum();
				User fromUser = userManager.getUserByUserName(userName);
				System.out.println(user.getUsername());
				String[] attachments=CertGen.certificateGeneration(fromUser.getUsername());
				System.out.println("attachments in reqman"+attachments[0]);
				accountmanager.saveAccount(account);
				//userManager.updateUser(user);
				CertGen cert= new CertGen();
				cert.sendNotificationEmail(fromUser, attachments);
				//requestManager.approveRequest(request.getRequestId(), user.getUsername());
			}
			else
			{
				return null;
			}
		}

		else if(request.getRequestType().equalsIgnoreCase("view_account"))
		{
			
		
		
		}
		//}
		
	/*	else if(request.getRequestType().equalsIgnoreCase("view_account"))
		{
		
		
		}
		else if(request.getRequestType().equalsIgnoreCase("modify_account"))
		{
		
		
		}
		else if(request.getRequestType().equalsIgnoreCase("delete_account"))
		{
		   if(request.getRequestType().contains("account"))
		   {
			   String reqContent[]=request.getRequestContent().split(",");
			   String accountNum=null;
			   for(int i=0;i<reqContent.length;i++)
			   {
				   if(reqContent[i].contains("account"))
				   {
					  String accountNumArray[]=reqContent[i].split(":");
					  accountNum=accountNumArray[1];
					   
				   }
			   }
			   int result=accountmanager.deleteAccountByAccountNumber(accountNum);
			   if(result>0)requestManager.approveRequest(request.getRequestId(), request.getRequestType(), username);
		   }
		   if(request.getRequestType().contains("username"))
		   {
			   String reqContent[]=request.getRequestContent().split(",");
			   String userName=null;
			   for(int i=0;i<reqContent.length;i++)
			   {
				   if(reqContent[i].contains("username"))
				   {
					  String usernameArray[]=reqContent[i].split(":");
					  userName=usernameArray[1];
					   
				   }
			   }
			   int result=accountmanager.deleteAccountsByUsername(userName);
			   if(result>0)requestManager.approveRequest(request.getRequestId(), request.getRequestType(), username);

		   }
		
		}*/
		return "success";
	}
	public boolean paymentHandler(Transaction transaction)
	{
		String content = transaction.getTransactionContent();
		if(content!=null){
			String[] strArray = content.split(":");
			String userName1 = userManager.getUserNameByAccount(transaction.getTransactionToAccountNum());
			if(strArray[0].equals(new CertGen().decryptCustomerMessageWithPublicKey(userName1,strArray[1]))){

				   Account fromUserAccount = accountmanager.getAccountDetails(transaction.getTransactionFromAccountNum());
		            double fromUserBalance = fromUserAccount.getBalance() + transaction.getTransactionAmount();
		            fromUserAccount.setBalance(fromUserBalance);
		            accountmanager.updateAccount(fromUserAccount);

		            Account toUserAccount = accountmanager.getAccountDetails(transaction.getTransactionToAccountNum());
		            double toUserBalance = toUserAccount.getBalance() - transaction.getTransactionAmount();
		            toUserAccount.setBalance(toUserBalance);
		            accountmanager.updateAccount(toUserAccount);
		            return true;
			
	}
		
		}
		return false;	
}
}
