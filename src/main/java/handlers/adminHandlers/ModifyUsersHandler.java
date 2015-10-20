package handlers.adminHandlers;

import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class ModifyUsersHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public Object requestHandler(String userName,String parameterType) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (Object)sql.readUserDetails(userName,parameterType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public void deleteRequestHandler(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.deleteUserDetails(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void updateRequestHandler(String[] parameters) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updateUserDetails(parameters);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void requestModifyHandler(String user, String parameter,String parametertype,String usertype) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.insertintoRequestDatabase(user,parameter,parametertype,usertype);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void updateRequestHandler(String newvalue, String columnname, String user) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updateRequestDatabase(newvalue, columnname, user);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void declineRequestHandler(String newvalue, String columnname,String user) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.declineRequestDatabase(newvalue,columnname,user);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public Object viewRequestHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.viewRequestDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}



