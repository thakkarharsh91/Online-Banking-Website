package com.sundevils.web.controller;

import gunpreetPackage.BusinessLogic.CheckAuthentication;
import gunpreetPackage.BusinessLogic.SendEmail;
import gunpreetPackage.Dao.JdbcConnection_tbl_user_authentication;
import gunpreetPackage.Model.InternalUser;
import gunpreetPackage.Model.PIIShow;
import gunpreetPackage.Model.requestPII;
import gunpreetPackage.Model.userDetails;
import handlers.adminHandlers.LoginHandler;
import handlers.adminHandlers.PasswordChangeHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerClass {
	
	private static final Logger LOG = Logger.getLogger(ControllerClass.class);

	PIIShow show = new PIIShow();
	ArrayList<PIIShow> showList = new ArrayList<PIIShow>();

	@RequestMapping("/reset")
	public ModelAndView resetPasswordFunc(HttpSession session){
		ModelAndView modelandview = new ModelAndView("resetpassword");
		String userName = (String)session.getAttribute("USERNAME");
		modelandview.addObject("user", userName);
		return modelandview;
	}	

	@RequestMapping(value="/resetButton", method = RequestMethod.POST)
	public ModelAndView reset(
			@RequestParam (value="current") String presenttpassword,
			@RequestParam (value="new") String newpassword,
			@RequestParam (value="confirm") String confirmpassword,HttpSession session) throws ClassNotFoundException{

		String userName = (String)session.getAttribute("USERNAME");
		ModelAndView model = new ModelAndView();
		model.addObject("user", userName);
		CheckAuthentication auth= new CheckAuthentication();
		if(presenttpassword.equals("") || newpassword.equals("") || confirmpassword.equals("")){
			model.addObject("emptyFields", "All fields are mandatory");
			model.setViewName("resetpassword");
		}
		else{
			String result = auth.check(userName,presenttpassword, newpassword, confirmpassword);
			if(!result.equals("")){
				model.addObject("errormessage", result);
				model.setViewName("resetpassword");
			}
			else{
				PasswordChangeHandler handler = new PasswordChangeHandler();
				handler.requestHandler(userName);
				LoginHandler loginHandler;
				loginHandler = new LoginHandler();
				loginHandler.updateLoggedInFlag(userName,0);
				model.addObject("successpassword", "Your password has been successfully changed.  You will be automatically redirected to login page within few seconds.");
				model.setViewName("success");
			}
		}
		return model;
	}	

	@RequestMapping(value="/showInternal")
	public ModelAndView ShowInternalUsers(HttpSession session) 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		ResultSet resultset = null;
		JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
		ArrayList<InternalUser> list = new ArrayList<InternalUser>();
		try 
		{
			if(role == null)
			{
				ModelAndView model = new ModelAndView();
				model.setViewName("index");
				return model;
			}
			else if(role.equals("ADMIN"))
			{
				resultset = objConn.get_Internal_Users_Details();			
				while(resultset.next())
				{
					InternalUser user = new InternalUser();
					String name = new String(resultset.getBytes(1), "UTF-8");
					user.setUsername(name);
					String firstname = new String(resultset.getBytes(2), "UTF-8");
					user.setFirstname(firstname);
					String lastname = new String(resultset.getBytes(3), "UTF-8");
					user.setLastname(lastname);
					list.add(user);
				}
			}
			else
			{
				ModelAndView model = new ModelAndView();
				model.setViewName("index");
				return model;
			}			
		} 
		catch (Exception e) 
		{
			LOG.error("Error with viewing internal users: "+e.getMessage());
		}
		finally
		{
			try 
			{
				resultset.close();
			} 
			catch (SQLException e) 
			{
				// TODO Auto-generated catch block
				LOG.error("Error with closing resultset");
			}
		}
		
		ModelAndView modelandview = new ModelAndView();
		modelandview.addObject("list",list);
		modelandview.setViewName("deleteInternalUser");
		LOG.info("Generating the list of internal users to delete" + list );
		return modelandview;
	}

	@RequestMapping("/abc")
	public ModelAndView abc(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("piiRequests");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}
	
	@RequestMapping(value="/piirequest")
	public ModelAndView showRequest(HttpSession session) 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ResultSet resultset = null;
			ResultSet rs = null;
			ArrayList<requestPII> piilist = new ArrayList<requestPII>();
			ArrayList<userDetails> userlist = new ArrayList<userDetails>();
			showList = new ArrayList<PIIShow>();
			JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
			
			try 
			{	
				resultset = objConn.get_PII();			
				while(resultset.next())
				{
					requestPII requestObj = new requestPII();
					requestObj.setGovusername(resultset.getString(2));
					requestObj.setPid(resultset.getString(1));
					requestObj.setColumnname(resultset.getString(3));
					requestObj.setColumnvalue(resultset.getString(4));				
					piilist.add(requestObj);				
				}			
				for(int i=0; i<piilist.size(); i++)
				{
					String columnname = piilist.get(i).getColumnname();
					String columnvalue = piilist.get(i).getColumnvalue();				
					rs = objConn.get_UserDet(columnname, columnvalue);
					
					while(rs.next())
					{
						userDetails userObj = new userDetails();
						userObj.setUsername(rs.getString(1));
						userObj.setFirstname(rs.getString(2));
						userObj.setLastname(rs.getString(3));
						userObj.setEmail(rs.getString(4));
						userObj.setPhone(rs.getString(5));
						userObj.setSsn(rs.getString(6));
						userObj.setAddress(rs.getString(7));
						userObj.setAccountnumber(rs.getString(8));
						userlist.add(userObj);
					}			
				}			
			}
			catch (Exception e) 
			{
				LOG.error("Error with showing pii request: "+e.getMessage());
			}
			finally
			{
				try 
				{
					rs.close();
					resultset.close();
				} 
				catch (SQLException e) 
				{
					// TODO Auto-generated catch block
					LOG.error("Error with closing resultset");
				}				
			}
			ModelAndView modelandview = new ModelAndView();		
			for(int x =0; x<piilist.size();x++)
			{
				show.setPid(piilist.get(x).getPid());
				show.setUsername(userlist.get(x).getUsername());
				show.setFirstname(userlist.get(x).getFirstname());
				show.setLastname(userlist.get(x).getLastname());
				show.setEmail(userlist.get(x).getEmail());
				show.setPhonenumber(userlist.get(x).getPhone());
				show.setAddress(userlist.get(x).getAddress());
				show.setAccountnumber(userlist.get(x).getAccountnumber());
				showList.add(show);		
			}	
			modelandview.addObject("showList",showList);
			modelandview.setViewName("piiRequests");
			LOG.info("Generating the list of PII requests for admin " + showList );
			return modelandview;	
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
		
			
	}
	
	@RequestMapping(value="/delInternalUser", method = RequestMethod.POST)
	public ModelAndView delUsers(HttpServletRequest request,HttpSession session) 
	{
		
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("deleteInternalUser");
			ArrayList<String> list = new ArrayList<String>();
			try
			{
				String[] delList = request.getParameterValues("cblist");
				
				if(delList!=null)
				{
					for (int i = 0; i < delList.length; i++) 
					{
					    if (delList[i] != null)
					    {
					    	list.add(delList[i]);
					    }	    
					} 
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					objConn.delete_Internal_Users(list);
					modelandview = ShowInternalUsers(session);						
				}
				else
				{
					modelandview = ShowInternalUsers(session);
				}
				
			}
			catch(Exception e)
			{
				LOG.error("Error with deleting internal user: "+e.getMessage());
			}
			LOG.info("Generating view to delete internalusers");
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
		
	}

	@RequestMapping(value="/updatepiistatus", method = RequestMethod.POST)
	public ModelAndView updatePIIStatus(HttpServletRequest request,HttpSession session) 
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			String gemail ="";
			ArrayList<String> pidlist = new ArrayList<String>();
			ArrayList<String> emaillist = new ArrayList<String>();
			ModelAndView modelandview = new ModelAndView("piiRequests");
			try
			{
				String[] checkList = request.getParameterValues("checkList");
				String type = request.getParameter("Type");
				
				if(checkList!=null)
				{
					for (int i = 0; i < checkList.length; i++) 
					{
					    if (checkList[i] != null)
					    {
					    	pidlist.add(checkList[i]);
					    }	    
					} 
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					objConn.updatePIIStatus(pidlist);
					emaillist = objConn.getEmailList(pidlist);
					
					System.out.println(pidlist.size());
					System.out.println(emaillist.size());
					
					for(int j =0;j<pidlist.size();j++)
					{
						String id = pidlist.get(j);
						gemail = emaillist.get(j);
						
						for(int k=0; k<showList.size();k++)
						{
							if(id.equalsIgnoreCase(showList.get(k).getPid()))
							{
								SendEmail send = new SendEmail();
								if(type.equalsIgnoreCase("Approve"))
								{
									send.sendEmail(gemail,showList.get(k).getUsername(),showList.get(k).getAccountnumber(),
														 showList.get(k).getFirstname(),showList.get(k).getLastname(),
														 showList.get(k).getEmail(),showList.get(k).getPhonenumber(),showList.get(k).getAddress());
								}
								else
								{
									send.sendRejectEmail(gemail);
								}
								
								
							}
						}
					}
					System.out.println("Email sent");
					modelandview = showRequest(session);						
				}
				else
				{
					modelandview = showRequest(session);
				}		
				
			}
			catch(Exception e)
			{
				LOG.error("Error with updating pii status: "+e.getMessage());
			}
			LOG.info("Updating the PII status from PII_REQUESTED to COMPLETED and sending email to government agency: "+ gemail);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}
	
	@RequestMapping(value="/addg")
	public ModelAndView addGovernment(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("addGovernmentAgency");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}

	@RequestMapping(value="/addgovernmentagency", method = RequestMethod.POST)
	public ModelAndView addGovernmentAgency(
			@RequestParam (value="agencyname") String agencyname,
			@RequestParam (value="agencyid") String agencyid,
			@RequestParam (value="agencyemail") String agencyemail,HttpSession session) throws Exception	
	{
		
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ResultSet rs =null;
			ModelAndView modelandview = new ModelAndView("addGovernmentAgency");
			try 
			{
				if(agencyname.matches("/^[A-z]+$/") 
					&& agencyid.matches("[0-9]+") 
					&& agencyemail.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;"))
					{
						
						JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();	
						rs = objConn.checkgovernmentagency(agencyname, agencyid, agencyemail);
						if(rs.next())
						{
							System.out.println("Government data already present!");
						}
						else
						{
							objConn.addusername(agencyid);
							objConn.addgovernmentagency(agencyname, agencyid, agencyemail);									
						}
					}
				else
				{
					System.out.println("Please enter correct values!");
				}			
			}	
			catch(Exception e)
			{
				LOG.error("Error with adding government agency view: "+e.getMessage());
			}
			finally
			{
				rs.close();
			}
			LOG.info(" Added government agency with details: "+agencyname+","+agencyid+","+agencyemail);
			
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}

	@RequestMapping(value="/addi")
	public ModelAndView addInternal(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("addInternalUser");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}

	@RequestMapping(value="/addinternaluser", method = RequestMethod.POST)
	public ModelAndView addInternalUser(
			@RequestParam (value="firstname") String firstname,
			@RequestParam (value="middlename") String middlename,
			@RequestParam (value="lastname") String lastname,
			@RequestParam (value="ssn") String ssn,
			@RequestParam (value="email") String email,
			@RequestParam (value="phonenumber") String phonenumber,
			@RequestParam (value="dateofbirth") String dateofbirth,
			@RequestParam (value="address") String address,
			@RequestParam (value="state") String state,
			@RequestParam (value="username") String username,
			@RequestParam (value="designation") String usertype,
			@RequestParam (value="zip") String zip, HttpSession session) throws Exception{
		
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ResultSet resultset = null;
			ResultSet rs = null;
			try
			{
				if(firstname.matches("/^[A-z]+$/") && 
				   lastname.matches("/^[A-z]+$/") &&
				   middlename.matches("/^[A-z]+$/") &&
				   state.matches("/^[A-z]+$/") &&
				   ssn.matches("^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$") &&
				   email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;") &&
				   phonenumber.matches( "^\\+?[1]\\d{10,10}$") && 
				   username.matches("[0-9]+") && 
				   zip.matches("^[0-9]{5}(?:-[0-9]{4})?$"))
				{
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					resultset = objConn.checkusername(username);
					if(resultset.next())
					{
						System.out.println("Username already present");
					}
					else
					{
						objConn.addusername(username);
						rs = objConn.checkinternaluser(ssn);
						if(rs.next())
						{
							System.out.println("Internal User already present!");
						}
						else
						{
							objConn.update_internalUser(username,firstname, middlename, lastname, ssn, email, usertype, phonenumber, dateofbirth, address, state, zip);
							System.out.println("Internal user successfully added!");
						}		
					}	
				}
				else
				{
					System.out.println("Please enter correct values.");
				}
					
						
				
			}
			catch(Exception e)
			{
				LOG.error("Error with adding internal user: "+e.getMessage());
			}
			finally
			{
				resultset.close();
				rs.close();
			}
			ModelAndView modelandview = new ModelAndView("addInternalUser");
			LOG.info(" Added internal user with details: "+firstname+","+middlename+","+lastname+","+ssn+","+email+","+usertype+","+phonenumber+","+dateofbirth+","+address+","+state+","+zip);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}	
		
	}
	
	@RequestMapping(value="/modifyinternal")
	public ModelAndView modifyInternal(HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("modifyInternalAccount");		
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}

	@RequestMapping(value="/modifyinternaluser", method = RequestMethod.POST)
	public ModelAndView modifyInternalUser(
			@RequestParam (value="username") String username,
			@RequestParam (value="column") String column,
			@RequestParam (value="newinfo") String newinfo, HttpSession session)
	{
		String role = "";
		role = (String)session.getAttribute("Role");
		if(role == null)
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		else if(role.equals("ADMIN"))
		{
			ModelAndView modelandview = new ModelAndView("modifyInternalAccount");
			try 
			{
				if(username.matches("[0-9]+") && newinfo.matches("/^[A-z]+$/"))
				{
					JdbcConnection_tbl_user_authentication objConn = new JdbcConnection_tbl_user_authentication();
					objConn.modifyinternaluser(username, column,newinfo);			
				}
				else
				{
					System.out.println("Please enter correct info.");
				}
			} 
			catch (Exception e) 
			{
				// TODO Auto-generated catch block
				LOG.error("Error with modifying internal user: "+e.getMessage());			
			}
			LOG.info(" modifying internal user with details of username : "+username+", with column: "+column+", to: "+newinfo);
			return modelandview;
		}
		else
		{
			ModelAndView model = new ModelAndView();
			model.setViewName("index");
			return model;
		}
		
	}
	
}
