package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class updateAllowHandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public void requestUpdateHandler(String toUserName,String fromUserName,String type) {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			sql.updateAllowDataBase(toUserName,fromUserName,type);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
