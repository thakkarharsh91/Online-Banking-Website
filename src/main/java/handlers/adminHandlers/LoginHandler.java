package handlers.adminHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import databseHandler.MySQLAccess;

public class LoginHandler {

	static MySQLAccess sql;

	static { 
		sql = new MySQLAccess();
	}

	public ResultSet requestLoginHandler(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readLoginDataBase(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet requestAccountHandler(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readAccount(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
	public ResultSet requestAdminHandler(String rolename) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.readAdmin(rolename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public void updateLoggedInFlag(String userName,int status) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			sql.updateFlag(userName,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateLockedFlag(String userName,int status) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			sql.updateLockFlag(userName,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet requestBalance(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.getBalance(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet requestPendingTransaction(String userName) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.getPendingTransactions(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean insertTransactionDetails(String userName, int random,
			String amount, String accountNum, String accountNum2, String date,
			String transactionType, String status) {
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.insertTransactions(userName,random,amount,accountNum,accountNum2,date,transactionType,status);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean personalInfoChange(String userName, int random, String changeColumn,
			String currentInfo, String newInfo) {
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return sql.insertRequestChange(userName,random,currentInfo,newInfo,changeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void insertUnlockRequests(String userName, String requesttype,
			String requestfrom, String requestto,String modify,String approvalStatus,String oldValue,String newValue) {
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			sql.insertUnlockRequest(userName,requesttype,requestfrom,requestto,modify,approvalStatus,oldValue,newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ResultSet getUsername(String emailAddress) {

		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.getUserName(emailAddress);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public ResultSet getEmail(String userName) {
		try {
			sql.getConnection();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}


		try {
			return (ResultSet)sql.getEmail(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
	
}
