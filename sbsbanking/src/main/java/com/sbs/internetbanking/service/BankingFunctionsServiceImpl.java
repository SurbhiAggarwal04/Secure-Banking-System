package com.sbs.internetbanking.service;

import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.enums.TransactionType;
import com.sbs.internetbanking.model.Transaction;
import com.sbs.internetbanking.persistence.AccountManager;
import com.sbs.internetbanking.persistence.RequestManager;
import com.sbs.internetbanking.persistence.SBSTransactionsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.time.Instant;

public class BankingFunctionsServiceImpl implements BankingFunctionsService {
    private final Logger logger = LoggerFactory.getLogger(BankingFunctionsServiceImpl.class);

    private static final String STATUS = "Pending Approval";
    private static final int SAME_ACCOUNT_TRANSACTION = -1;

    @Autowired
    private AccountManager accountManager;
    @Autowired
    private SBSTransactionsManager transactionsManager;

    @Override
    public void creditFunds(int accountNumber, double amount, Instant timestamp) {
        logger.info("accountNumber:" + accountNumber
                + ", amount:" + amount + ", timestamp"
                + timestamp + ", transaction type"
                + TransactionType.CREDIT.name());

        String transactionDetails = transactionDetails(accountNumber,
                accountDetails(accountNumber), SAME_ACCOUNT_TRANSACTION, amount);

        StringBuilder instant = new StringBuilder(timestamp.toString());
        String transactionId = instant.insert(0, "t").toString();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionType(TransactionType.CREDIT.name());
        transaction.setTransactionUpdateTimestamp(Timestamp.from(timestamp));
        transaction.setTransactionFromAccountNum(Integer
                .toString(accountNumber));
        transaction.setTransactionToAccountNum(null);
        transaction.setTransactionState(STATUS);
        transaction.setTransactionDetails(transactionDetails);
        transaction.setTransactionAmount(amount);

        if (amount > 1000) {
            transaction.setTransactionRole(Roles.ROLE_MANAGER.name());
        } else {
            transaction.setTransactionRole(Roles.ROLE_EMPLOYEE.name());
        }
        transactionsManager.saveTransaction(transaction);
    }

    @Override
    public void debitFunds(int accountNumber, double amount, Instant timestamp) {
        logger.info("accountNumber:" + accountNumber
                + ", amount:" + amount
                + ", timestamp" + timestamp
                + ", transaction type"
                + TransactionType.DEBIT.name());

        String transactionDetails = transactionDetails(accountNumber,
                accountDetails(accountNumber), SAME_ACCOUNT_TRANSACTION, amount);

        StringBuilder instant = new StringBuilder(Instant.now().toString());
        String transactionId = instant.insert(0, "t").toString();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionType(TransactionType.DEBIT.name());
        transaction.setTransactionUpdateTimestamp(Timestamp.from(timestamp));
        transaction.setTransactionFromAccountNum(Integer
                .toString(accountNumber));
        transaction.setTransactionToAccountNum(null);
        transaction.setTransactionState(STATUS);
        transaction.setTransactionDetails(transactionDetails);
        transaction.setTransactionAmount(amount);

        if (amount > 1000) {
            transaction.setTransactionRole(Roles.ROLE_MANAGER.name());
        } else {
            transaction.setTransactionRole(Roles.ROLE_EMPLOYEE.name());
        }
        transactionsManager.saveTransaction(transaction);
    }

    @Override
    public void transferFunds(int fromAccountNumber, int toAccountNumber,
                              double amount, Instant timestamp) {
        logger.info("fromAccountNumber:" + fromAccountNumber +
                ",toAccountNumber:" + toAccountNumber +
                ", amount:" + amount +
                ", timestamp" + timestamp +
                ", transaction type" + TransactionType.TRANSFER.name());

        String transactionDetails = transactionDetails(fromAccountNumber,
                accountDetails(fromAccountNumber), toAccountNumber, amount);

        StringBuilder instant = new StringBuilder(Instant.now().toString());
        String transactionId = instant.insert(0, "t").toString();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionType(TransactionType.TRANSFER.name());
        transaction.setTransactionUpdateTimestamp(Timestamp.from(timestamp));
        transaction.setTransactionFromAccountNum(Integer
                .toString(fromAccountNumber));
        transaction.setTransactionToAccountNum(Integer
                .toString(toAccountNumber));
        transaction.setTransactionState(STATUS);
        transaction.setTransactionDetails(transactionDetails);
        transaction.setTransactionAmount(amount);

        if (amount > 1000) {
            transaction.setTransactionRole(Roles.ROLE_MANAGER.name());
        } else {
            transaction.setTransactionRole(Roles.ROLE_EMPLOYEE.name());
        }
        transactionsManager.saveTransaction(transaction);
    }

    @Override
    public void merchantPayments(int customerAccountNumber,
                                 int merchantAccountNumber, double amount, Instant timestamp) {
        logger.info("fromAccountNumber:" + customerAccountNumber
                + ",toAccountNumber:" + merchantAccountNumber
                + ", amount:" + amount
                + ", timestamp" + timestamp
                + ", transaction type" + TransactionType.PAYMENT.name());

        String transactionDetails = transactionDetails(customerAccountNumber,
                accountDetails(customerAccountNumber), merchantAccountNumber,
                amount);

        StringBuilder instant = new StringBuilder(Instant.now().toString());
        String transactionId = instant.insert(0, "t").toString();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(transactionId);
        transaction.setTransactionType(TransactionType.PAYMENT.name());
        transaction.setTransactionUpdateTimestamp(Timestamp.from(timestamp));
        transaction.setTransactionFromAccountNum(Integer
                .toString(customerAccountNumber));
        transaction.setTransactionToAccountNum(Integer
                .toString(merchantAccountNumber));
        transaction.setTransactionState(STATUS);
        transaction.setTransactionDetails(transactionDetails);
        transaction.setTransactionAmount(amount);

        if (amount > 1000) {
            transaction.setTransactionRole(Roles.ROLE_MANAGER.name());
        } else {
            transaction.setTransactionRole(Roles.ROLE_EMPLOYEE.name());
        }
        transactionsManager.saveTransaction(transaction);
    }

    private String accountDetails(int accountNumber) {
        return accountManager
                .getAccountDetails(Integer.toString(accountNumber))
                .getBalance().toString();
    }

    private String transactionDetails(int fromAccountNumber, String balance,
                                      int toAccountNumber, double amountRequested) {

        if (toAccountNumber == SAME_ACCOUNT_TRANSACTION) {
            return "from: " + fromAccountNumber + " balance - " + balance
                    + " to: " + " requested amount - " + amountRequested;
        }

        return "from: " + fromAccountNumber + " balance - " + balance + " to: "
                + toAccountNumber + " requested amount - " + amountRequested;
    }
}