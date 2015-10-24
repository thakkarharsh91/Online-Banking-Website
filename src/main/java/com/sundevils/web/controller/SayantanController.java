package com.sundevils.web.controller;


import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utilities.SendEmail;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.plivo.helper.api.response.account.Account;

import handlers.adminHandlers.ViewUsersHandler;
import handlers.externaluserHandlers.openAccountHandler;
import handlers.governmemntHandlers.RequestPIIHandler;
import handlers.systemmanagerHandlers.authorizeExtUserHandler;
import handlers.systemmanagerHandlers.reviewExtUserHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;




@Controller
public class SayantanController 
{


	//openAccountForm
	@RequestMapping(value="/openAccountForm",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView registerRecepient(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws SQLException
	{

		ModelAndView model = null;
		System.out.println("here in POST");
		try
		{
			model = new ModelAndView();
			String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			String regex1= "^\\+?[1]\\d{10,10}$";
			String regex2="^[0-9]{5}$";
			String regex3= "^B[A-Z]{2}-[0-9]{3}$";
			String usertype= "";
			String accounttype="";
			String prefix="";
			String firstname="";
			String middlename="";
			String lastname="";
			String gender="";
			String address="";
			String state="";
			String zip="";
			String passportnumber="";
			String ssn="";
			String email="";
			String phonenumber="";
			String dateofbirth="";
			String documents="";
			String businesslicence="";
			String status="";
			String checkagreement="";
			System.out.println("here in try");
			if(request.getParameter("submit")!=null)
			{
				System.out.println("Inside If Statement");
				usertype=request.getParameter("usertype");
				accounttype=request.getParameter("accounttype");
				prefix=request.getParameter("prefix");
				firstname=request.getParameter("firstname");
				middlename=request.getParameter("middlename");
				lastname=request.getParameter("lastname");
				gender=request.getParameter("gender");
				address=request.getParameter("address");
				state=request.getParameter("state");
				zip=request.getParameter("zip");
				passportnumber=request.getParameter("passportnumber");
				ssn=request.getParameter("ssn");
				email=request.getParameter("email");
				phonenumber=request.getParameter("phonenumber");
				dateofbirth=request.getParameter("dateofbirth");
				documents=request.getParameter("documents");
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
					System.out.println(ssn);
					System.out.println(accounttype);
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					if(rs.next())
					{

						String temp_ssn = rs.getString("ssn");
						String temp_accounttype = rs.getString("accounttype");
						System.out.println(temp_ssn);
						System.out.println(temp_accounttype);
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
						SendEmail sendemail=new SendEmail();
						sendemail.sendEmailApplication(firstname, lastname, accounttype, email);

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
		System.out.println("here in POST");
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
					System.out.println(requesttype);
					System.out.println(requestdetails);
					String name="sayantan";
					ResultSet rs =  handler.getExsistingPIIRequest(name,requesttype,requestdetails);
					if(rs.next())
					{

						String temp_requesttype = rs.getString("requesttype");
						String temp_requestdetails = rs.getString("requestdetails");
						System.out.println(temp_requesttype);
						System.out.println(temp_requestdetails);
						if(name.equals("sayantan") && temp_requesttype.equals(requesttype) && temp_requestdetails.equals(requestdetails))
						{
							model.addObject("ExsistingUser", " The request type and request details already exsists");
							model.setViewName("Request.PII");
						}



					}
					else 
					{

						System.out.println("Inside Request PII");
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
		System.out.println("here in POST");
		try
		{
			model = new ModelAndView();
			String regex = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$";
			String ssn="";
			String accounttype="";
			String email="";
			String firstname="";
			String lastname="";
			System.out.println("here in try");
			if(request.getParameter("Approve")!=null)
			{
				System.out.println("Inside Approve Statement");

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
					System.out.println("SSN:"+ssn);
					System.out.println("Account Type:"+accounttype);
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					ResultSet rs1 =  handler.getExsistingApprovedAccount(ssn,accounttype);
					if(!rs.next())
					{

						System.out.println("Inside RS next");
						model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exists");
						model.setViewName("System.Manager.Add.External.User");



					}
					else if (rs1.next())
					{

						model.addObject("ExsistingUser", " The application has been already approved");
						model.setViewName("System.Manager.Add.External.User");
					}
					else {

						System.out.println("Else RS Next");
						handler.approveUser(request.getParameter("ssn"),request.getParameter("accounttype"));
						model.addObject("Successful", "Application has been appoved");
						model.setViewName("System.Manager.Add.External.User");


					}

				}
			}
			else if (request.getParameter("Reject")!=null)
			{
				System.out.println("Inside Reject Statement");

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
					System.out.println("SSN: "+ssn);
					System.out.println("Account type: "+accounttype);
					ResultSet rs =  handler.getExsistingAccount(ssn,accounttype);
					if(!rs.next())
					{
						System.out.println("Inside RS Next");
						model.addObject("ExsistingUser", " Recepient with entered SSN and Account Type does not exsists");
						model.setViewName("System.Manager.Add.External.User");




					}
					else 
					{

						System.out.println("Else RS Next");
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

	//Sayantan: File download controller
	@RequestMapping(value = "/ReviewAccountAppplication", method = RequestMethod.GET)
	public ModelAndView printWelcome(ModelMap model, String ssn, String accounttype) {
		ssn="123-456-9876";
		accounttype="Saving Account";
		ModelAndView model1 = new ModelAndView();
		reviewExtUserHandler handler = new reviewExtUserHandler(); 
		model1.addObject("users", handler.reviewExtUser(ssn,accounttype));
		model1.addObject("title", "All users in the database");
		model1.addObject("message", "This is protected page!");
		model1.setViewName("System.Manager.Account.Review");

			
		
		
		
		
		
		ssn="118-45-1158";
		String directorypath="C:\\AccountOpeningDocuments\\"+ssn;
		File folder = new File(directorypath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				//System.out.println("File " + listOfFiles[i].getName());
			

			model.addAttribute("file1", listOfFiles[0].getName());
			
			}
		}
		//return "System.Manager.Account.Review";
		
		return model1;
	}

	@RequestMapping(value = "/DownloadDocuments", method = RequestMethod.GET)
	public @ResponseBody void downloadFiles(HttpServletRequest request,
			HttpServletResponse response, String ssn) {
		ssn="118-45-1158";
			

		//Get file names
		String directorypath="C:\\AccountOpeningDocuments\\"+ssn;
		File folder = new File(directorypath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) 
		{
			if (listOfFiles[i].isFile()) {
				//System.out.println("File " + listOfFiles[i].getName());
			}
			String filepath= directorypath +"\\"+ listOfFiles[i].getName();
			
			ServletContext context = request.getServletContext();
			File downloadFile = new File(filepath);
			FileInputStream inputStream = null;
			OutputStream outStream = null;
			
		

			try {
			inputStream = new FileInputStream(downloadFile);

			response.setContentLength((int) downloadFile.length());
			response.setContentType(context.getMimeType(filepath));			

			// response header
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"",downloadFile.getName());
			response.setHeader(headerKey, headerValue);

			// Write response
			outStream = response.getOutputStream();
			IOUtils.copy(inputStream, outStream);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
				if (null != inputStream)
					outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		}
	}
}