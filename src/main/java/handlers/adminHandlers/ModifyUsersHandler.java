package handlers.adminHandlers;

import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;

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
			return (Object)sql.readModifyUser(userName,parameterType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public Object requestManagers() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (Object)sql.readSystemManagers();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	public ResultSet requestCountHandler() {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.countRequestDatabase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;


	}
	
	public void updateCountHandler(int count,String Username) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.updatecountDatabase(count,Username);
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	public void deleteRequestHandler(String userName,String accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.deleteUserDetails(userName,accountnumber);
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
	public Object viewRequestHandler(String Manager) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.viewRequestDatabase( Manager);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Object getaccounttypeHandler(String username, String accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.readAccountType(username,accountnumber);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	public Object updateaccountrequest(String username,String manager,String Accountnumber) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
		 sql.insertAccountChangeRequest(username,manager,Accountnumber);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	

}



