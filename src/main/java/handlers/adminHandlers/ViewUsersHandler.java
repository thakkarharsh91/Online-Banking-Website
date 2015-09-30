package handlers.adminHandlers;

import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.servlet.ModelAndView;

import authentication.User;
import databseHandler.MySQLAccess;

public class ViewUsersHandler {

	static MySQLAccess sql;
	
	static { 
		sql = new MySQLAccess();
	}
	
	public Object requestHandler() {
		
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		try {
			return (Object)sql.readDataBase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	
	
}
