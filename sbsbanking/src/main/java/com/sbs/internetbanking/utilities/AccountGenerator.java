package com.sbs.internetbanking.utilities;


import java.util.Date;
import java.util.Random;

import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.Request;

public class AccountGenerator {
public static Account generateAccount(Request request)
{
	Account account=new Account();
	System.out.println("account generator");
//	if(request.getRequestContent().contains("username") && request.getRequestContent().contains("account type"))
//	{
//		System.out.println("getting generated");
	/*	String requestContentArray[]=request.getRequestContent().split(",");
		for(int i=0;i<requestContentArray.length;i++)
		{
			if(requestContentArray[i].contains("username"))
			{
				String usernameArray[]=requestContentArray[i].split(":");
				account.setUserName(usernameArray[1]);
			}
			if(requestContentArray[i].contains("account type"))
			{
				String[] accountType=requestContentArray[i].split(":");
				account.setAccountType(accountType[1]);
			}
		}
	*/
	    account.setAccountType("CHECKING");
		account.setAccountCreationTimestamp(new Date());
		account.setAccountNumber(IdentifierGenerator.generateAccountNumber());
		account.setAccountStatus(AccountStatus.ACTIVE.toString());
		account.setBalance(0.0);
		account.setLastUpdateTimestamp(new Date());
		account.setUserName(request.getRequestFromAccountNum());
		return account;
		
		
//	}
	
}
}
