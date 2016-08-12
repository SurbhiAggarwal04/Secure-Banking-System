package com.sbs.internetbanking.controllers;

import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.BankingTransaction;
import com.sbs.internetbanking.persistence.AccountManager;
import com.sbs.internetbanking.service.BankingFunctionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Controller
public class BankingFunctionsController {
    @Autowired
    private BankingFunctionsService bankingFunctionsService;
    @Autowired
    private AccountManager accountManager;

    @RequestMapping(value = "/creditfunds")
    public ModelAndView creditFundsPage(HttpServletRequest request) {
        ModelAndView creditModel = new ModelAndView();
        creditModel.setViewName("credit");

        String userName = request.getUserPrincipal().getName();
        List<String> accounts = getAllAccounts(userName);

        creditModel.addObject("accounts", accounts);
        creditModel.addObject("errorMessage", "");
        return creditModel;
    }

    @RequestMapping(value = "/creditConfirmation", method = RequestMethod.POST)
    public ModelAndView creditFundsConfirmation(@ModelAttribute BankingTransaction bankingTransaction) {
        ModelAndView creditModel = new ModelAndView();
        String accountNumber = bankingTransaction.getFromAccount();
        String amount = bankingTransaction.getAmount();
        Date transferDate = bankingTransaction.getTransferDate();
        if (accountNumber == null || amount == null || transferDate == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("credit");
            model.addObject("errorMessage", "Please enter all the fields");
            return model;
        }

        creditModel.setViewName("creditConfirmation");
        creditModel.addObject("accountNumber", accountNumber);
        creditModel.addObject("amount", amount);
        creditModel.addObject("transferDate", transferDate);
        creditFunds(Integer.parseInt(accountNumber.split("-")[1]), Integer.parseInt(amount), transferDate);
        return creditModel;
    }

    private void creditFunds(int accountNumber, int amount, Date timestamp) {

        bankingFunctionsService.creditFunds(accountNumber, amount, Instant.ofEpochMilli(timestamp.getTime()));
    }

    @RequestMapping(value = "/debitfunds")
    public ModelAndView debitFundsPage(HttpServletRequest request) {
        ModelAndView debitModel = new ModelAndView();
        debitModel.setViewName("debit");

        String userName = request.getUserPrincipal().getName();
        List<String> accounts = getAllAccounts(userName);

        debitModel.addObject("accounts", accounts);
        debitModel.addObject("errorMessage", "");

        return debitModel;
    }

    @RequestMapping(value = "/debitConfirmation", method = RequestMethod.POST)
    public ModelAndView debitFundsConfirmation(@ModelAttribute BankingTransaction bankingTransaction) {
        ModelAndView debitModel = new ModelAndView();
        String accountNumber = bankingTransaction.getFromAccount();
        String amount = bankingTransaction.getAmount();
        Date transferDate = bankingTransaction.getTransferDate();
        debitModel.setViewName("creditConfirmation");
        debitModel.addObject("accountNumber", accountNumber);
        debitModel.addObject("amount", amount);
        debitModel.addObject("transferDate", transferDate);

        if (accountNumber == null || amount == null || transferDate == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("debit");
            model.addObject("errorMessage", "Please enter all the fields");
            return model;
        }

        debitFunds(Integer.parseInt(accountNumber.split("-")[1]), Integer.parseInt(amount), transferDate);
        return debitModel;
    }

    private void debitFunds(int accountNumber, int amount, Date timestamp) {

        bankingFunctionsService.debitFunds(accountNumber, amount, Instant.ofEpochMilli(timestamp.getTime()));
    }

    @RequestMapping(value = "/transfer")
    public ModelAndView transferFundsPage(HttpServletRequest request) {
        ModelAndView transferModel = new ModelAndView();
        transferModel.setViewName("tranfer");

        String userName = request.getUserPrincipal().getName();
        List<String> accounts = getAllAccounts(userName);

        transferModel.addObject("accounts", accounts);
        transferModel.addObject("errorMessage", "");

        return transferModel;
    }

    @RequestMapping(value = "/transferConfirmation", method = RequestMethod.POST)
    public ModelAndView transferFundsPage(@ModelAttribute BankingTransaction bankingTransaction) {

        ModelAndView transferModel = new ModelAndView();

        String fromAccountNumber = bankingTransaction.getFromAccount();
        String toAccountNumber = bankingTransaction.getToAccount();
        String amount = bankingTransaction.getAmount();
        Date transferDate = bankingTransaction.getTransferDate();

        if (fromAccountNumber == null || toAccountNumber==null || amount == null || transferDate == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("transfer");
            model.addObject("errorMessage", "Please enter all the fields");
            return model;
        }

        transferModel.setViewName("transferConfirmation");
        transferModel.addObject("fromAccountNumber", fromAccountNumber);
        transferModel.addObject("toAccountNumber", toAccountNumber);
        transferModel.addObject("amount", amount);
        transferModel.addObject("date", transferDate);
        transferFunds(Integer.parseInt(fromAccountNumber.split("-")[1]), Integer.parseInt(toAccountNumber), Double.parseDouble(amount), transferDate);
        return transferModel;
    }

    private void transferFunds(int fromAccountNumber, int toAccountNumber, double amount, Date timestamp) {
        bankingFunctionsService.transferFunds(fromAccountNumber, toAccountNumber, amount, Instant.ofEpochMilli(timestamp.getTime()));
    }

    @RequestMapping(value = "/payment")
    public ModelAndView paymentsPage() {
        ModelAndView paymentModel = new ModelAndView();
        paymentModel.setViewName("payment");
        paymentModel.addObject("errorMessage", "");

        return paymentModel;
    }

    @RequestMapping(value = "/paymentConfirmation", method = RequestMethod.POST)
    public ModelAndView payments(@ModelAttribute BankingTransaction bankingTransaction) {
        ModelAndView paymentModel = new ModelAndView();

        String fromAccountNumber = bankingTransaction.getFromAccount();
        String toAccountNumber = bankingTransaction.getToAccount();
        String amount = bankingTransaction.getAmount();
        Date transferDate = bankingTransaction.getTransferDate();

        if (fromAccountNumber == null || toAccountNumber==null || amount == null || transferDate == null) {
            ModelAndView model = new ModelAndView();
            model.setViewName("payment");
            model.addObject("errorMessage", "Please enter all the fields");
            return model;
        }

        paymentModel.setViewName("paymentConfirmation");
        paymentModel.addObject("fromAccountNumber", fromAccountNumber);
        paymentModel.addObject("toAccountNumber", toAccountNumber);
        paymentModel.addObject("amount", amount);
        paymentModel.addObject("date", transferDate);
        payments(Integer.parseInt(fromAccountNumber), Integer.parseInt(toAccountNumber), Double.parseDouble(amount), transferDate);
        return paymentModel;
    }

    private void payments(int fromAccountNumber, int toAccountNumber, double amount, Date timestamp) {
        bankingFunctionsService.merchantPayments(fromAccountNumber, toAccountNumber, amount, Instant.ofEpochMilli(timestamp.getTime()));
    }

    private List<String> getAllAccounts(String userName) {
        List<Account> accountsList = accountManager.getAllAccountsByUserName(userName);
        List<String> accountDetails = new ArrayList<>();
        for (Account account : accountsList) {
            accountDetails.add(account.getAccountType() + "-" + account.getAccountNumber());
        }

        return accountDetails;
    }
}