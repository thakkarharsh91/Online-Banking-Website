package com.sundevils.web.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import soroosh.PaymentFormDataLoader;
import soroosh.PaymentInfo;
import soroosh.PaymentInfoValidator;
import soroosh.SorooshDatabaseConnection;
import utilities.OtpUtility;
import utilities.TimeUtility;

@Controller
public class SorooshController {

	private String userRole = "USER";
	private String merchantRole = "MERCHANT";
	private String managerRole = "SYSTEM_MANAGER";
	private String employeeRole = "EMPLOYEE";
	private String adminRole = "ADMIN";
	private String governmentRole = "GOVERNMENT";
	String modelTime="2015/10/24 00:00:00";
	String otpGenerateTime;


	private ModelAndView redirectToAccessDeniedPage(String role) {
		ModelAndView model = new ModelAndView();
		if(role == null){
			model.setViewName("login");
		}
		else if(role.equalsIgnoreCase(managerRole)){
			model.addObject("homeaddress", "managerhome");
			model.setViewName("Access.Denied");
		}
		else if(role.equalsIgnoreCase(employeeRole)){
			model.addObject("homeaddress", "employeehome");
			model.setViewName("Access.Denied");
		}
		else if(role.equalsIgnoreCase(adminRole)){
			model.addObject("homeaddress", "admin");
			model.setViewName("Access.Denied");
		}
		else if(role.equalsIgnoreCase(merchantRole)){
			model.addObject("homeaddress", "merchanthome");
			model.setViewName("Access.Denied");
		}
		else if(role.equalsIgnoreCase(userRole)){
			model.addObject("homeaddress", "customerhome");
			model.setViewName("Access.Denied");
		}
		else if(role.equalsIgnoreCase(governmentRole)){
			model.addObject("homeaddress", "governmenthome");
			model.setViewName("Access.Denied");
		}
		else{
			model.setViewName("login");
		}
		return model;
	}
	
	//returns false if the user has the required permission
	private boolean checkAccessCondition(HttpSession session, String role){
		if(session == null){
			return true;
		}
		if(((String)session.getAttribute("Role")) == null){
			return true;
		}
		if(!((String)session.getAttribute("Role")).equalsIgnoreCase(role))
			return true;
		return false;
	}

	@RequestMapping(value = {"/customerhome"}, method = RequestMethod.GET)
	public ModelAndView customerHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(checkAccessCondition(request.getSession(), userRole)){
			return redirectToAccessDeniedPage((String)request.getSession().getAttribute("Role"));
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("customerhome");
		return model;
	}

