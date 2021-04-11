package com.sofi.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sofi.coupons.beans.User;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.enums.UserType;
import com.sofi.coupons.exceptions.ApplicationException;
import com.sofi.coupons.utils.ConnectionPool;

public class UsersDao {

	private ConnectionPool connections;

	public UsersDao() {
		connections = ConnectionPool.getInstance();
	}

	public long createUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			// User ID is defined as a primary key and auto incremented
			String sqlStatement = "insert into users (user_name, password, user_type, company_id, first_name, last_name) values (?,?,?,?,?,?);";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getUserType().name());
			statement.setString(5, user.getFirstName());
			statement.setString(6, user.getLastName());

			if(user.getCompanyId()==null) {
				statement.setObject(4,null);
			} else {
				statement.setLong(4,user.getCompanyId());
			}
			// Executing the update
			statement.executeUpdate();

			// If the user was created, returning the user ID
			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}
			throw new ApplicationException(ExceptionType.GENERAL_ERROR, "Failed to generate user ID");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to create new user: " + user.toString());

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public User getUser(long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from users where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the query
			resultSet = statement.executeQuery();

			//Creating and returning a user from the received data 
			if(resultSet.next()) {

				return resultSetUserCreation(resultSet);
			}else{
				return null;
			}

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get user by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public void updateUser(User user) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "update users set user_name = ?, password = ?, user_type = ?, company_id = ?, first_name = ?, last_name = ? where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getUserType().name());
			statement.setString(5, user.getFirstName());
			statement.setString(6, user.getLastName());
			statement.setLong(7, user.getId());


			if(user.getCompanyId()==null) 
				statement.setObject(4,null);
			else
				statement.setLong(4,user.getCompanyId());

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to update user: " + user);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public void deleteUser (long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			//"On delete CASCADE" was applied in MySQL, therefore no need to delete purchases prior to users;
			String sqlStatement = "delete from users where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to delete user by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public List<User> getAllUsers() throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from users;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Executing the query
			resultSet = statement.executeQuery();

			// Creating a list to store all the users
			List<User> users = new ArrayList<User>();

			//Creating users from the received data and adding to the ArrayList
			while(resultSet.next()) {
				users.add(resultSetUserCreation(resultSet));
			}
			return users;

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get all users");

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public boolean isUserNameExists(String userName) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from users where user_name = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, userName);

			// Executing the query
			resultSet = statement.executeQuery();

			//if user by this user_name was found return true, if not, return false
			if(resultSet.next()) {
				return true;
			}
			return false;

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR,  "Failed to check if user names exists: " + userName);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}   
	}


	public UserType login(String userName, String password) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select user_type from users where user_name = ? and password = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, userName);
			statement.setString(2, password);

			// Executing the query
			resultSet = statement.executeQuery();

			if(resultSet.next()) {
				return UserType.valueOf(resultSet.getString(1));
			}
			return null;

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to login");

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}   
	}

	///////////////////
	//Private methods//
	///////////////////

	//Private method for object creation from the result set
	private User resultSetUserCreation(ResultSet resultSet) throws SQLException {

		Long companyId;
		if(resultSet.getObject("company_id") == null) {
			companyId = null;
		}else {
			companyId = resultSet.getLong("company_id");
		}
		long id = resultSet.getLong("id");
		String userName = resultSet.getString("user_name");
		String password = resultSet.getString("password");
		UserType userType = UserType.valueOf(resultSet.getString("user_type"));
		String firstName = resultSet.getString("first_name");
		String lastName = resultSet.getString("last_name");
		User user = new User(id, userName, password, userType, companyId, firstName, lastName);
		return user;
	}
}
