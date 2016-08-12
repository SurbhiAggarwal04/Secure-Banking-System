package com.sbs.internetbanking.service;

import com.sbs.internetbanking.enums.TransactionType;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.persistence.AccountManager;
import org.springframework.beans.factory.annotation.Autowired;

public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private AccountManager accountManager;

    public void updateAccount(String fromAccountNumber, String toAccountNumber, Double amount, String transactionType) {
        if (TransactionType.CREDIT.name().equals(transactionType)) {
            Account userAccount = accountManager.getAccountDetails(fromAccountNumber);
            double balance = userAccount.getBalance() + amount;
            userAccount.setBalance(balance);
            accountManager.updateAccount(userAccount);
        } else if (TransactionType.DEBIT.name().equals(transactionType)) {
            Account userAccount = accountManager.getAccountDetails(fromAccountNumber);
            double balance = userAccount.getBalance() - amount;
            userAccount.setBalance(balance);
            accountManager.updateAccount(userAccount);
        } else if (TransactionType.TRANSFER.name().equals(transactionType)) {
            Account fromUserAccount = accountManager.getAccountDetails(fromAccountNumber);
            double fromUserBalance = fromUserAccount.getBalance() - amount;
            fromUserAccount.setBalance(fromUserBalance);
            accountManager.updateAccount(fromUserAccount);

            Account toUserAccount = accountManager.getAccountDetails(fromAccountNumber);
            double toUserBalance = toUserAccount.getBalance() + amount;
            toUserAccount.setBalance(toUserBalance);
            accountManager.updateAccount(toUserAccount);
        }
   
        else
        {
        	
        }
    }
}
