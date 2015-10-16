package com.sundevils.web.controller;

import handlers.adminHandlers.LoginHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import utilities.CaptchaUtility;
import utilities.OtpUtility;

import com.user.info.AccountDetails;

@Controller
public class CustomerController {
	long startTime = 0;
	@RequestMapping(value = "/login/debitAndCredit", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView creditAndDebit (HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		getAccountNumbers(model);
		
		model.setViewName("creditAndDebit");
		return model;
	}
	
	@RequestMapping(value = "/login/viewBal", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView viewBalance(HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		LoginHandler handler = new LoginHandler(); 
		ResultSet rs = handler.requestBalance("akarsh");
		List<AccountDetails> acntdetails=new ArrayList<AccountDetails>();
		
		try {
			while(rs.next()){
				AccountDetails details=new AccountDetails();
				details.setAccountNumber(rs.getString("accountnumber"));
				details.setAccountType(rs.getString("accounttype"));
				details.setBalance(rs.getDouble("balance"));
				System.out.println(details.getAccountNumber());
				acntdetails.add(details);
			}
		model.addObject("accountDetails",acntdetails);
		
		} catch (SQLException e) {
			model.addObject("accountDetails","");
			e.printStackTrace();
		}
		
		System.out.println("Printing Values");
		for(AccountDetails d:acntdetails){
			System.out.println(d.getAccountNumber());
			System.out.println(d.getAccountType());
			System.out.println(d.getBalance());
		}
		
		model.setViewName("viewBalance");
		return model;
		
	}
	
	@RequestMapping(value = "/login/**/creditAndDebitFull**", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView editCreditAndDebit(HttpServletRequest request){
		
		ModelAndView model=null;
		model=new ModelAndView();
		//System.out.println("Hiiiiiiiiiiiiiiii");
		if(request.getParameter("submit")!=null){
			String option=request.getParameter("transaction");
			String accountNum=request.getParameter("transactions");
			String amount=request.getParameter("amount");
			
			if(amount!=null){
				LoginHandler handler = new LoginHandler(); 
				ResultSet rs = handler.requestBalance("akarsh");
				double balance=0;
				try {
					while(rs.next()){
						if(rs.getString("accountNumber").equals(accountNum)){
							balance=rs.getDouble("balance");
						}
					}
					ResultSet rs1=handler.requestPendingTransaction("akarsh");
					double tempBalance=0;
					while(rs1.next()){
						if((rs.getString("transfertype").equalsIgnoreCase("D") ||rs.getString("transfertype").equalsIgnoreCase("T"))){
							tempBalance=tempBalance+rs1.getDouble("transactionamount");
						}
						else if(rs.getString("transfertype").equalsIgnoreCase("C")){
							tempBalance=tempBalance-rs1.getDouble("transactionamount");
						}
					}
					double finalBalance=balance-tempBalance;
					if(option.equalsIgnoreCase("debit") && (Double.parseDouble(amount)>finalBalance)){
						model.addObject("insuffFunds", "The Account has insufficient funds");
						getAccountNumbers(model);
						model.setViewName("creditAndDebit");
					}
					else{
						int random = (new Random()).nextInt(900000) + 100000;
						Date date=new Date();
						boolean flag;
						if(option.equalsIgnoreCase("debit")){
							 flag=handler.insertTransactionDetails("akarsh",random,amount,accountNum,"",date.toString(),option,"No");
						}
						else{
							 flag=handler.insertTransactionDetails("akarsh",random,amount,"",accountNum,date.toString(),option,"No");
						}
						
						if(flag){
							model.addObject("sucess", "Transaction is Sucess");
							model.setViewName("customerhome");
						}
						else{
							model.addObject("failue", "Transaction is failed");
							model.setViewName("customerhome");
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else{
				model.addObject("emptyFields", "Amount Field is mandatory");
				getAccountNumbers(model);
				model.setViewName("creditAndDebit");
			}
		}
		return model;
	
	}

	private void getAccountNumbers(ModelAndView model) {
		LoginHandler handler=new LoginHandler();
		ResultSet rs=handler.requestBalance("akarsh");
		List<String> accountNumbers=new ArrayList<String>();
		try {
			while(rs.next()){
				accountNumbers.add(rs.getString("accountnumber"));
			}
			model.addObject("accountNumbers",accountNumbers);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/login/editPersonalInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editPersonalInfo (HttpServletRequest request){
		ModelAndView model = new ModelAndView();
		model.setViewName("editPII");
		return model;
	}
	
	@RequestMapping(value = {"/login/**/editPII**"}, method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editPII(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException {
		System.out.println("In Controller");
		ModelAndView model=new ModelAndView();
		if(request.getParameter("submit")!=null){
			String changeColumn=request.getParameter("PII");
			String currentInfo=request.getParameter("curInfo");
			String newInfo=request.getParameter("newInfo");
			String confirmNewInfo=request.getParameter("cnfrmNewInfo");
			String otp=request.getParameter("otpCode");
			LoginHandler handler = new LoginHandler();
			String otpString = (String)session.getAttribute("OTP");
			long diff = System.currentTimeMillis() - startTime;
			int random = (new Random()).nextInt(900000) + 100000;
			int minutes = (int) ((diff / (1000*60)) % 60);
			if(minutes > 3){
				otpString = "";
			}
			if(currentInfo.isEmpty() ||newInfo.isEmpty()||confirmNewInfo.isEmpty()||otp.isEmpty()){
				model.addObject("emptyFields", "All fields are mandatory");
				model.setViewName("editPII");
			}
			
			else if(!otp.equalsIgnoreCase(otpString))
			{
				model.addObject("wrongOtp", "Otp code does not match");
				model.setViewName("editPII");
			}
			else if(changeColumn.equalsIgnoreCase("Phone Number")){
				Pattern pattern = Pattern.compile("\\d{10}");
			     Matcher matcher1 = pattern.matcher(currentInfo);
			     Matcher matcher2 = pattern.matcher(newInfo);
			     
			     if(matcher1.matches() && matcher2.matches()){
			    	  
			    	 boolean flag = handler.personalInfoChange("akarsh",random,changeColumn,currentInfo,newInfo);
			    	 model.setViewName("customerhome");
			     }
			     else{
			    	 model.addObject("phoneNum", "Valid phone number should be numeric, 10 digit and no special charaecters");
					 model.setViewName("editPII");
			     }
			}
			else{
				boolean flag = handler.personalInfoChange("akarsh",random,changeColumn,currentInfo,newInfo);
				model.setViewName("customerhome");
			}
		}
		else if(request.getParameter("otpButton")!=null){
			startTime = System.currentTimeMillis();
			OtpUtility otp = new OtpUtility();
			otp.sendOtp(request);
			model.setViewName("editPII");
		}
		return model;
	
	}
	
	@RequestMapping(value = "/login/**/captcha" , method = RequestMethod.GET)
	public ModelAndView generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		ModelAndView model = new ModelAndView();
		CaptchaUtility captcha = new CaptchaUtility();
		captcha.generateCaptcha(request,response);
		model.setViewName("login");
		return model;
	}
}