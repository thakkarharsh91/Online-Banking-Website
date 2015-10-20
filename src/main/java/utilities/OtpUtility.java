package utilities;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class OtpUtility {

	public void sendOtp(HttpServletRequest request,String email){

		sendEmail(request,email);
	}

	private static ClientResponse sendEmail(HttpServletRequest request,String email) {
		int random = (new Random()).nextInt(900000) + 100000;
		HttpSession session = request.getSession(true);
		session.setAttribute("OTP", Integer.toString(random));
		String messageText = "Your one time password is " + random + ". Please enter this password to continue.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "One Time Password");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}

	public void sendUserName(HttpServletRequest request,String userName, String emailAddress){

		sendUserNameInEmail(request,userName,emailAddress);
	}
	
	private static ClientResponse sendUserNameInEmail(HttpServletRequest request,String userName, String emailAddress) {
		String messageText = "Your username is " + userName + ". Please login with this username to continue.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", "thakkarharsh90@gmail.com");
		formData.add("subject", "One Time Password");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
				post(ClientResponse.class, formData);
	}

}
