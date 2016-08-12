package com.sbs.internetbanking.service;

import java.time.Instant;

public interface BankingFunctionsService {
    public void creditFunds(int accountNumber, double amount, Instant timestamp);

    public void debitFunds(int accountNumber, double amount, Instant timestamp);

    public void transferFunds(int fromAccountNumber, int toAccountNumber, double amount, Instant timestamp);

    public void merchantPayments(int customerAccountNumber, int merchantAccountNumber, double amount, Instant timestamp);
}