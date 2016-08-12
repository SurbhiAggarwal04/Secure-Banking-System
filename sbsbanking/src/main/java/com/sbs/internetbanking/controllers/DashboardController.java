package com.sbs.internetbanking.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.sbs.internetbanking.enums.RequestStatus;
import com.sbs.internetbanking.enums.RequestType;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.enums.TransactionStatus;
import com.sbs.internetbanking.enums.TransactionType;
import com.sbs.internetbanking.model.Account;
import com.sbs.internetbanking.model.Form;
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
import com.sbs.internetbanking.utilities.CreatePDF;
import com.sbs.internetbanking.utilities.IdentifierGenerator;
import com.sbs.internetbanking.utilities.TransactionTableGenerator;

@Controller
public class DashboardController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	UserManager userManager;

	@Autowired
	ContentManager contentManager;

	@Autowired
	SBSTransactionsManager transactionManager;

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

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	AccountManager accountManager;

	@RequestMapping(value = "/getProfile", method = RequestMethod.GET)
	public ModelAndView getProfile(HttpServletRequest request) {

		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		ModelAndView model = new ModelAndView();
		User user = userManager.getUserByUserName(principal.getName());
		System.out.println("date" + user.getUserProfileInfo().getDob());
		System.out.println("address" + user.getUserProfileInfo());

		model.addObject("user", user);
		model.setViewName("viewProfile");
		return model;
	}

	@RequestMapping(value = "/getUpdateProfile", method = RequestMethod.GET)
	public ModelAndView getUpdateProfile(HttpServletRequest request) {
		User userForm = new User();
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		User user = userManager.getUserByUserName(principal.getName());
		System.out.println(user.getUserProfileInfo().getSsn());
		model.addObject("userForm", userForm);
		model.addObject("user", user);
		model.setViewName("updateProfile");
		return model;
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public ModelAndView updateProfile(@ModelAttribute("userForm") User userForm, HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		System.out.println("date" + userForm.getUserProfileInfo().getDob());
		System.out.println("address" + userForm.getUserProfileInfo());
		System.out.println(userForm.getUserProfileInfo().getSsn());
		Principal principal = request.getUserPrincipal();
		User user = userManager.getUserByUserName(principal.getName());
		if (user.compareTo(userForm) == 0) {
			user.getUserProfileInfo().setDob(userForm.getUserProfileInfo().getDob());
			user.getUserProfileInfo().setFirstname(userForm.getUserProfileInfo().getFirstname());
			user.getUserProfileInfo().setLastname(userForm.getUserProfileInfo().getLastname());
			user.getUserProfileInfo().setEmailId(userForm.getUserProfileInfo().getEmailId());
			user.getUserProfileInfo().setPhone(userForm.getUserProfileInfo().getPhone());
			user.getUserProfileInfo().setDob(userForm.getUserProfileInfo().getDob());
			user.getUserProfileInfo().getAddress().setAddressLine1(userForm.getUserProfileInfo().getAddress().getAddressLine1());
			user.getUserProfileInfo().getAddress().setAddressLine2(userForm.getUserProfileInfo().getAddress().getAddressLine2());
			user.getUserProfileInfo().getAddress().setCity(userForm.getUserProfileInfo().getAddress().getCity());
			user.getUserProfileInfo().getAddress().setState(userForm.getUserProfileInfo().getAddress().getState());
			user.getUserProfileInfo().getAddress().setAreaCode(userForm.getUserProfileInfo().getAddress().getAreaCode());
		}
		userManager.updateUser(user);
		model.addObject("user", user);
		model.setViewName("viewProfile");
		return model;
	}

	@RequestMapping(value = "/getAccount", method = RequestMethod.GET)
	public ModelAndView getAccount(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
		List<Account> accounts = accountmanager.getAccounts(principal.getName());
		for (int i = 0; i < accounts.size(); i++) {
			accounts.get(i).setAccountNumber(
					"........"
							+ accounts
									.get(i)
									.getAccountNumber()
									.substring(accounts.get(i).getAccountNumber().length() - 5,
											accounts.get(i).getAccountNumber().length()));
		}
		ModelAndView model = new ModelAndView();
		model.addObject("form", new Form());
		model.addObject("accounts", accounts);
		model.setViewName("account");
		return model;
	}

	/*
	 * @RequestMapping(value = "/viewRequests") public ModelAndView
	 * getRequests(HttpServletRequest request) { ModelAndView model = new
	 * ModelAndView(); Principal principal = request.getUserPrincipal();
	 * System.out.println(principal.getName()); User user = new User();
	 * System.out.println("inside viewrequests"); //int roleId = user.getRoles()
	 * 
	 * model.addObject("form",new Form()); List<Request> requestList
	 * =requestManager.getRequestList(principal.getName());
	 * System.out.println("requestlist"+requestList);
	 * model.addObject("requestlist",requestList);
	 * model.setViewName("viewRequests");
	 * 
	 * return model; }
	 */

	/*
	 * @RequestMapping(value = "/approvenotification") public ModelAndView
	 * setApproverequests(HttpServletRequest request ,@ModelAttribute("form")
	 * Form form) { ModelAndView model = new ModelAndView(); Principal principal
	 * = request.getUserPrincipal(); System.out.println(principal.getName());
	 * Map samplemap = form.getMap(); System.out.println("approvalrequests");
	 * 
	 * model.setViewName("viewRequests"); model.addObject("form",new Form());
	 * String requestId=(String) samplemap.get("requestId"); Request
	 * finalrequest= requestManager.getRequestById(requestId); String reqType=
	 * finalrequest.getRequestType(); requestManager.approveRequest(requestId,
	 * reqType);
	 * 
	 * return model; }
	 */
	/*
	 * @RequestMapping(value = "/rejectnotification") public ModelAndView
	 * setDeclinerequests(HttpServletRequest request ,@ModelAttribute("form")
	 * Form form) { ModelAndView model = new ModelAndView(); Principal principal
	 * = request.getUserPrincipal(); System.out.println(principal.getName());
	 * Map samplemap = form.getMap(); System.out.println("reject ");
	 * 
	 * model.setViewName("viewRequests"); model.addObject("form",new Form());
	 * String requestId=(String) samplemap.get("requestId"); Request
	 * finalrequest= requestManager.getRequestById(requestId); String reqType=
	 * finalrequest.getRequestType(); requestManager.declineRequest(requestId,
	 * reqType);
	 * 
	 * return model; }
	 */

	@RequestMapping(value = "/raiseRequest")
	public ModelAndView CreateRequest() {
		ModelAndView model = new ModelAndView();
		model.addObject("requestObj", new Request());
		model.setViewName("createRequest");

		return model;
	}

	@RequestMapping(value = "/submitRequest", method = RequestMethod.POST)
	public ModelAndView submitRequest(@ModelAttribute("requestObj") Request requestObj, HttpServletRequest request) {
		// DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		/*
		 * String choice = request.getParameter("choiceRadio"); String reqType =
		 * request.getParameter("reqType"); Request requestObj = new Request();
		 * String requestContent = ""; String username = ""; if
		 * (request.getParameter("username") != null ||
		 * request.getParameter("username") != "") {
		 * 
		 * String username=request.get requestObj.setReqUsername();
		 * 
		 * } if (request.getParameter("accountType") != null ||
		 * request.getParameter("accountType") != "") { String accountType =
		 * request.getParameter("accountType"); requestContent = requestContent
		 * + "," + "Account Type:" + accountType; } if
		 * (request.getParameter("accountNum") != null ||
		 * request.getParameter("accountNum") != "") { String accountNum =
		 * request.getParameter("accountNum"); requestContent = requestContent +
		 * "," + "Account Number:" + accountNum; } if
		 * (request.getParameter("transactionId") != null ||
		 * request.getParameter("transactionId") != "") { String transactionId =
		 * request.getParameter("transactionId"); requestContent =
		 * requestContent + "," + "Transaction Id:" + transactionId; }
		 * 
		 * // String reqTypeId=requestManager.getRequestId(reqType); Principal
		 * principal = request.getUserPrincipal();
		 */

		/*
		 * if (choice.equalsIgnoreCase("customer")) {
		 * requestObj.setRequestFor(username); } else if
		 * (choice.equalsIgnoreCase("sysAdmin")) {
		 * requestObj.setRequestFor(Roles.ROLE_ADMINISTRATOR.name()); } else {
		 */

		/* requestObj.setRequestType(reqType); */

		User user = (User) request.getSession().getAttribute("user");
		requestObj.setCreationTimeStamp(new Date());
		requestObj.setRequestStatus(RequestStatus.PENDING_APPROVAL.toString());
		requestObj.setRequestApprovedBy("ANONYMOUS");
		requestObj.setRequestFromAccountNum(user.getUsername());
		requestObj.setRequestId(IdentifierGenerator.getRequestId());

		requestManager.saveRequest(requestObj);
		ModelAndView model = new ModelAndView();
		model.addObject("successRequestMessage", "Request has been successfully submitted");
		model.setViewName("createRequest");

		return model;
	}

	/*
	 * @RequestMapping(value = "/createRequestform" ) public ModelAndView
	 * CreateRequest(HttpServletRequest request,@ModelAttribute("request")
	 * Request raiserequest) { ModelAndView model = new ModelAndView();
	 * Principal principal = request.getUserPrincipal();
	 * 
	 * model.setViewName("createRequest");
	 * raiserequest.setRequestFromAccountNum(principal.getName());
	 * raiserequest.setRequestId("123"); // check this
	 * raiserequest.setRequestContent("creation");
	 * raiserequest.setCreationTimeStamp(Calendar.getInstance().getTime());
	 * raiserequest.setRequestStatus("PENDING_APPROVAL");
	 * requestManager.raiseRequest(raiserequest);
	 * 
	 * 
	 * return model; }
	 * 
	 * @RequestMapping(value = "/viewExternaluserprofile" ) public ModelAndView
	 * viewProfile() { ModelAndView model = new ModelAndView();
	 * model.setViewName("viewExternaluserprofile"); model.addObject("form", new
	 * Form()); return model;
	 * 
	 * }
	 * 
	 * @RequestMapping(value = "/viewExternaluserprofileform" ) public
	 * ModelAndView getProfile(@ModelAttribute("form") Form
	 * form,HttpServletRequest request) { ModelAndView model = new
	 * ModelAndView(); Map samplemap = form.getMap();
	 * model.setViewName("viewExternaluserprofile"); String userName= (String)
	 * samplemap.get("userName"); System.out.println("givenusername"+userName);
	 * 
	 * Principal principal = request.getUserPrincipal(); String
	 * requesterUsername=principal.getName();
	 * System.out.println("retrieveduser"+requesterUsername);
	 * //model.setViewName("createRequest");
	 * 
	 * List<Request> reqList=
	 * requestManager.viewAuthorization(requesterUsername, userName); Request
	 * finalrequest = null; if(reqList!=null){ finalrequest = reqList.get(0);
	 * User user = userManager.getUserByUserName(userName);
	 * finalrequest.setRequestStatus("INACTIVE");
	 * requestManager.updateRequest(finalrequest); }
	 * System.out.println(reqList); return model;
	 * 
	 * }
	 */

	@RequestMapping(value = "/getStatement", method = RequestMethod.GET)
	public ModelAndView getStatement(HttpServletRequest request, @ModelAttribute("form") Form form) {
		/*
		 * Principal principal = request.getUserPrincipal(); List
		 * accounts=accountmanager.getAccounts(principal.getName());
		 */
		ModelAndView model = new ModelAndView();
		Map map = form.getMap();
		if (map.containsKey("accountNum")) {
			String accountNumber = (String) map.get("accountNum");
			accountNumber = accountNumber.replace(".", "");
			String button = request.getParameter("btnStatement");

			System.out.println(map.get("accountNum"));
			System.out.println(button);
			if (button.equalsIgnoreCase("get statement")) {

				List<Transaction> transactionList = transactionManager.getTransactions(accountNumber);
				if (transactionList.size() > 0) {
					for (int i = 0; i < transactionList.size(); i++) {
						transactionList.get(i).setTransactionFromAccountNum(
								"......."
										+ transactionList
												.get(i)
												.getTransactionFromAccountNum()
												.substring(transactionList.get(i).getTransactionFromAccountNum().length() - 5,
														transactionList.get(i).getTransactionFromAccountNum().length()));
						if(trans.getactionList)
						
						transactionList.get(i).setTransactionToAccountNum(
								"......."
										+ transactionList
												.get(i)
												.getTransactionToAccountNum()
												.substring(transactionList.get(i).getTransactionToAccountNum().length() - 5,
														transactionList.get(i).getTransactionToAccountNum().length()));

					}
					model.addObject("accountTransactionList", transactionList);
					model.addObject("accountNum", accountNumber);

					System.out.println("listadded");
				}

				else {

					model.addObject("accountTransactionList", null);

				}
			} else {

				model.addObject("accountTransactionList", null);

			}
		} else {
			model.addObject("NoSelectionError", "No Selection made");
		}
		model.setViewName("userAccountRespectiveTransaction");
		return model;
	}

	@RequestMapping(value = "/getAllStatement", method = RequestMethod.GET)
	public ModelAndView getAllStatement(HttpServletRequest request) {
		// int numOfRecordPerPage=10;
		Principal principal = request.getUserPrincipal();
		List<Account> accounts = accountmanager.getAccounts(principal.getName());
		ModelAndView model = new ModelAndView();
		model.addObject("transactions", null);
		ArrayList<Transaction> transList = new ArrayList<Transaction>();

		List<Transaction> transactionList = transactionManager.getAllTransactions(accounts);
		// int numberOfPages=0;
		if (transactionList.size() != 0) {
			for (int i = 0; i < transactionList.size(); i++) {
				transactionList.get(i).setTransactionFromAccountNum(
						"......."
								+ transactionList
										.get(i)
										.getTransactionFromAccountNum()
										.substring(transactionList.get(i).getTransactionFromAccountNum().length() - 5,
												transactionList.get(i).getTransactionFromAccountNum().length() - 1));
				transactionList.get(i).setTransactionToAccountNum(
						"......."
								+ transactionList
										.get(i)
										.getTransactionToAccountNum()
										.substring(transactionList.get(i).getTransactionToAccountNum().length() - 5,
												transactionList.get(i).getTransactionToAccountNum().length() - 1));

			}

			System.out.println(transList.size());
			model.addObject("transactions", transactionList);
			model.addObject("accounts", accounts);

		} else {
			model.addObject("transactionFecthingError", "No Transaction has been made yet!");
		}
		transList.addAll(transactionList);
		model.setViewName("userAllTransactions");
		return model;
	}

	@RequestMapping(value = "/downloadStatement", method = RequestMethod.GET)
	public void downloadStatement(HttpServletRequest request, HttpServletResponse response) {

		String accountNum = request.getParameter("accountNum");
		List<Transaction> transactionList = new ArrayList<Transaction>();
		ModelAndView model = new ModelAndView();
		if (accountNum == null || accountNum == "") {

			Principal principal = request.getUserPrincipal();
			List<Account> accounts = accountmanager.getAccounts(principal.getName());

			model.addObject("transactions", null);
			transactionList = transactionManager.getAllApprovedTransactions(accounts);
			model.setViewName("userAccountRespectiveTransaction");

		} else {
			transactionList = transactionManager.getApprovedTransactions(accountNum);
			model.setViewName("userAllTransactions");
		}

		ArrayList<String> trsactionsString = TransactionTableGenerator.generateTable(transactionList);

		System.out.println("Inside");
		final ServletContext servletContext = request.getSession().getServletContext();
		final File tempDirectory = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		final String temperotyFilePath = tempDirectory.getAbsolutePath();

		String fileName = "statement.pdf";
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);

		try {
			System.out.println(trsactionsString.size());
			CreatePDF.createPDF(temperotyFilePath + "\\" + fileName, trsactionsString);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos = convertPDFToByteArrayOutputStream(temperotyFilePath + "\\" + fileName);
			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
		} catch (Exception e1) {
			// e1.printStackTrace();
		}

	}

	private ByteArrayOutputStream convertPDFToByteArrayOutputStream(String fileName) {

		InputStream inputStream = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {

			inputStream = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			baos = new ByteArrayOutputStream();

			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, bytesRead);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return baos;
	}

	@RequestMapping(value = { "/getRequestType" }, method = RequestMethod.GET)
	public @ResponseBody String getSecurityQuestions() {
		Gson gson = new Gson();

		System.out.println("inside request");

		return (gson.toJson(requestManager.getRequestType()).toString());
	}

	@RequestMapping(value = "/viewRequests")
	public ModelAndView getRequests(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		// User user = new User();
		System.out.println("inside viewrequests");
		// int roleId = user.getRoles()

		model.addObject("form", new Form());
		ArrayList<Object> requestList = (ArrayList<Object>) requestManager.getRequestList(principal.getName());
		ArrayList<Request> requests = new ArrayList<Request>();
		System.out.println("requestList" + requestList);
		System.out.println(requestList.size());
		ListIterator it = requestList.listIterator();
		for (int i = 0; i < requestList.size(); i++) {

			System.out.println("inside request");
			requests.add((Request) requestList.get(i));

		}

		System.out.println("requestlist" + requestList);
		model.addObject("requestlist", requests);
		model.setViewName("viewRequests");

		return model;
	}

	@RequestMapping(value = "/viewTransactions")
	public ModelAndView getTransactions(HttpServletRequest request) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		System.out.println("inside view transactions");

		User user = userManager.getUserByUserName(principal.getName());
		model.addObject("form", new Form());
		List<Transaction> transactionList = transactionManager.getTransactionsByRole(user.getRole());
		List<Transaction> transactions = new ArrayList<Transaction>();
		for (Transaction transaction : transactionList) {
			transactions.add(transaction);
		}

		model.addObject("transactionslist", transactions);
		model.setViewName("viewTransactions");

		return model;
	}

	@RequestMapping(value = "/approvetransaction", method = RequestMethod.POST)
	public ModelAndView approveTransactions(HttpServletRequest request, @ModelAttribute("form") Form form) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		Map samplemap = form.getMap();
		System.out.println("approval transaction requests");
		model.addObject("form", new Form());
		String transactionId = (String) samplemap.get("transactionId");
		System.out.println(transactionId);
		Transaction transaction = transactionManager.getTransactionById(transactionId);
		transaction.setTransactionState(TransactionStatus.APPROVED.name());
		transactionManager.updateTransaction(transaction);
		transactionService.updateAccount(transaction.getTransactionFromAccountNum(), transaction.getTransactionToAccountNum(),
				transaction.getTransactionAmount(), transaction.getTransactionType());

		model.setViewName("viewTransactions");
		return model;
	}

	@RequestMapping(value = "/rejecttransaction", method = RequestMethod.POST)
	public ModelAndView rejectTransactions(HttpServletRequest request, @ModelAttribute("form") Form form) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		Map samplemap = form.getMap();
		System.out.println("approval transaction requests");
		model.addObject("form", new Form());
		String transactionId = (String) samplemap.get("transactionId");
		System.out.println(transactionId);
		Transaction transaction = transactionManager.getTransactionById(transactionId);
		transaction.setTransactionState(TransactionStatus.DECLINED.name());
		model.setViewName("viewTransactions");
		transactionManager.updateTransaction(transaction);

		return model;
	}

	@RequestMapping(value = "/approvenotification", method = RequestMethod.POST)
	public ModelAndView setApproverequests(HttpServletRequest request, @ModelAttribute("form") Form form) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		Map samplemap = form.getMap();
		System.out.println("approvalrequests");
		model.addObject("form", new Form());
		if (samplemap.containsKey("requestId")) {
			String requestId = (String) samplemap.get("requestId");
			System.out.println(requestId);
			model.addObject("viewRequestSucessMessage", "Request has been approved");
			Request finalrequest = requestManager.getRequestById(requestId);
			String reqType = finalrequest.getRequestType();
			/*
			 * String result = requestHandlerService.requestTypeOperation(
			 * finalrequest, principal.getName());
			 */
			// requestManager.approveRequest(requestId, reqType,
			// principal.getName());
		} else if (samplemap.containsKey("transactionId")) {
			String transactionId = (String) samplemap.get("transactionId");
			System.out.println(transactionId);
			/*
			 * Request finalrequest= requestManager.getRequestById(requestId);
			 * String reqType= finalrequest.getRequestType();
			 */
			model.addObject("viewRequestSucessMessage", "Transaction has been approved");
			// requestManager.approveRequest(requestId, reqType,
			// principal.getName());
			transactionManager.approveTransaction(transactionId, principal.getName());
		} else {
			model.addObject("approveFailure", "Please select request to approve");
		}
		model.setViewName("viewRequests");
		return model;
	}

	@RequestMapping(value = "/rejectnotifications", method = RequestMethod.POST)
	public ModelAndView setDeclinerequests(HttpServletRequest request, @ModelAttribute("form") Form form) {
		ModelAndView model = new ModelAndView();
		Principal principal = request.getUserPrincipal();
		System.out.println(principal.getName());
		Map samplemap = form.getMap();

		model.addObject("form", new Form());
		if (samplemap.containsKey("requestId")) {
			String requestId = (String) samplemap.get("requestId");
			Request finalrequest = requestManager.getRequestById(requestId);
			System.out.println(requestId);
			String reqType = finalrequest.getRequestType();
			System.out.println(reqType);
			requestManager.declineRequest(requestId, reqType);
			model.addObject("viewRequestSucessMessage", "Request has been declined");
		} else if (samplemap.containsKey("transactionId")) {
			String transactionId = (String) samplemap.get("transactionId");
			System.out.println(transactionId);
			/*
			 * Request finalrequest= requestManager.getRequestById(requestId);
			 * String reqType= finalrequest.getRequestType();
			 */
			model.addObject("viewRequestSucessMessage", "Transaction has been declined");
			// requestManager.approveRequest(requestId, reqType,
			// principal.getName());
			transactionManager.rejectTransaction(transactionId, principal.getName());
		} else {
			model.addObject("approveFailure", "Please select request to deny");
		}

		model.setViewName("viewRequests");
		return model;
	}

	@RequestMapping(value = "/showTransactionsForInternalUserRequests")
	public ModelAndView showTransactionsForInternalUserRequests(HttpServletRequest servletRequest) {
		ModelAndView requestModel = new ModelAndView();
		String userName = servletRequest.getUserPrincipal().getName();
		List<Request> requestList = requestManager.getRequestList(userName);
		List<Transaction> transactionList = new ArrayList<>();
		for (Request request : requestList) {
			if (RequestStatus.APPROVED.name().equals(request.getRequestStatus())) {
				if (RequestType.VIEW_TRANSACTION.name().equals(request.getRequestType())) {
					if (request.getRequestTransactionId() != null) {
						Transaction transaction = transactionManager.getTransactionById(request.getRequestTransactionId());
						transactionList.add(transaction);
					} else {
						List<Transaction> transactions = transactionManager.getTransactions(request.getRequestAccountNum());
						transactionList.addAll(transactions);
					}
				} else if (RequestType.MODIFY_TRANSACTION.name().equals(request.getRequestType())) {
					Transaction transaction = transactionManager.getTransactionById(request.getRequestTransactionId());
					transaction.setTransactionAmount(Double.parseDouble(request.getRequestAmount()));
					transactionManager.updateTransaction(transaction);
				} else if (RequestType.DELETE_TRANSACTION.name().equals(request.getRequestType())) {
					transactionManager.deleteTransactionByTransactionId(request.getRequestTransactionId());
				}
			}
		}
		requestModel.addObject("transactionList", transactionList);

		return requestModel;
	}
	
	@RequestMapping(value = "/paymentRequest" )
	public ModelAndView submitPayment()
	{
		ModelAndView model = new ModelAndView();
		
		model.addObject("form", new Form());
		//model.addObject("transaction",new Transaction());
		model.setViewName("paymentRequest");
		return model;
		
	}
	
	@RequestMapping(value = "/paymentRequestform",method = RequestMethod.POST)
	public ModelAndView getSubmitpayment(@ModelAttribute("form") Form form,HttpServletRequest request )
	{
		
		ModelAndView model = new ModelAndView();
		Principal principal =request.getUserPrincipal();
		String requesterUsername=principal.getName();
		String savingAccount;
		List<Account> Accountlists=accountManager.getAccounts(requesterUsername);
		if(!CollectionUtils.isEmpty(Accountlists))
		{
			 savingAccount = Accountlists.get(0).getAccountNumber();
				Transaction transaction = new Transaction();
				String accountNum = form.getMap().get("AccountNumber");
				String amount =form.getMap().get("Amount");
				System.out.println("inside payment requestform");
				String randomNumber = form.getMap().get("RandomNumber");
				System.out.println("randomNumber"+randomNumber);
				System.out.println("amount"+amount);
				System.out.println("accountNum"+accountNum);
				transaction.setTransactionAmount(Double.parseDouble(amount));
				transaction.setTransactionToAccountNum(accountNum);
				transaction.setTransactionFromAccountNum(savingAccount);
				
				transaction.setTransactionId(IdentifierGenerator.generateTransactionid());
				transaction.setTransactionState(TransactionStatus.PENDING_APPROVAL.name());
				transaction.setTransactionType(TransactionType.PAYMENT.name());
				transaction.setTransactionContent(randomNumber);
				transaction.setTransactionAprrovedBy("CUSTOMER");
			
				transactionManager.saveTransaction(transaction);
				
				model.setViewName("paymentRequest");
		}
		/*else
		 savingAccount = Accountlists.get(0).getAccountNumber();*/

		
		return model;
		
	}
	

	public ModelAndView showAccountsForInternalUserRequests(HttpServletRequest servletRequest) {
		ModelAndView requestModel = new ModelAndView();
		String userName = servletRequest.getUserPrincipal().getName();
		String userRole = userManager.getUserByUserName(userName).getRole();

		if (Roles.ROLE_MANAGER.name().equals(userRole)) {

			List<Request> requestList = requestManager.getRequestList(userName);
			List<Account> accountList = new ArrayList<>();
			for (Request request : requestList) {
				if (RequestStatus.APPROVED.name().equals(request.getRequestStatus())) {
					if (RequestType.VIEW_ACCOUNT.name().equals(request.getRequestType())) {
						if (request.getRequestAccountNum() != null) {
							Account account = accountmanager.getAccountDetails(request.getRequestAccountNum());
							accountList.add(account);
						}
					} /*
					 * else if
					 * (RequestType.MODIFY_TRANSACTION.equals(request.getRequestType
					 * ())) { Transaction transaction =
					 * transactionManager.getTransactionById
					 * (request.getRequestTransactionId());
					 * transaction.setTransactionAmount
					 * (request.getRequestAmount());
					 * transactionManager.updateTransaction(transaction); }
					 */else if (RequestType.DELETE_ACCOUNT.name().equals(request.getRequestType())) {
						accountmanager.deleteAccountByAccountNumber(request.getRequestAccountNum());
					}
				}
			}
			requestModel.addObject("accountList", accountList);
		}

		return requestModel;
	}
}