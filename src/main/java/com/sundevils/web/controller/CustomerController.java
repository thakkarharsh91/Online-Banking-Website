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
import com.user.info.Transactions.TransactionRequestDetails;

@Controller
public class CustomerController {
	long startTime = 0;
	@RequestMapping(value = "/login/**/debitAndCredit", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView creditAndDebit (HttpServletRequest request,HttpSession session){
		ModelAndView model = new ModelAndView();
		String userName="";
		userName = (String)session.getAttribute("USERNAME");
		getAccountNumbers(model,userName);

		model.setViewName("creditAndDebit");
		return model;
	}

	@RequestMapping(value = "/login/**/viewBal", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView viewBalance(HttpServletRequest request,HttpSession session){
		ModelAndView model = new ModelAndView();
		LoginHandler handler = new LoginHandler(); 
		String userName="";
		userName = (String)session.getAttribute("USERNAME");
		ResultSet rs = handler.requestBalance(userName);
		List<AccountDetails> acntdetails=new ArrayList<AccountDetails>();

		try {
			while(rs.next()){
				AccountDetails details=new AccountDetails();
				details.setAccountNumber(rs.getString("accountnumber"));
				details.setAccountType(rs.getString("accounttype"));
				details.setBalance(rs.getDouble("balance"));
				acntdetails.add(details);
			}
			model.addObject("accountDetails",acntdetails);

		} catch (SQLException e) {
			model.addObject("accountDetails","");
			e.printStackTrace();
		}

		model.setViewName("viewBalance");
		return model;

	}

	@RequestMapping(value = "/login/**/creditAndDebitFull**", method = {RequestMethod.POST, RequestMethod.GET}) 
	public ModelAndView editCreditAndDebit(HttpServletRequest request,HttpSession session){

		ModelAndView model=null;
		model=new ModelAndView();
		String userName="";
		userName = (String)session.getAttribute("USERNAME");
		if(request.getParameter("submit")!=null){
			String option=request.getParameter("transaction");
			String accountNum=request.getParameter("transactions");
			String amount=request.getParameter("amount");
			try{
				double am=Double.parseDouble(amount); 
				LoginHandler handler = new LoginHandler(); 
				ResultSet rs = handler.requestBalance(userName);
				double balance=0;
				try {
					while(rs.next()){
						if(rs.getString("accountNumber").equals(accountNum)){
							balance=rs.getDouble("balance");
						}
					}
					ResultSet rs1=handler.requestPendingTransaction(userName);
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
						getAccountNumbers(model,userName);
						model.setViewName("creditAndDebit");
					}
					else{
						int random = (new Random()).nextInt(900000) + 100000;
						Date date=new Date();
						boolean flag;
						if(option.equalsIgnoreCase("debit")){
							flag=handler.insertTransactionDetails(userName,random,amount,accountNum,"",date.toString(),option,"PENDING");
						}
						else{
							flag=handler.insertTransactionDetails(userName,random,amount,"",accountNum,date.toString(),option,"PENDING");
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
			catch(NumberFormatException nfe){
				model.addObject("emptyFields", "Amount Field has invalid input");
				getAccountNumbers(model,userName);
				model.setViewName("creditAndDebit");
			}
		}
		return model;

	}

	private void getAccountNumbers(ModelAndView model, String userName) {
		LoginHandler handler=new LoginHandler();
		ResultSet rs=handler.requestBalance(userName);
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

	@RequestMapping(value = "/login/**/editPersonalInfo", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editPersonalInfo (HttpServletRequest request,HttpServletResponse response,HttpSession session){
		ModelAndView model = new ModelAndView();
		String userName="";
		userName = (String)session.getAttribute("USERNAME");
		model.setViewName("editPII");
		return model;
	}

	@RequestMapping(value = {"/login/**/editPII**"}, method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView editPII(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException {
		ModelAndView model=new ModelAndView();
		LoginHandler handler = new LoginHandler();
		String userName = (String)session.getAttribute("USERNAME");
		if(request.getParameter("submit")!=null){
			String changeColumn=request.getParameter("PII");
			String currentInfo=request.getParameter("curInfo");
			String newInfo=request.getParameter("newInfo");
			String confirmNewInfo=request.getParameter("cnfrmNewInfo");
			String otp=request.getParameter("otpCode");
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

					handler.personalInfoChange(userName,random,changeColumn,currentInfo,newInfo);
					model.setViewName("customerhome");
				}
				else{
					model.addObject("phoneNum", "Valid phone number should be numeric, 10 digit and no special charaecters");
					model.setViewName("editPII");
				}
			}
			else{
				handler.personalInfoChange(userName,random,changeColumn,currentInfo,newInfo);
				model.setViewName("customerhome");
			}
		}
		else if(request.getParameter("otpButton")!=null){
			startTime = System.currentTimeMillis();
			OtpUtility otp = new OtpUtility();
			String email = null;
			ResultSet rs = handler.getEmail(userName);
			while(rs.next()){
				email=rs.getString("email");
			}
			otp.sendOtp(request,email);
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
	
	@RequestMapping(value = "**/downloadStatement", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView downloadStatement(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, SQLException{
		LoginHandler handler = new LoginHandler(); 
		String userName = (String)session.getAttribute("USERNAME");
		ResultSet rs = handler.requestTrasactionDetails(userName);
		List<TransactionRequestDetails> transactionDetails=new ArrayList<TransactionRequestDetails>();
		while(rs.next()){
			TransactionRequestDetails details=new TransactionRequestDetails();
			details.setTransactionID(rs.getString("transactionid"));
			details.setTransactionAmount(rs.getString("transactionamount"));
			details.setSourceAccount(rs.getString("sourceaccountnumber"));
			details.setDestAccount(rs.getString("destinationaccountnumber"));
			details.setDateandTime(rs.getString("dateandtime"));
			details.setTransferType(rs.getString("transfertype"));
			details.setStatus(rs.getString("status"));
			transactionDetails.add(details);
		}
		
		return new ModelAndView("WritePDF", "transactionDetails", transactionDetails);
		
	}
	
	@RequestMapping(value = "**/home", method = {RequestMethod.POST, RequestMethod.GET})
	public ModelAndView homePage(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException{
		ModelAndView model=new ModelAndView();
		String role=(String) request.getSession().getAttribute("Role");
		String userName=(String) request.getSession().getAttribute("USERNAME");
		LoginHandler handler=new LoginHandler();
		ResultSet rs = handler.getEmail(userName);
		while(rs.next()){
			role=rs.getString("usertype");
		}
		if(role.equalsIgnoreCase("USER")){
			model.setViewName("customerhome");
		}
		else if(role.equalsIgnoreCase("MERCHANT")){
			model.setViewName("merchanthome");
		}
		return model;
	}
}
