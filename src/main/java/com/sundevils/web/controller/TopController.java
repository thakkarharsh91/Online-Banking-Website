package com.sundevils.web.controller;


import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.ViewUsersHandler;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import utilities.CaptchaUtility;
import utilities.OtpUtility;

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
						String pass = rs.getString("usercurrentpassword");
						String role = rs.getString("employeetype");
						int loggedIn = rs.getInt("isloggedin");
						if(uName.equals(userName) && pass.equals(password)){
							if(loggedIn == 0){
								session.setAttribute("USERNAME", userName);
								handler.updateLoggedInFlag(userName,1);
								if(role.equals("SYSTEM_MANAGER")){
									model.setViewName("managerhome");
								}
								else if(role.equals("EMPLOYEE")){
									model.setViewName("employeehome");
								}
								else if(role.equals("ADMIN")){
									model.setViewName("admin");
								}
								else if(role.equals("MERCHANT")){
									model.setViewName("merchanthome");
								}
								else if(role.equals("USER")){
									model.setViewName("customerhome");
								}
								else if(role.equals("GOVERNMENT")){
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
}