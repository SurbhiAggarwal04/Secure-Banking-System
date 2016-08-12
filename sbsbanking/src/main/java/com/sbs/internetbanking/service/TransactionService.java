package com.sbs.internetbanking.service;

public interface TransactionService {
    void updateAccount(String fromAccountNumber, String toAccountNumber, Double amount, String transactionType);
}