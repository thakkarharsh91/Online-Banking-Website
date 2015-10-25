package gunpreetPackage.BusinessLogic;

import org.apache.log4j.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SendEmail 
{
	private static final Logger LOG = Logger.getLogger(SendEmail.class);
	
	public void sendEmail(String govemail,String username, String accountnumber, String firstname, String lastname, 
			String email,String phonenumber, String address)
	{
		try {
			String messageText = "Your requested PII request. "
							   + "Username: " + username + " "
							   + "AccountNumber: " + accountnumber + " "
							   + "First Name: " + firstname + " "
							   + "Last Name: " + lastname + " "
							   + "Email: " + email + " "
							   + "Phonenumber: " + phonenumber + " "
							   + "Address: " + address + " ";
							  
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
			MultivaluedMapImpl formData = new MultivaluedMapImpl();
			formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
			formData.add("to", govemail);
			formData.add("subject", "PII Request Info:");
			formData.add("text", messageText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error with sending approve email to government agency: "+e.getMessage());
		}
	}
	
	public void sendRejectEmail(String govemail)
	{
		try {
			String messageText = "Your request has been declined! Sorry.";				  
			Client client = Client.create();
			client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
			MultivaluedMapImpl formData = new MultivaluedMapImpl();
			formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
			formData.add("to", govemail);
			formData.add("subject", "PII Request Info:");
			formData.add("text", messageText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOG.error("Error with sending reject email to government agency: "+e.getMessage());
		}
	}
}
