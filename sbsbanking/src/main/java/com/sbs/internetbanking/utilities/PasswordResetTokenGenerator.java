package com.sbs.internetbanking.utilities;

import java.util.UUID;

import com.sbs.internetbanking.model.User;

public class PasswordResetTokenGenerator {

	public static String getPasswordResetTokenForUser(User user){
		String random =	UUID.randomUUID().toString();
		String[] tokens = random.split("-");
		System.out.println(tokens);
		String temp = tokens[0]+tokens[1]+tokens[2];
		System.out.println(temp);
		System.out.println(temp.substring(3,13));
		return temp.substring(3,13);
		
	}
}
