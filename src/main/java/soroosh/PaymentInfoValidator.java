package soroosh;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import utilities.TimeUtility;

public class PaymentInfoValidator {

	public ArrayList<String> merchantPaymentRequestValidateData(String username, String payerName, String amount, 
			String bankAccount, String signature) throws SQLException, ClassNotFoundException {

		ArrayList<String> errors = new ArrayList<String>();
		SorooshDatabaseConnection database = new SorooshDatabaseConnection();

		if(payerName == null || amount == null || signature == null || bankAccount == null)
			errors.add("all fields are mandatory");

		else{
			if(!database.userExists(payerName))
				errors.add("Merchant does not exist");

			try{
				Double.parseDouble(amount);
			} catch(NumberFormatException e){
				errors.add("Invalid format for amount");
				return errors;
			}

			if(Double.parseDouble(amount) <= 0){
				errors.add("Invalid amount");
			}
			
			if(!database.accountValid(bankAccount, username)){
				errors.add("Source account is not valid");
				return errors;
			}
			
			if(!database.signatureValid(username, signature)){
				errors.add("Invalid private key - make sure the signature is inputted correctly");
			}
		}
		return errors;
	}

	public ArrayList<String> userPaymentValidateData(String merchantName, String accountNumber, String amount, 
			String oTPMethod, String oTPText, String signature, HttpSession session, String username) throws SQLException, ClassNotFoundException {

		ArrayList<String> errors = new ArrayList<String>();

		if((merchantName == null) || (accountNumber == null) || (amount == null) || (oTPText == null) 
				|| (signature == null)){
			errors.add("all fields are mandatory");
		}
		String otpEnteredtime=TimeUtility.generateDateMethod()+" "+TimeUtility.generateHoursMethod()+":"+TimeUtility.generateMinutesMethod()+":"+TimeUtility.generateSecondsMethod();
		//String otpGeneratedTime=request.getSession.getAttribute(otpGenerateTime)
		String modelTime="2015/10/24 00:00:00";
		/*
		 * long genSec=TimeUtility.getDifferenceinSeconds(modelTime,otpGenerateTime);
			long enterSec=TimeUtility.getDifferenceinSeconds(modelTime,otpEnterTime);
			if((enterSec-genSec)>180){
				otpString = "";
			}
		 */
		if(session.getAttribute("OTP") == null){
			errors.add("Please order OTP");
			return errors;
		}
		
		else{
			if(!((String)session.getAttribute("OTP")).equals(oTPText)){
				errors.add("Wrong OTP please try again");
			}

			SorooshDatabaseConnection database = new SorooshDatabaseConnection();
			if(!database.merchantExists(merchantName))
				errors.add("Merchant does not exist");

			if(!database.accountValid(accountNumber, username)){
				errors.add("Source account is not valid");
				return errors;
			}

			double doubleAmount = 0;
			try{
				doubleAmount = Double.parseDouble(amount);
			} catch(NumberFormatException e){
				errors.add("Invalid format for amount");
				return errors;
			}

			if(doubleAmount <= 0)
				errors.add("Invalid amount");

			if(!database.balanceAvailable(accountNumber, doubleAmount))
				errors.add("Not enough balance in the account chosen");

			if(!database.signatureValid(username, signature)){
				errors.add("Invalid private key - make sure the signature is inputted correctly");
			}
		}
		return errors;
	}

	public ArrayList<String> payerPaymentAcceptValidation(String accountNumber,  String username, 
			PaymentInfo payment, String signature)
					throws SQLException, ClassNotFoundException {

		ArrayList<String> errors = new ArrayList<String>();
		SorooshDatabaseConnection database = new SorooshDatabaseConnection();

		if((accountNumber == null) || (signature == null)){
			errors.add("All fields are mandatory");
			return errors;
		}
		
		if(!database.accountValid(accountNumber, username)){
			errors.add("Source account is not valid");
			return errors;
		}

		if(!database.balanceAvailable(accountNumber, payment.getAmount()))
			errors.add("Not enough balance in the account chosen");

		if(!database.signatureValid(username, signature)){
			errors.add("The private key is wrong - make sure you are using the right key");
		}

		return errors;
	}

	public ArrayList<String> payerPaymentAcceptValidation(String accountNumber,  String username, 
			PaymentInfo payment)
					throws SQLException, ClassNotFoundException {

		ArrayList<String> errors = new ArrayList<String>();
		SorooshDatabaseConnection database = new SorooshDatabaseConnection();

		if(accountNumber == null){
			errors.add("Source account is not valid");
			return errors;
		}
		
		if(!database.accountValid(accountNumber, username)){
			errors.add("Source account is not valid");
			return errors;
		}

		if(!database.balanceAvailable(accountNumber, payment.getAmount()))
			errors.add("Not enough balance in the account chosen");

		return errors;
	}
}
