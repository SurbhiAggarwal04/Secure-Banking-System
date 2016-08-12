package com.sbs.internetbanking.utilities;

import com.sbs.internetbanking.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionTableGenerator {
    public static ArrayList<String> generateTable(List<Transaction> transactions) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < transactions.size(); i++) {

            Transaction transaction = transactions.get(i);
            String transactionstring = transaction.getTransactionUpdateTimestamp() + "," + transaction.getTransactionType() + "," + transaction.getTransactionFromAccountNum() + "," + transaction.getTransactionToAccountNum() + ","
                    + transaction.getTransactionAmount() + "," + transaction.getTransactionState() + "," + transaction.getTransactionDetails();
            result.add(transactionstring);
        }
        return result;
    }
}
