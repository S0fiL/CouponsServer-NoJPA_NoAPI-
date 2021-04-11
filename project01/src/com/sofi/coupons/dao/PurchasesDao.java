package com.sofi.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.sofi.coupons.beans.Purchase;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;
import com.sofi.coupons.utils.ConnectionPool;


public class PurchasesDao {

	private ConnectionPool connections;

	public PurchasesDao() {
		connections = ConnectionPool.getInstance();
	}

	public long createPurchase(Purchase purchase) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		PreparedStatement statement2 = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();


			// Creating the SQL queries
			// Purchase ID is defined as a primary key and auto incremented
			String sqlStatement = "insert into purchases (user_id, coupon_id, amount, timestamp) values(?,?,?,?);";
			//Decreasing coupons amount by the purchased coupons amount
			String sqlStatement2 = "update coupons set amount = amount-? where id = ?;";
			// Combining between the syntax and our connection
			try {
				//Deactivate auto commit to transfer two statement simultaneously
				connection.setAutoCommit(false);

				statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);
				statement2 = connection.prepareStatement(sqlStatement2);

				// Replacing the question marks in the statement above with the relevant data
				statement.setLong(1, purchase.getUserId());
				statement.setLong(2, purchase.getCouponId());
				statement.setInt(3, purchase.getAmount());
				statement.setTimestamp(4, purchase.getTimestamp());
				statement2.setInt(1, purchase.getAmount());
				statement2.setLong(2, purchase.getCouponId());

				// Preparing the updates for execution
				statement.executeUpdate();
				statement2.executeUpdate();	

				//Executing prepared updates
				connection.commit();
			} catch (SQLException e){

				try {
					//a rollback required to restore values
					connection.rollback();
				} catch (SQLException e1) {
					throw new ApplicationException(e1, ExceptionType.GENERAL_ERROR, "Failed to rollback and restore values");
				}
			}
			//Setting connection back to auto commit
			connection.setAutoCommit(true);

			// If the purchase was created, returning the purchase ID
			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}
			throw new ApplicationException(ExceptionType.GENERAL_ERROR, "Failed to generate purchase ID");

		} catch (SQLException e) {

			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to create new purchase: " + purchase.toString());

		} finally {
			//Returning connection to the connection pool
			connections.closePreparedStatement(statement2);
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public Purchase getPurchase(long id) throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from purchases where id =?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the query
			resultSet = statement.executeQuery();

			//Creating and returning a purchase from the received data 
			if(resultSet.next()) {
				return resultSetPurchaseCreation(resultSet);
			}
			return null;

		}catch(SQLException e) {

			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get purchase by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public void deletePurchase (long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "delete from purchases where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to delete purchase by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public List<Purchase> getAllPurchases() throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from purchases;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get all purchases");

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	} 


	public List<Purchase> getPurchasesByCompany(long companyId) throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from purchases where coupon_id in (select id from coupons where company_id = ?);";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, companyId);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get purchases by company ID: " + companyId);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}   


	public List<Purchase> getPurchasesByUser(long userId) throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from purchases where user_id =?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, userId);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get purchases by user ID: " + userId);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}

	///////////////////
	//Private methods//
	///////////////////

	//Private method for list creation from a result set
	private List<Purchase> listCreation(ResultSet resultSet) throws SQLException{

		// Creating a list to store all the purchases
		List<Purchase> purchases = new ArrayList<Purchase>();

		//Creating purchases from the received data and adding to the ArrayList
		while(resultSet.next()) {
			purchases.add(resultSetPurchaseCreation(resultSet));
		}
		return purchases;
	}


	//Private method for object creation from the result set
	private Purchase resultSetPurchaseCreation(ResultSet resultSet) throws SQLException {

		long id = resultSet.getLong("id");
		long userId = resultSet.getLong("user_id");
		long couponId = resultSet.getLong("coupon_id");
		int amount = 	resultSet.getInt("amount");
		Timestamp timestamp = resultSet.getTimestamp("timestamp");
		Purchase purchase = new Purchase(id, userId, couponId, amount, timestamp);
		return purchase;
	}
}
