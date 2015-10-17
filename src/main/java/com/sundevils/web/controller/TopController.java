package com.sundevils.web.controller;


import handlers.adminHandlers.LoginHandler;
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
	long startTime = 0;
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
			String otpData = "";
			String captchaString = "";
			String otpString = "";
			if(request.getParameter("submit")!=null){
				userName = request.getParameter("username");
				password = request.getParameter("password");
				captchaData = request.getParameter("captcha");
				otpData = request.getParameter("otpCode");
				captchaString=(String)session.getAttribute("CAPTCHA");
				otpString = (String)session.getAttribute("OTP");
				long diff = System.currentTimeMillis() - startTime;
				int minutes = (int) ((diff / (1000*60)) % 60);
				if(minutes > 3){
					otpString = "";
				}
				if(userName.equals("") || password.equals("") ||
						captchaData.equals("") || otpData.equals("")){
					model.addObject("emptyFields", "All fields are mandatory");
					model.setViewName("login");
				}
				else if(!captchaData.equals(captchaString)){
					model.addObject("wrongCaptcha", "Please re-enter captcha");
					model.setViewName("login");
				}
				else if(!otpString.equals(otpData)){
					model.addObject("wrongOtp", "Otp code does not match");
					model.setViewName("login");
				}
				else{
					LoginHandler handler = new LoginHandler(); 
					ResultSet rs = handler.requestLoginHandler(userName);
					if(rs.next()){
						String uName = rs.getString("username");
						String fName = rs.getString("Firstname");
						String pass = rs.getString("usercurrentpassword");
						String role = rs.getString("employeetype");
						int loggedIn = rs.getInt("isloggedin");
						if(uName.equals(userName) && pass.equals(password)){
							if(loggedIn == 0){
								session.setAttribute("USERNAME", userName);
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
							model.addObject("wrongCredentials", "Username and Password do not match");
							model.setViewName("login");
						}
					}
					else{
						model.addObject("wrongCredentials", "Username and Password do not match");
						model.setViewName("login");
					}
				}
			}
			else if(request.getParameter("imgCaptcha")!=null){
				CaptchaUtility captcha = new CaptchaUtility();
				captcha.generateCaptcha(request,response);
				model.setViewName("login");
			}
			else if(request.getParameter("otpButton")!=null){
				startTime = System.currentTimeMillis();
				OtpUtility otp = new OtpUtility();
				otp.sendOtp(request);
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
	
	@RequestMapping(value = "/updateAllow", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView updateAllow(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException {
        
		String role = (String)session.getAttribute("Role");
		if(role.equals("MANAGER"))
		{
		System.out.println(role);
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
			// TODO Auto-generated catch block
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