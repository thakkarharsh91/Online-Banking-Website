package utilities;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class SendEmail {


	public ClientResponse sendEmailInvitation(String tmpusername, String tmppwd, String privatekey) {
		
				
		String messageText = "Your temporary username is " + tmpusername + ". Your temporary password is" + tmppwd + ". Your private key for the future transaction is" + privatekey +". Visit to Sun Devils Bank to change your username and password.";
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", "sguha3@asu.edu");
		formData.add("subject", "Welcome to SunDevil Bank");
		formData.add("text", messageText);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}
}
