package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class RequestAuthorize {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public ResultSet getRequestHandler(String User) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (ResultSet)sql.authRequest(User);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
public void updateRequestStatus(String rStatus, String[] rID) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			sql.updateStatus(rStatus, rID);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

public ResultSet checkRequestStatus(String rID) {
	
	try {
		sql.getConnection();
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	
	try {
		return (ResultSet)sql.checkStatus(rID);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
}
