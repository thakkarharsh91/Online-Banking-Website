package handlers.employeeHandlers;

import java.sql.SQLException;

import databseHandler.CheckSourceAccountAccess;

public class CheckSourceAccountNumberHandler {

	static CheckSourceAccountAccess sql;
	
	static { 
		sql = new CheckSourceAccountAccess();
	}
	
	public Object requestHandler(String username,String fromaccountnumber) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (Object)sql.readDataBaseAccount(username,fromaccountnumber);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	
	
}