	@RequestMapping(value = {"/merchanthome"}, method = RequestMethod.GET)
	public ModelAndView merchantHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(checkAccessCondition(request.getSession(), merchantRole)){
			return redirectToAccessDeniedPage((String)request.getSession().getAttribute("Role"));
		}
		ModelAndView model = new ModelAndView();
		model.setViewName("merchanthome");
		return model;

	}

	@RequestMapping(value = {"/otpforpayment"}, method = RequestMethod.GET)
	public ModelAndView merchantHomePage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {

		String username = (String)session.getAttribute("USERNAME");

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			String email = handler.getEmailAddressForUsername(username);
			OtpUtility otp = new OtpUtility();
			
			otp.sendOtp(request, email);
			otpGenerateTime=TimeUtility.generateDateMethod()+" "+TimeUtility.generateHoursMethod()+":"+TimeUtility.generateMinutesMethod()+":"+TimeUtility.generateSecondsMethod();
			request.getSession().setAttribute("otpGenerateTime", otpGenerateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = {"/merchantstartpayment"}, method = RequestMethod.GET)
	public ModelAndView merchantRequestPaymentPage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {
		
		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView();
		PaymentFormDataLoader handler = new PaymentFormDataLoader();

		try {
			ArrayList<String> list = (ArrayList<String>)handler.getAllMerchantsForUserPayment();
			list.remove(session.getAttribute("USERNAME"));
			model.addObject("merchants", list);
			model.addObject("users", handler.getAllUsersForMerchantPayment());
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			e.printStackTrace();
			return model;
		}
		
		try {
			model.addObject("bankaccounts", handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME")));
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}

		model.setViewName("Merchant.Organization/Payment");
		return model;
	}

	@RequestMapping(value = {"/usermakepayment"}, method = RequestMethod.GET)
	public ModelAndView userPaymentPage(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) throws IOException {
		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView();
		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			model.addObject("merchants", handler.getAllMerchantsForUserPayment());
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}

		try {
			model.addObject("bankaccounts", handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME")));
		} catch (Exception e) {
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
			e.printStackTrace();
			return model;
		}
		model.setViewName("Individual.Customers/Payment");
		return model;

	}

	@RequestMapping(value = {"/readmerchantpaymentform"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantPaymentRequestFormReader(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) { 
		ModelAndView model = new ModelAndView(); 
		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		String payer = request.getParameter("payer");  
		String amount = request.getParameter("amount"); 
		String accountNumber = request.getParameter("accountNumber"); 
		String signature = request.getParameter("signature");

		ArrayList<String> errors = null;
		String username = (String)session.getAttribute("USERNAME");
		
		if(payer.equalsIgnoreCase(username)){
			errors = new ArrayList<String>();
			errors.add("Cannot request payment from self");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}
		
		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.merchantPaymentRequestValidateData(username, payer, amount, accountNumber, signature);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			errors.add("Internal error occured - try again later");
		}
		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}
		
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
		try {
			databaseConnection.putMerchantPaymentRequestInDatabase(payer, amount, accountNumber, 
					(String)session.getAttribute("USERNAME"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}
		model.addObject("message", "Payment successfully submitted");
		model.setViewName("Merchant.Organization/Confirm.Payment.Action");

		return model;
	}

	@RequestMapping(value = {"/readuserpaymentform"}, method = {RequestMethod.POST}) 
	public ModelAndView userPaymentFormReader(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) { 
		
		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 

		String merchantName = request.getParameter("merchantName");  
		String accountNumber = request.getParameter("accountNumber"); 
		String amount = request.getParameter("amount"); 
		String OTPMethod = request.getParameter("OTP"); 
		String OTPText = request.getParameter("otpText"); 
		String signature = request.getParameter("signature");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		ArrayList<String> errors = null;
		try {
			errors = formValidator.userPaymentValidateData(merchantName, accountNumber, amount,  OTPMethod, 
					OTPText, signature, session, (String)session.getAttribute("USERNAME"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors = new ArrayList<String>();
			errors.add("Internal error occured - try again later");
		}
		if(errors.size() == 0){
			model.setViewName("Individual.Customers/Review.Payment");

			double doubleAmount = Double.parseDouble(amount);
			PaymentInfo payment = new PaymentInfo((String)session.getAttribute("USERNAME"), accountNumber, 
					merchantName, doubleAmount);
			session.setAttribute("payment", payment);

			model.addObject("merchantname", merchantName);
			model.addObject("bankaccount", accountNumber);
			model.addObject("amount", amount);
		}
		else{
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
		}
		return model; 
	}

	@RequestMapping(value = {"/submituserpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView userPaymentSubmit(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 
		
		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 
		ArrayList<String> errors = new ArrayList<String>();

		if((request.getParameter("submit") != null) && (session.getAttribute("payment") != null)){
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				errors = databaseConnection.putCustomerPaymentInDatabase((PaymentInfo)session.getAttribute("payment"));
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
				errors.add("Internal error - please try again later");
				session.removeAttribute("payment");
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
				return model;
			}

			if(errors.size() == 0){
				session.removeAttribute("payment");
				model.addObject("message", "Payment successfully submitted");
				model.setViewName("Individual.Customers/Confirm.Payment.Action");
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
				return model;
			}
		}
		else if(request.getParameter("cancel") != null){
			session.removeAttribute("payment");
			model.addObject("message", "Payment successfully cancelled");
			model.setViewName("Individual.Customers/Confirm.Payment.Action");
		}
		else{
			errors.add("Unknown input - payment cancelled");
			session.removeAttribute("payment");
			model.setViewName("Individual.Customers/Confirm.Payment.Action");
		}

		return model; 
	}

	@RequestMapping(value = {"/merchantshowpayments"}, method = {RequestMethod.GET}) 
	public ModelAndView merchantShowAllPayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 
		
		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 
		Object allPaymentsTo = null;
		Object allPaymentsFrom = null;

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			allPaymentsTo = handler.getAllPyamentsToMerchant((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}

		try {
			allPaymentsFrom = handler.getAllPyamentsFromMerchant((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
			return model;
		}

		session.setAttribute("submittedPayments", allPaymentsTo);
		session.setAttribute("requestedPayments", allPaymentsFrom);

		model.addObject("payments", allPaymentsTo);
		model.addObject("paymentsRequests", allPaymentsFrom);
		model.setViewName("Merchant.Organization/Merchant.Authorize.Transactions");
		return model;
	}

	@RequestMapping(value = {"/usershowpayments"}, method = {RequestMethod.GET}) 
	public ModelAndView userShowAllPayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 
		
		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 
		Object allPayments = null;

		PaymentFormDataLoader handler = new PaymentFormDataLoader();
		try {
			allPayments = handler.getAllPyamentsToCustomer((String)session.getAttribute("USERNAME"));
		} catch (Exception e) {
			e.printStackTrace();
			ArrayList<String> errors = new ArrayList<String>();
			errors.add("Internal error - please try again later");
			model.addObject("errors", errors);
			model.setViewName("Individual.Customer/Payment.Error");
			return model;
		}

		session.setAttribute("submittedPayments", allPayments);
		model.addObject("payments", allPayments);
		model.setViewName("Individual.Customers/Customer.Authorize.Payments");
		return model;
	}

	@RequestMapping(value = {"/merchantpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantAuthorizePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null){
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		
		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("submittedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				PaymentInfo thisPayment = payments.get(iteration);
				session.setAttribute("acceptedPayment", thisPayment);

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.
							getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Merchant.Organization/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("payment_payer", thisPayment.getUsername());
				model.addObject("payment_amount", thisPayment.getAmount());

				model.setViewName("Merchant.Organization/Receive.Payment");
				return model;			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByMerchant(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		return merchantShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/merchantrequestpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantRequestPaymentsDecisions(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null)
			return goToMerchantErrorPage("Internal error - please try again later");
		
		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("requestedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				PaymentInfo thisPayment = payments.get(iteration);
				session.setAttribute("acceptedPayment", thisPayment);

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.
							getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Merchant.Organization/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("requester_merchant", thisPayment.getDestinationAccountNumber());
				model.addObject("request_payment_amount", thisPayment.getAmount());

				model.setViewName("Merchant.Organization/Pay.Payment.To.Merchant");
				return model;
			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByMerchant(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		session.removeAttribute("requestedPayments");
		return merchantShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/payerpaymentdecision"}, method = {RequestMethod.POST}) 
	public ModelAndView userPayerAuthorizePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ModelAndView model = new ModelAndView(); 
		int iteration = -1;

		if(request.getParameter("iteration") == null){
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		
		try{
			iteration = Integer.parseInt(request.getParameter("iteration"));
		} catch(NumberFormatException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error - please try again later");
		}
		ArrayList<PaymentInfo> payments = (ArrayList<PaymentInfo>)session.getAttribute("submittedPayments");

		if(payments == null || payments.size() == 0 || iteration > payments.size() || iteration < 0){
			return goToMerchantErrorPage("Internal error - please try again later");
		}

		int id = payments.get(iteration).getPaymentId();
		SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();

		try{
			if(request.getParameter("accept") != null) {
				session.setAttribute("acceptedPayment", payments.get(iteration));
				session.removeAttribute("submittedPayments");

				PaymentFormDataLoader handler = new PaymentFormDataLoader();
				try {
					Object accounts =  handler.getAllAccountsForUserPayment((String)session.getAttribute("USERNAME"));
					model.addObject("bankaccounts", accounts);
				} catch (Exception e) {
					ArrayList<String> errors = new ArrayList<String>();
					errors.add("Internal error - please try again later");
					model.addObject("errors", errors);
					model.setViewName("Individual.Customers/Payment.Error");
					e.printStackTrace();
					return model;
				}

				model.addObject("requester_merchant", payments.get(iteration).getDestinationAccountNumber());
				model.addObject("request_payment_amount", payments.get(iteration).getAmount());

				model.setViewName("Individual.Customers/Pay.Payment.To.Merchant");
				return model;
			}
			else if(request.getParameter("reject") != null) {
				databaseConnection.updatePaymentByPayer(id, false);
			}
		} catch(ClassNotFoundException | SQLException e){
			e.printStackTrace();
			return goToMerchantErrorPage("Internal error in payment authorization - try again later");
		}

		return userShowAllPayments(request, response, session);
	}

	@RequestMapping(value = {"/merchantreceivepayment"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantReceivePayments(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.receivePaymentDatabaseUpdate(payment.getPaymentId(), accountNumber);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Merchant.Organization/Confirm.Payment.Action");
				model.addObject("message", "You received " + payment.getAmount() + " into your account " + 
						accountNumber);
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Merchant.Organization/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	@RequestMapping(value = {"/merchantpayersubmitacceptpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView merchantPayerSubmitPaymentsToBank(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, merchantRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}
		
		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String signature = request.getParameter("signature"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment, signature);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Merchant.Organization/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.acceptPaymentDatabaseUpdate(payment, accountNumber, username);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Merchant.Organization/Confirm.Payment.Action");
				model.addObject("message", "You accepted to pay " + payment.getAmount() + " to " + 
						payment.getUsername());
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Merchant.Organization/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	@RequestMapping(value = {"/userpayersubmitacceptpayment"}, method = {RequestMethod.POST}) 
	public ModelAndView userPayerSubmitPaymentsToBank(HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) { 

		if(checkAccessCondition(session, userRole)){
			return redirectToAccessDeniedPage((String)session.getAttribute("Role"));
		}		
		
		ArrayList<String> errors = new ArrayList<String>();
		ModelAndView model = new ModelAndView(); 
		PaymentInfo payment = (PaymentInfo)session.getAttribute("acceptedPayment");
		String accountNumber = request.getParameter("accountNumber"); 
		String username = (String)session.getAttribute("USERNAME");

		PaymentInfoValidator formValidator = new PaymentInfoValidator();
		try {
			errors = formValidator.payerPaymentAcceptValidation(accountNumber, username, payment);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			errors.add("Internal error occured - try again later");
		}

		if(errors.size() != 0){
			model.addObject("errors", errors);
			model.setViewName("Individual.Customers/Payment.Error");
		}

		else{
			SorooshDatabaseConnection databaseConnection = new SorooshDatabaseConnection();
			try {
				databaseConnection.acceptPaymentDatabaseUpdate(payment, accountNumber, username);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				errors.add("Internal error occured - try again later");
			}

			if(errors.size() == 0){
				model.setViewName("Individual.Customers/Confirm.Payment.Action");
				model.addObject("message", "You accepted to pay " + payment.getAmount() + " to " + 
						payment.getDestinationAccountNumber());
			}
			else{
				model.addObject("errors", errors);
				model.setViewName("Individual.Customers/Payment.Error");
			}
		}

		session.removeAttribute("acceptedPayment");
		return model; 
	}

	private ModelAndView goToMerchantErrorPage(String message) {
		ModelAndView model = new ModelAndView(); 
		ArrayList<String> errors = new ArrayList<String>();
		errors.add(message);
		model.addObject("errors", errors);
		model.setViewName("Merchant.Organization/Payment.Error");
		return model;
	}
}
