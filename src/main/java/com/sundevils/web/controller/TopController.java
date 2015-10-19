package com.sundevils.web.controller;


import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.ModifyUsersHandler;
import handlers.adminHandlers.UnlockInternalAccountHandler;
import handlers.adminHandlers.ValidateUserhandler;
import handlers.adminHandlers.ViewUsersHandler;
import handlers.adminHandlers.transactionViewRequestHandler;
import handlers.adminHandlers.updateAllowHandler;
import handlers.employeeHandlers.CheckSourceAccountNumberHandler;
import handlers.employeeHandlers.CreateTransactionHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import utilities.CaptchaUtility;
import utilities.OtpUtility;

import com.user.info.Transactions.TransactionRequestDetails;


@Controller
public class TopController {
	long startOtpTime = 0;
	long startEmailTime = 0;
	long startTime = 0;
	int count = 0;
	@RequestMapping(value = {"/","/welcome**" }, method = RequestMethod.GET)
	public ModelAndView welcomePage(HttpServletRequest request,HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is welcome page!");
		model.setViewName("index");
		return model;

	}

	@RequestMapping(value = {"/transact**" }, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView transactPage(HttpServletRequest request,HttpServletResponse response) throws IOException {

		ModelAndView model = new ModelAndView();
		CreateTransactionHandler handler = new CreateTransactionHandler();
		String userName = "";
		String transamount = "";
		String sourceacc = "";
		String destacc = "";
		String type = "";

		if(request.getParameter("submit")!=null){
			userName = request.getParameter("username");
			transamount = request.getParameter("transamount");
			sourceacc = request.getParameter("sourceacc");
			destacc = request.getParameter("destacc");
			type = request.getParameter("type");

			if(userName.isEmpty() || transamount.isEmpty() || sourceacc.isEmpty() || destacc.isEmpty() || type.isEmpty())
			{
				model.addObject("success_msg", "Error: There are empty fields. Please rectify");
			}

			else
			{
				CheckSourceAccountNumberHandler accounthandler = new CheckSourceAccountNumberHandler(); 
				Boolean account_match=(Boolean) accounthandler.requestHandler(userName,sourceacc);
				if(account_match)
					model.addObject("success_msg", handler.transactionHandler(userName,transamount,sourceacc,destacc,type));
				else
					model.addObject("success_msg","Invalid username or source account for user does not exist");

			}

		}

		else
		{
			model.addObject("success_msg","");
		}	

		model.addObject("title", "Transaction");
		model.addObject("message", "This is transaction insert page!");
		model.setViewName("create_transactions");
		return model;

	}

	@RequestMapping(value = "/admin**", method = RequestMethod.GET)
	public ModelAndView adminPage() {

		ModelAndView model = new ModelAndView();
		model.addObject("title", "Spring Security Custom Login Form");
		model.addObject("message", "This is protected page!");
		model.setViewName("admin");
		return model;

	}

	@RequestMapping(value = "/viewusers", method = RequestMethod.GET)
	public ModelAndView managerPage() {

		ModelAndView model = new ModelAndView();
		ViewUsersHandler handler = new ViewUsersHandler(); 
		model.addObject("users", handler.requestHandler());
		model.addObject("title", "All users in the database");
		model.addObject("message", "This is protected page!");
		model.setViewName("viewusers");
		return model;

	}
	
	@RequestMapping(value = "/modifyUs", method = {RequestMethod.POST,RequestMethod.GET})
	public ModelAndView modifyUsersPage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException  {
		ModelAndView model = null;
		try{
		String searchParameter = "";
		String deleteParameter="";
		String searchParameterType = "";
	    model = new ModelAndView();	
		if (request.getParameter("submit")!=null){
		
		searchParameter = request.getParameter("username");
		searchParameterType = request.getParameter("searchcat");
		System.out.println(searchParameterType);
		ModifyUsersHandler handler = new ModifyUsersHandler(); 
		model.addObject("users", handler.requestHandler(searchParameter,searchParameterType));
		model.addObject("title", "All users in the database");
		model.addObject("message", "This is protected page!");
		model.setViewName("modifyUsers");
		}
		
		else if (request.getParameter("delete")!= null){
		System.out.println(request.getParameter("hiddenUser"));	
		deleteParameter =request.getParameter("hiddenUser");
		ModifyUsersHandler handler = new ModifyUsersHandler(); 
		handler.deleteRequestHandler(deleteParameter);
		searchParameter = request.getParameter("hiddenUser");
		searchParameterType = "UserName";
		System.out.println(searchParameterType);
		
		model.addObject("users", handler.requestHandler(searchParameter,searchParameterType));
		model.addObject("title", "All users in the database");
		model.addObject("message", "This is protected page!");
		model.setViewName("modifyUsers");
			
		}
		else if (request.getParameter("modify")!= null)
		{	String s= "hello";
		    byte[] b = s.getBytes();
		    System.out.println(b);
			ModifyUsersHandler handler = new ModifyUsersHandler();
		    searchParameter = request.getParameter("hiddenUser");
		    searchParameterType = "UserName";
			model.addObject("singleUser", handler.requestHandler(searchParameter,searchParameterType));
			model.addObject("title", "All users in the database");
			model.setViewName("modifyUser");
		}
		else if (request.getParameter("save")!=null){
			
		String[] Parameters= new String[11];
		System.out.println("here" +request.getParameter("Name"));
		Parameters[0]= request.getParameter("Name");
		Parameters[1]=request.getParameter("firstName");
		Parameters[2]= request.getParameter("lastName");
		Parameters[3]= request.getParameter("email");
		Parameters[4]= request.getParameter("phonenumber");
		Parameters[5]= request.getParameter("passport");
		Parameters[6]= request.getParameter("address");
		Parameters[7]= request.getParameter("state");
		Parameters[8]= request.getParameter("zip");
		Parameters[9]= request.getParameter("businessLicense");
		Parameters[10]= request.getParameter("dateOfBirth");
		ModifyUsersHandler handler = new ModifyUsersHandler(); 
		handler.updateRequestHandler(Parameters);
		model.setViewName("modifyUser");
		
		
		}
	
		else {
			model.setViewName("modifyUsers");
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return model;

	}

	@RequestMapping(value = "/captcha" , method = RequestMethod.GET)
	public ModelAndView generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ModelAndView model = new ModelAndView();
		CaptchaUtility captcha = new CaptchaUtility();
		captcha.generateCaptcha(request,response);
		model.setViewName("login");
		return model;
	}

	@RequestMapping(value = {"/login**"}, method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		ModelAndView model = null;
		try{
			model= new ModelAndView();
			String userName = "";
			String password = "";
			String captchaData = "";
			String captchaString = "";
			if(request.getParameter("submit")!=null){
				userName = request.getParameter("username");
				password = request.getParameter("password");
				captchaData = request.getParameter("captcha");
				captchaString=(String)session.getAttribute("CAPTCHA");
				
				if(userName.equals("") || password.equals("") ||
						captchaData.equals("")){
					model.addObject("emptyFields", "All fields are mandatory");
					model.setViewName("login");
				}
				else if(!captchaData.equals(captchaString)){
					model.addObject("wrongCaptcha", "Please re-enter captcha");
					model.setViewName("login");
				}
				
				else{
					LoginHandler handler = new LoginHandler(); 
					ResultSet rs = handler.requestLoginHandler(userName);
					if(rs.next()){
						String uName = rs.getString("username");
						String fName = rs.getString("firstname");
						String pass = rs.getString("usercurrentpassword");
						String role = rs.getString("usertype");
						int loggedIn = rs.getInt("isloggedin");
						int lock = rs.getInt("islocked");
						session.setAttribute("USERNAME", userName);
						if(lock == 0){
							if(uName.equals(userName) && pass.equals(password)){
								if(loggedIn == 0){
									handler.updateLoggedInFlag(userName,1);
									if(role.equals("SYSTEM_MANAGER")){
										request.getSession().setAttribute("Manager", fName);
										request.getSession().setAttribute("managerUsername", uName);
										request.getSession().setAttribute("isUserLoggedIn",true);
										request.getSession().setAttribute("Role", role);
										model.setViewName("managerhome");
									}
									else if(role.equals("EMPLOYEE")){
										request.getSession().setAttribute("Employee", fName);	
										model.setViewName("employeehome");
									}
									else if(role.equals("ADMIN")){
										request.getSession().setAttribute("Admin", fName);	
										request.getSession().setAttribute("AdminUsername", uName);
										request.getSession().setAttribute("Role", role);
										model.setViewName("admin");
									}
									else if(role.equals("MERCHANT")){
										request.getSession().setAttribute("Merchant", fName);	
										request.getSession().setAttribute("MerchantUsername", uName);
										model.setViewName("merchanthome");
									}
									else if(role.equals("USER")){
										request.getSession().setAttribute("isUserLoggedIn","Set");
										request.getSession().setAttribute("User", fName);	
										request.getSession().setAttribute("CustomerUsername", uName);
										request.getSession().setAttribute("Role", role);
										model.setViewName("customerhome");
									}
									else if(role.equals("GOVERNMENT")){
										request.getSession().setAttribute("Government", fName);	
										model.setViewName("governmenthome");
									}
								}
								else{
									model.addObject("loggedIn", "User is already logged in to the other system");
									model.setViewName("login");
								}
							}
							else{
								count++;
								if(count>2){
									handler.updateLockedFlag(userName,1);
									model.setViewName("unlockaccount");
								}
								else{
									model.addObject("wrongCredentials", "Username and Password do not match");
									model.setViewName("login");
								}
							}
						}
						else{
							model.setViewName("unlockaccount");
						}
					}
					else{
						model.addObject("wrongCredentials", "Username does not exist. Please enter correct username");
						model.setViewName("login");
					}
				}
			}
			else if(request.getParameter("imgCaptcha")!=null){
				CaptchaUtility captcha = new CaptchaUtility();
				captcha.generateCaptcha(request,response);
				model.setViewName("login");
			}
			else{
				LoginHandler handler;
				handler = new LoginHandler();
				handler.updateLoggedInFlag(userName,0);
				model.setViewName("login");
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET) 
	public ModelAndView formView() { 
		ModelAndView model = new ModelAndView();	
		model.setViewName("form"); return model; 
	} 

	@RequestMapping(value = "/readForm", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView formReader(HttpServletRequest request) { 
		ModelAndView model = new ModelAndView(); 
		if(request.getParameter("admin") != null) 
			model.addObject("userType", "Admin"); 
		else if(request.getParameter("user") != null) 
			model.addObject("userType", "User"); 
		Object obj = request.getParameter("userId"); 
		model.addObject("userId", obj); 
		model.setViewName("form"); return model; 
	}

	@RequestMapping(value = "/logoutusers", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView logoutUser(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String userName = "";
		handler = new LoginHandler();
		userName=(String)session.getAttribute("USERNAME");
		handler.updateLoggedInFlag(userName,0);
		model.setViewName("logout"); 
		return model;
	}

	@RequestMapping(value = "/forgotusername", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView forgotUserName(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		model.setViewName("forgotusername"); 
		return model;
	}

	@RequestMapping(value = "/forgotpassword", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView forgotPassword(HttpServletRequest request,HttpSession session) { 
		ModelAndView model = new ModelAndView();	
		model.setViewName("forgotpassword"); 
		return model;
	}

	@RequestMapping(value = "/getusername", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView getUserName(HttpServletRequest request,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String emailAddress = "";
		handler = new LoginHandler();
		emailAddress=request.getParameter("emailAddress");
		if(emailAddress.equals("") || emailAddress == null){
			model.addObject("emptyFields", "Please enter email address");
			model.setViewName("forgotusername");
		}
		else{
			ResultSet rs = handler.getUsername(emailAddress);
			String userName = "";
			OtpUtility otp = new OtpUtility(); 
			if(rs.next()){
				userName = rs.getString("username");
				otp.sendUserName(request, userName, emailAddress);
				model.setViewName("getusernamesuccess"); 
			}
			else{
				model.addObject("wrongemail", "Please enter correct email address");
				model.setViewName("forgotusername");
			}
		}
		return model;
	}

	@RequestMapping(value = "/getpassword", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView getPassword(HttpServletRequest request,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String emailAddress = "";
		String userName = "";
		String otpCode = "";
		handler = new LoginHandler();
		String otpString = "";
		String user = "";
		String email = "";
		if(request.getParameter("submit")!=null){

			emailAddress=request.getParameter("email");
			userName=request.getParameter("username");
			otpCode = request.getParameter("otpCode");
			otpString = (String)session.getAttribute("OTP");
			long diff = System.currentTimeMillis() - startEmailTime;
			int minutes = (int) ((diff / (1000*60)) % 60);
			if(minutes > 3){
				otpString = "";
			}
			if(emailAddress.equals("") || userName.equals("") || otpCode.equals("")){
				model.addObject("emptyFields", "All fields are mandatory");
				model.setViewName("forgotpassword");
			}
			else if(!otpString.equals(otpCode)){
				model.addObject("wrongOtp", "Otp code does not match");
				model.setViewName("forgotpassword");
			}
			else{
				ResultSet rs = handler.requestLoginHandler(userName);
				if(rs.next()){
					user = rs.getString("username");
					email = rs.getString("email");
					if(user.equals(userName) && email.equals(emailAddress)){
						session.setAttribute("USERNAME", userName);
						model.addObject("user", user);
						model.setViewName("resetpassword");
					}
					else{
						model.addObject("incorrectFields","Either username and/or email address is incorrect");
						model.setViewName("forgotpassword");
					}
				}
				else{
					model.addObject("incorrectFields","Either username and/or email address is incorrect");
					model.setViewName("forgotpassword");
				}
			}
		}

		else if(request.getParameter("otpButton")!=null){
			OtpUtility otp = new OtpUtility();
			otp.sendOtp(request);
			startEmailTime = System.currentTimeMillis();
			model.setViewName("forgotpassword");
		}
		return model;
	}

	@RequestMapping(value = "/updateAllow", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updateAllow(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException {

		String role = (String)session.getAttribute("Role");
		if(role.equals("MANAGER"))
		{
			String toUserName = "";
			String fromUserName = "";
			String userType = "";
			String type = "";
			ModelAndView model = new ModelAndView();
			List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
			if(request.getParameter("submit")!=null){
				toUserName = request.getParameter("username");
				userType = request.getParameter("userType");
				fromUserName = (String)session.getAttribute("managerUsername");
				ValidateUserhandler validate_Handler = new ValidateUserhandler();
				ResultSet result = validate_Handler.ValidateHandler(toUserName);
				//result.next();
				if (result.next() )
				{
					type = result.getString("Usertype");
					if (type.equals(userType))
					{
						updateAllowHandler handler = new updateAllowHandler();
						handler.requestUpdateHandler(toUserName,fromUserName,"View");
					}
					else
					{
						model.addObject("Validity", "Invalid Recipient");
					}
				}
				else
				{
					model.addObject("Validity", "Invalid Recipient");
				}
			}

			transactionViewRequestHandler handler_request = new transactionViewRequestHandler(); 
			ResultSet rs = handler_request.getRequestHandler();
			try {
				while(rs.next())
				{
					TransactionRequestDetails view = new TransactionRequestDetails();
					view.setRequstID(rs.getString("requestid"));
					view.setRqstTo(rs.getString("requestto"));
					view.setRqstFrom(rs.getString("requestfrom"));
					view.setRqstType(rs.getString("requesttype"));
					view.setRqstTime(rs.getString("requestdate"));
					view.setRqstStatus(rs.getString("requeststatus"));
					transReqstdetails.add(view);
				}
				model.addObject("requestDetails",transReqstdetails);
				//request.setAttribute(", o);
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			model.setViewName("allowViewRequests");

			return model;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("login");
			return model;
		}

	}

	@RequestMapping(value = "/unlockinternal**", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView unlockinternalPage(HttpServletRequest request,HttpServletResponse response) {

		ModelAndView model = new ModelAndView();
		UnlockInternalAccountHandler handler = new UnlockInternalAccountHandler();
		String usrname="";
		if(request.getParameter("submit")!=null)
		{
			usrname=request.getParameter("username");
			System.out.println(usrname);
			if(usrname.isEmpty())
			{
				model.addObject("unlock_msg","Error empty field");
			}
			else
			{
				model.addObject("unlock_msg",handler.requestHandler(usrname));
			}

		}


		model.addObject("title", "Unlocking internal user");
		model.setViewName("unlockinternaluser");

		return model;

	}

	@RequestMapping(value = "/unlockaccount**", method = {RequestMethod.GET,RequestMethod.POST})
	public ModelAndView unlockAccount(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException { 
		ModelAndView model = new ModelAndView();	
		LoginHandler handler;
		String accountNumber = "";
		String userName = "";
		String otpCode = "";
		handler = new LoginHandler();
		String otpString = "";
		String user = "";
		String account = "";
		String admin = "";
		if(request.getParameter("submit")!=null){

			accountNumber=request.getParameter("account");
			userName=request.getParameter("username");
			otpCode = request.getParameter("otpCode");
			otpString = (String)session.getAttribute("OTP");
			long diff = System.currentTimeMillis() - startTime;
			int minutes = (int) ((diff / (1000*60)) % 60);
			if(minutes > 3){
				otpString = "";
			}
			if(accountNumber.equals("") || userName.equals("") || otpCode.equals("")){
				model.addObject("emptyFields", "All fields are mandatory");
				model.setViewName("unlockaccount");
			}
			else if(!otpString.equals(otpCode)){
				model.addObject("wrongOtp", "Otp code does not match");
				model.setViewName("unlockaccount");
			}
			else{
				ResultSet rs = handler.requestAccountHandler(userName);
				ResultSet rs1 = handler.requestAdminHandler("ADMIN");
				if(rs.next() && rs1.next()){
					user = rs.getString("username");
					account = rs.getString("accountnumber");
					admin = rs1.getString("username");
					if(user.equals(userName) && account.equals(accountNumber)){
						handler.insertUnlockRequests(user,"UnlockAccount", user, admin,"test", "Pending","test","test");
						model.setViewName("unlockaccountsuccess");
					}
					else{
						model.addObject("incorrectFields","Either username and/or account number is incorrect");
						model.setViewName("unlockaccount");
					}
				}
				else{
					model.addObject("incorrectFields","Either username and/or account number is incorrect");
					model.setViewName("unlockaccount");
				}
			}
		}

		else{
			String userSession = (String) session.getAttribute("USERNAME");
			handler = new LoginHandler();
			ResultSet rs = handler.requestLoginHandler(userSession);
			String email = "";
			if(rs.next()){
				email = rs.getString("email");
			}
			OtpUtility otp = new OtpUtility();
			otp.sendOtp(request,email);
			startTime = System.currentTimeMillis();
			model.setViewName("unlockaccount");
		}
		return model;
	}

	/*	@RequestMapping(value = "/accessRequests" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView allowViewAccess(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		ModelAndView model = new ModelAndView();
		String user="";
		String role = "";
		String type = "";
		model.setViewName("accessRequests");
		//System.out.println((String)request.getSession().getAttribute("isUserLoggedIn"));
		type = request.getParameter("Type");
		List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
		RequestAuthorize authorize = new RequestAuthorize(); 
		role = (String)session.getAttribute("Role");

		if(role.equals("USER"))
		{
		user = (String)session.getAttribute("CustomerUsername");
		}
		else if(role.equals("ADMIN"))
		{
		user = (String)session.getAttribute("AdminUsername");
		}
		ResultSet rs = authorize.getRequestHandler(user);
		try {
			while(rs.next())
			{
				TransactionRequestDetails view = new TransactionRequestDetails();
				view.setRequstID(rs.getString("requestid"));
				view.setRqstFrom(rs.getString("requestfrom"));
				view.setRqstTime(rs.getString("requestdate"));
				view.setRqstStatus(rs.getString("requeststatus"));
				transReqstdetails.add(view);
			}
			if(request.getParameter("submit")!=null){

			}

			model.addObject("requestApprove",transReqstdetails);
			//request.setAttribute(", o);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}*/

	/*	@RequestMapping(value = "/updateaccessRequests" , method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updateViewAccess(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		ModelAndView model = new ModelAndView();
		String user="";
		String role = "";
		model.setViewName("accessRequests");
		//System.out.println((String)request.getSession().getAttribute("isUserLoggedIn"));
		List<TransactionRequestDetails> transReqstdetails=new ArrayList<TransactionRequestDetails>();
		RequestAuthorize authorize = new RequestAuthorize(); 
		role = (String)session.getAttribute("Role");

		if(role.equals("USER"))
		{
		user = (String)session.getAttribute("CustomerUsername");
		}
		else if(role.equals("ADMIN"))
		{
		user = (String)session.getAttribute("AdminUsername");
		}
		ResultSet rs = authorize.getRequestHandler(user);
		try {
			while(rs.next())
			{
				TransactionRequestDetails view = new TransactionRequestDetails();
				view.setRequstID(rs.getString("requestid"));
				view.setRqstFrom(rs.getString("requestfrom"));
				view.setRqstTime(rs.getString("requestdate"));
				view.setRqstStatus(rs.getString("requeststatus"));
				transReqstdetails.add(view);
			}
			model.addObject("requestApprove",transReqstdetails);
			//request.setAttribute(", o);
		} 
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model;
	}*/
}