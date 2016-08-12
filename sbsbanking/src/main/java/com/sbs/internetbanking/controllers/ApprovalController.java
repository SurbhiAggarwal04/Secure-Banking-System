package com.sbs.internetbanking.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.asn1.x509.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sbs.internetbanking.PKI.CertGen;
import com.sbs.internetbanking.enums.RequestStatus;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.enums.TransactionStatus;
import com.sbs.internetbanking.enums.TransactionType;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.Request;
import com.sbs.internetbanking.model.Transaction;
import com.sbs.internetbanking.model.User;
import com.sbs.internetbanking.persistence.AccountManager;
import com.sbs.internetbanking.persistence.ContentManager;
import com.sbs.internetbanking.persistence.RequestManager;
import com.sbs.internetbanking.persistence.SBSTransactionsManager;
import com.sbs.internetbanking.persistence.UserManager;
import com.sbs.internetbanking.service.RequestHandlerService;
import com.sbs.internetbanking.service.TransactionService;

@Controller
public class ApprovalController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	UserManager userManager;

	@Autowired
	ContentManager contentManager;

	@Autowired
	SBSTransactionsManager sBSTransactionsManager;

	@Autowired
	AccountManager accountmanager;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	RequestManager requestManager;

	@Autowired
	RequestHandlerService requestHandlerService;

	@RequestMapping(value = "/viewExternalTransactions", method = RequestMethod.GET)
	public ModelAndView getProfile(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		List<Transaction> transactionlist = requestManager.getExternalTransactionRequests(user);
		System.out.println(transactionlist);
		model.addObject("etRequestList", transactionlist);
		model.setViewName("viewExternalTransaction");
		return model;
	}

	@RequestMapping(value = "/viewInternalRequests", method = RequestMethod.GET)
	public ModelAndView viewInternalRequests(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		List<Request> reqList = requestManager.getInternalRequests(user);
		System.out.println(reqList);
		model.addObject("requestlist", reqList);
		model.setViewName("viewInternalRequests");
		return model;
	}

	@RequestMapping(value = "/approveExternalTransaction", method = RequestMethod.POST)
	public ModelAndView approveExternalTransaction(@RequestParam("transId") String transId, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		Transaction transaction = sBSTransactionsManager.getTransactionById(transId);
		
		System.out.println(transaction.getTransactionType());
		if(transaction.getTransactionType().equalsIgnoreCase(TransactionType.PAYMENT.name()))
		{
			boolean result=requestHandlerService.paymentHandler(transaction);
		}
		else
		{
		transactionService.updateAccount(transaction.getTransactionFromAccountNum(), transaction.getTransactionToAccountNum(),
				transaction.getTransactionAmount(), transaction.getTransactionType());
		}
		transaction.setTransactionAprrovedBy(user.getUsername());
		transaction.setTransactionState(TransactionStatus.APPROVED.name());
		transaction.setTransactionUpdateTimestamp(new Timestamp(new Date().getTime()));
		
		sBSTransactionsManager.updateTransaction(transaction);
		// TODO: Girish Konda, amount deduction in account
		List<Transaction> transactionlist = requestManager.getExternalTransactionRequests(user);
		System.out.println(transactionlist);
		model.addObject("etRequestList", transactionlist);
		model.setViewName("viewExternalTransaction");
		return model;
	}

	@RequestMapping(value = "/rejectExternalTransaction", method = RequestMethod.POST)
	public ModelAndView rejectExternalTransaction(@RequestParam("transId") String transId, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		Transaction transaction = sBSTransactionsManager.getTransactionById(transId);
		transaction.setTransactionAprrovedBy(user.getUsername());
		transaction.setTransactionState(TransactionStatus.DECLINED.name());
		transaction.setTransactionUpdateTimestamp(new Timestamp(new Date().getTime()));
		sBSTransactionsManager.updateTransaction(transaction);
		List<Transaction> transactionlist = requestManager.getExternalTransactionRequests(user);
		System.out.println(transactionlist);
		model.addObject("etRequestList", transactionlist);
		model.setViewName("viewExternalTransaction");
		return model;
	}

	@RequestMapping(value = "/approveInternalRequest", method = RequestMethod.POST)
	public ModelAndView approveInternalRequest(@RequestParam("reqId") String reqId, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		Request requestObj = requestManager.getRequestById(reqId);

		String result = requestHandlerService.requestTypeOperation(requestObj, user);
		System.out.println(result);
		if (result != null) {
			requestObj.setApprovedTimeStamp(new Timestamp(new Date().getTime()));
			requestObj.setRequestApprovedBy(user.getUsername());
			requestObj.setRequestStatus(RequestStatus.APPROVED.name());
			requestManager.updateRequest(requestObj);
		} else {
			model.addObject("requestApproveError", "Some problem occurred");
		}
		// TODO: Girish Konda, amount deduction in account
		List<Request> reqList = requestManager.getInternalRequests(user);
		System.out.println(reqList);
		model.addObject("requestlist", reqList);
		model.setViewName("viewInternalRequests");
		return model;
	}

	@RequestMapping(value = "/rejectInternalRequest", method = RequestMethod.POST)
	public ModelAndView rejectInternalRequest(@RequestParam("reqId") String reqId, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		ModelAndView model = new ModelAndView();
		Request requestObj = requestManager.getRequestById(reqId);
		requestObj.setApprovedTimeStamp(new Timestamp(new Date().getTime()));
		requestObj.setRequestApprovedBy(user.getUsername());
		requestObj.setRequestStatus(RequestStatus.DECLINED.name());
		requestManager.updateRequest(requestObj);
		// TODO: Girish Konda, amount deduction in account
		List<Request> reqList = requestManager.getInternalRequests(user);
		System.out.println(reqList);
		model.addObject("requestlist", reqList);
		model.setViewName("viewInternalRequests");
		return model;
	}

	@RequestMapping(value = "/requestedPayments", method = RequestMethod.GET)
	public ModelAndView requestedPayments(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		List<Account> Accountlists = accountmanager.getAccounts(user.getUsername());
		ModelAndView model = new ModelAndView();
		if (!CollectionUtils.isEmpty(Accountlists)) {
			List<Transaction> transactionlist = requestManager.getRequestedPayments(Accountlists.get(0).getAccountNumber());
			System.out.println(transactionlist);
			model.addObject("transactionlist", transactionlist);
		}
		model.setViewName("viewPayments");
		return model;
	}

	@RequestMapping(value = "/approveRequestedPayment", method = RequestMethod.POST)
	public ModelAndView approveRequestedPayment(HttpServletRequest request, @RequestParam(name = "transId") String transId,
			@RequestParam(name = "randomNumber") String randomNumber) {
		ModelAndView model = new ModelAndView();
		Transaction tran = sBSTransactionsManager.getTransactionById(transId);
		tran.setTransactionContent(tran.getTransactionContent() + ":" + randomNumber);
		if(tran.getTransactionAmount()>1000)
		tran.setTransactionAprrovedBy(Roles.ROLE_MANAGER.name());
		else
		tran.setTransactionAprrovedBy(Roles.ROLE_EMPLOYEE.name());
		tran.setTransactionState(TransactionStatus.PROCESSING.name());
		sBSTransactionsManager.updateTransaction(tran);
		model.setViewName("viewPayments");
		User user = (User) request.getSession().getAttribute("user");
		List<Account> Accountlists = accountmanager.getAccounts(user.getUsername());
		List<Transaction> transactionlist = requestManager.getRequestedPayments(Accountlists.get(0).getAccountNumber());
		System.out.println(transactionlist);
		model.addObject("processingSuccess", "Transaction under process");
		model.addObject("transactionlist", transactionlist);
		return model;
	}
	
	@RequestMapping(value = "/rejectRequestedPayment", method = RequestMethod.POST)
	public ModelAndView rejectRequestedPayment(HttpServletRequest request, @RequestParam(name = "transId") String transId,
			@RequestParam(name = "randomNumber") String randomNumber) {
		ModelAndView model = new ModelAndView();
		Transaction tran = sBSTransactionsManager.getTransactionById(transId);

		tran.setTransactionState(TransactionStatus.DECLINED.name());
		sBSTransactionsManager.updateTransaction(tran);
		model.setViewName("viewPayments");
		User user = (User) request.getSession().getAttribute("user");
		List<Account> Accountlists = accountmanager.getAccounts(user.getUsername());
		List<Transaction> transactionlist = requestManager.getRequestedPayments(Accountlists.get(0).getAccountNumber());
		System.out.println(transactionlist);
		model.addObject("transactionlist", transactionlist);
		model.addObject("processingReject", "Transaction successfully rejected");
		return model;
	}
}