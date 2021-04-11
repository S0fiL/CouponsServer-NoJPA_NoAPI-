package com.sofi.coupons.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;

public class ConnectionPool {

	private static ConnectionPool instance;
	private static ArrayList<Connection> connections = new ArrayList<Connection>();
	private static final int MAX_CONNECTIONS = 50;

	static {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/project1?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","mysqlpass");
			for (int i =0; i < MAX_CONNECTIONS; i++) {
				connections.add(con);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static ConnectionPool getInstance() {
		if(instance== null)
			instance = new ConnectionPool();
		return instance;
	}


	///////////////////////
	//Connections methods//
	///////////////////////


	public synchronized Connection getConnection() throws ApplicationException {

		while (connections.size()==0) {
			try {
				wait();
			} catch (InterruptedException e) {
				throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Wait to get connection interrupted");
			}
		}

		Connection con = connections.get(0);
		connections.remove(0);
		return con;

	}

	public synchronized void returnConnection(Connection connection) {

		connections.add(connection);
		notify();
	}

	//Close Prepared statement only
	public void closePreparedStatement(PreparedStatement statement) throws ApplicationException {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to close PreparedStatement");
		}
	}

	//Closing all the resources used and returning the connection
	public void closeResources(Connection connection, PreparedStatement statement) throws ApplicationException {
		closePreparedStatement(statement);
		returnConnection(connection);
	}

	public void closeResources(Connection connection, PreparedStatement statement, ResultSet resultSet) throws ApplicationException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to close ResultSet");
		}
		closeResources(connection, statement);
	}


	//Closing all the connections
	public synchronized void closeAllConnections() throws ApplicationException {

		while(connections.size()<MAX_CONNECTIONS) {

			try {
				wait();
			} catch (InterruptedException e) {
				throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Wait to return all connections interrupted");
			}
			for (Connection connection : connections) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to close connections");
				}
			}
		}
	}
}




