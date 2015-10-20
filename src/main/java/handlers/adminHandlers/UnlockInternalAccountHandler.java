package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.UnlockInternalAccountAccess;


public class UnlockInternalAccountHandler {

	static UnlockInternalAccountAccess sql;

	static { 
		sql = new UnlockInternalAccountAccess();
	}

	public Object requestHandler(String username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (Object)sql.UpdateDataBaseUserAuthentication(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Object readrequestHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (Object)sql.readDataBaseRequest();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public Object updaterequestHandler(String username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (Object)sql.UpdateDataBaseRequest(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}


}

