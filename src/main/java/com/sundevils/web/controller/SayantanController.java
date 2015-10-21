package com.sundevils.web.controller;


import handlers.externaluserHandlers.openAccountHandler;
import handlers.governmemntHandlers.RequestPIIHandler;
import handlers.systemmanagerHandlers.authorizeExtUserHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class SayantanController 
{


	//openAccountForm
	@RequestMapping(value="/openAccountForm",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView registerRecepient(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{

		ModelAndView model = null;
		try
		{
			model = new ModelAndView();
			String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			String regex1= "^\\+?[1]\\d{10,10}$";
			String regex2="^[0-9]{5}$";
			String regex3= "^B[A-Z]{2}-[0-9]{3}$";
			String usertype= "";
			String accounttype="";
			String firstname="";
			String lastname="";
			String zip="";
			String passportnumber="";
			String ssn="";
			String email="";
			String phonenumber="";
			String businesslicence="";
			String checkagreement="";
			if(request.getParameter("submit")!=null)
			{
				usertype=request.getParameter("usertype");
				accounttype=request.getParameter("accounttype");
				firstname=request.getParameter("firstname");
				lastname=request.getParameter("lastname");
				lastname=request.getParameter("gender");
				zip=request.getParameter("zip");
				passportnumber=request.getParameter("passportnumber");
				ssn=request.getParameter("ssn");
				email=request.getParameter("email");
				phonenumber=request.getParameter("phonenumber");
				businesslicence=request.getParameter("businesslicence");	
				checkagreement=request.getParameter("checkagreement");

				//store the session of SSN
				session.setAttribute("SSN_Value", ssn);
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(ssn);

				Pattern pattern1 = Pattern.compile(regex1);
				Matcher matcher1 = pattern1.matcher(phonenumber);

				Pattern pattern2 = Pattern.compile(regex2);
				Matcher matcher2 = pattern2.matcher(zip);


				Pattern pattern3 = Pattern.compile(regex3);
				Matcher matcher3 = pattern3.matcher(businesslicence);
				//Check for validation

				if (!matcher.matches())
				{
					model.addObject("emptyFields", "SSN should be of format XXX-XXX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
					model.setViewName("Account.Opening.Form");
				}
				else if (!matcher1.matches())
				{
					model.addObject("emptyFields", "The mobile number should be of format +1XXXXXXXXXX");
					model.setViewName("Account.Opening.Form");
				}
				else if (!matcher2.matches())
				{
					model.addObject("emptyFields", "The ZIP must have 5 digts. No other characters allowed.");
					model.setViewName("Account.Opening.Form");
				}
				else if(usertype.equals("") || accounttype.equals("") ||  firstname.equals("") || lastname.equals("") ||  passportnumber.equals("") ||email.equals("") || ssn.equals("")|| phonenumber.equals("")||checkagreement.equals(""))
				{
					model.addObject("emptyFields", "First Name, Last Name, Passport Number, Email, SSN, Mobile Number, and agreement are mandatory fields");
					model.setViewName("Account.Opening.Form");
				}
				else if (usertype.equals("Merchant/Organization") && !matcher3.matches())
				{
					model.addObject("emptyFields", "Business Licence is mandatory for the Merchant/Organization. Business Licence should be 7 characters, should start with letter B, first three characters must be in alphabet, fourth character must be dash (-),fifth to seventh character must be numbers");
					model.setViewName("Account.Opening.Form");
				}
				else 
				{
					openAccountHandler handler = new openAccountHandler();
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					if(rs.next())
					{
						String temp_ssn = rs.getString("ssn");
						String temp_accounttype = rs.getString("accounttype");
						if(temp_ssn.equals(ssn) && temp_accounttype.equals(accounttype))
						{
							model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type already exsists");
							model.setViewName("Account.Opening.Form");
						}
					}
					else {
						handler.openAccount(request.getParameter("usertype"),request.getParameter("accounttype"),request.getParameter("prefix"),request.getParameter("firstname"),request.getParameter("middlename"),request.getParameter("lastname"),request.getParameter("gender"),request.getParameter("address"),request.getParameter("state"),request.getParameter("zip"),request.getParameter("passportnumber"),request.getParameter("ssn"),request.getParameter("email"),request.getParameter("phonenumber"),request.getParameter("dateofbirth"),request.getParameter("documents"),request.getParameter("businesslicence"),"Applied");
						model.addObject("Successful", "Account Opening Application has been submitted sucessfully");
						model.setViewName("uploadfile");
					}
				}
			}
			else
			{
				model.addObject("Successful","");
				model.setViewName("Account.Opening.Form");
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return model;
	}

	// Government Agency Controller
	@RequestMapping(value="/requestPII",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView requestPII(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{
		ModelAndView model = null;
		try
		{
			model = new ModelAndView();	
			String requesttype="";
			String requestdetails="";
			String authorizeto="";		

			if(request.getParameter("submit")!=null)
			{

				requesttype=request.getParameter("requesttype");
				requestdetails=request.getParameter("requestdetails");
				authorizeto=request.getParameter("authorizeto");



				if(requesttype.equals("") || requestdetails.equals("") ||  authorizeto.equals(""))
				{
					model.addObject("emptyFields", "All fields are mandatory");
					model.setViewName("Request.PII");
				}
				else 
				{
					RequestPIIHandler handler = new RequestPIIHandler();
					String name="sayantan";
					ResultSet rs =  handler.getExsistingPIIRequest(name,requesttype,requestdetails);
					if(rs.next())
					{

						String temp_requesttype = rs.getString("requesttype");
						String temp_requestdetails = rs.getString("requestdetails");
						if(name.equals("sayantan") && temp_requesttype.equals(requesttype) && temp_requestdetails.equals(requestdetails))
						{
							model.addObject("ExsistingUser", " The request type and request details already exsists");
							model.setViewName("Request.PII");
						}



					}
					else 
					{

						handler.requestPII("sayantan",request.getParameter("requesttype"),request.getParameter("requestdetails"),request.getParameter("authorizeto"));
						model.addObject("Successful", "Request for PII has been submitted sucessfully");
						model.setViewName("Request.PII");


					}

				}
			}
			else
			{

				model.addObject("Successful","");
				model.setViewName("Request.PII");
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return model;
	}

	//Authorize External User Account by System Manager
	//openAccountForm
	@RequestMapping(value="/authorizeExternalUser",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView authorizeExternalUser(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{

		ModelAndView model = null;
		try
		{
			model = new ModelAndView();
			String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			String ssn="";
			String accounttype="";
			if(request.getParameter("Approve")!=null)
			{

				accounttype=request.getParameter("accounttype");

				ssn=request.getParameter("ssn");
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(ssn);
				//Check for validation
				if (!matcher.matches())
				{
					model.addObject("emptyFields", "SSN should be of format XXX-XXX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
					model.setViewName("System.Manager.Add.External.User");
				}
				else if(accounttype.equals("") || ssn.equals(""))
				{
					model.addObject("emptyFields", "Account type and SSN are mandatory fields");
					model.setViewName("System.Manager.Add.External.User");
				}
				else 
				{
					authorizeExtUserHandler handler = new authorizeExtUserHandler();
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					ResultSet rs1 =  handler.getExsistingApprovedAccount(ssn,accounttype);
					if(!rs.next())
					{
						model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exists");
						model.setViewName("System.Manager.Add.External.User");
					}
					else if (rs1.next())
					{
						model.addObject("ExsistingUser", " The application has been already approved");
						model.setViewName("System.Manager.Add.External.User");
					}
					else {
						handler.approveUser(request.getParameter("ssn"),request.getParameter("accounttype"));
						model.addObject("Successful", "Application has been appoved");
						model.setViewName("System.Manager.Add.External.User");
					}

				}
			}
			else if (request.getParameter("Reject")!=null)
			{
				accounttype=request.getParameter("accounttype");
				ssn=request.getParameter("ssn");
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(ssn);
				//Check for validation
				if (!matcher.matches())
				{
					model.addObject("emptyFields", "SSN should be of format XXX-XXX-XXXX.\n The first three digits called the area number. \nThe area number cannot be 000, 666, or between 900 and 999.Digits four and five are called the group number and range from 01 to 99.\nThe last four digits are serial numbers from 0001 to 9999.");
					model.setViewName("System.Manager.Add.External.User");
				}
				else if(accounttype.equals("") || ssn.equals(""))
				{


					model.addObject("emptyFields", "Account type and SSN are mandatory fields");
					model.setViewName("System.Manager.Add.External.User");
				}
				else 
				{
					authorizeExtUserHandler handler = new authorizeExtUserHandler();
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					if(!rs.next())
					{
						model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exsists");
						model.setViewName("System.Manager.Add.External.User");




					}
					else 
					{
						handler.rejectUser(request.getParameter("ssn"),request.getParameter("accounttype"));
						model.addObject("Successful", "Application has been rejected");
						model.setViewName("System.Manager.Add.External.User");
					}

				}
			}
			else
			{
				model.addObject("Successful","");
				model.setViewName("System.Manager.Add.External.User");
			}

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return model;
	}

}