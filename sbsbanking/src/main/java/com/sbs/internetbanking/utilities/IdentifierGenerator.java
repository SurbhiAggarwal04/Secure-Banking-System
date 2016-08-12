package com.sbs.internetbanking.utilities;

import java.util.Random;

import com.sbs.internetbanking.model.Address;
import com.sbs.internetbanking.model.User;

public class IdentifierGenerator {

	public static String getAddressId() {
		return "AD" + generateRandomNumber();
	}

	public static String getRequestId() {
		return "R" + generateRandomNumber();
	}

	public static String generateAccountNumber() {

		return "A" + generateRandomNumber();
	}
	
	public static String generateTransactionid() {

		return "T" + generateRandomNumber();
	}

	private static String generateRandomNumber() {
		Random generator = new Random(System.currentTimeMillis());
		long acctNum = (generator.nextLong() + 9999999L);
		String accNumStr = String.valueOf(acctNum);
		System.out.println("acct"+accNumStr);
		if (accNumStr.contains("-"))
			accNumStr=accNumStr.replace("-", "");
		String truncatedAcctNum = accNumStr.substring(0, 10);
		System.out.println("truncated"+truncatedAcctNum);
		return truncatedAcctNum;
	}
}