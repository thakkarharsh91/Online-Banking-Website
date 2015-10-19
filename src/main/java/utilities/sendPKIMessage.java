package utilities;

import java.util.LinkedHashMap;
import java.util.Random;

import javax.ws.rs.core.MediaType;

import com.plivo.helper.api.client.RestAPI;
import com.plivo.helper.exception.PlivoException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class sendPKIMessage 
{
	static int random = (new Random()).nextInt(900000) + 100000;
	static String messageText = "Your one time password is " + random + ". Please enter this password to continue.";
	

	public void sendMessage(String otp,String phone) 
	{
		try
		{
			String authId = "MAZDRMMDCXZJDHMGFKNZ";
			String authToken = "YThhNDg0ZDVmYjI3NGVhYWVmMzlhYWQ1OWI4Yzgz";
			RestAPI api = new RestAPI(authId, authToken, "v1");
			LinkedHashMap<String, String> parameters = new LinkedHashMap<String, String>();
			parameters.put("src", "+15202767815"); 
			parameters.put("dst", phone); 
			parameters.put("text", "Your otp is:  "+otp);
			try 
			{
				api.sendMessage(parameters);
			} 
			catch (PlivoException e1) 
			{
				e1.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public ClientResponse sendEmail(String privatekey,String email) 
	{
		Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api","key-6ccbcefd92ef23373e638edb9fbcb84b"));
		WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox49500e12fe5b4f679478baf006256263.mailgun.org" + "/messages");
		MultivaluedMapImpl formData = new MultivaluedMapImpl();
		formData.add("from", "Sun Devils Bank <mailgun@sandbox49500e12fe5b4f679478baf006256263.mailgun.org>");
		formData.add("to", email);
		formData.add("subject", "EncodedPrivateKey");
		formData.add("text", "Your encoded privatekey:   "+privatekey);
		return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class, formData);
	}

}

