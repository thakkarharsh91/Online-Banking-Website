package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class transactionViewRequestHandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public ResultSet getRequestHandler() {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (ResultSet)sql.getRequestInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
}
