package com.sofi.coupons.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.enums.Category;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;
import com.sofi.coupons.utils.ConnectionPool;

public class CouponsDao {

	ConnectionPool connections;

	public CouponsDao() {
		connections = ConnectionPool.getInstance();
	}

	public long createCoupon(Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			// Coupons ID is defined as a primary key and auto incremented
			String sqlStatement = "insert into coupons (company_id, category, title, description, start_date, end_date, amount, price, image) values (?,?,?,?,?,?,?,?,?);";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, coupon.getCompanyId());
			statement.setString(2, coupon.getCategory().name());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStartDate());
			statement.setDate(6, coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setFloat(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());

			// Executing the update
			statement.executeUpdate();

			// If the coupon was created, returning the coupon ID
			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}
			throw new ApplicationException(ExceptionType.GENERAL_ERROR, "Failed to generate coupon ID");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to create a new coupon: " + coupon.toString());

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}    

	public Coupon getCoupon(Long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons where id =?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the query
			resultSet = statement.executeQuery();

			//Creating and returning a coupon from the received data 
			if(resultSet.next()) {
				return resulSetCouponCreation(resultSet);
			} else {
				return null;
			}

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Faild to get coupon by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public void updateCoupon (Coupon coupon) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query

			String sqlStatement = "update coupons set company_id = ?, category = ?, title = ?, description = ?, start_date = ?, end_date = ?, amount = ?, price = ?, image = ? where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, coupon.getCompanyId());
			statement.setString(2, coupon.getCategory().name());
			statement.setString(3, coupon.getTitle());
			statement.setString(4, coupon.getDescription());
			statement.setDate(5, coupon.getStartDate());
			statement.setDate(6, coupon.getEndDate());
			statement.setInt(7, coupon.getAmount());
			statement.setFloat(8, coupon.getPrice());
			statement.setString(9, coupon.getImage());
			statement.setLong(10, coupon.getId());

			// Executing the update
			statement.executeUpdate();

		} catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to update coupon: " + coupon.toString());

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public void deleteCoupon (long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			//"On delete CASCADE" was applied in MySQL, therefore no need to delete purchases prior to coupons;
			String sqlStatement = "delete from coupons where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to delete coupon by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public List<Coupon> getAllCoupons() throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get all coupons");

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public List<Coupon> getCouponsByCompany (long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons where company_id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, companyId);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		} catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get coupons by company ID: " + companyId);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public List<Coupon> getCouponsByCategory (Category category) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons where category = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, category.name());

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		} catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get coupon by category: " + category);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}   


	public boolean isCouponTitleExists(String couponTitle, long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons where title = ? and company_id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, couponTitle);
			statement.setLong(2, companyId);

			// Executing the query
			resultSet = statement.executeQuery();

			//if coupon by this title was found return true, if not, return false
			if(resultSet.next()) {
				return true;
			}
			return false;

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to check if coupon title exists: " + couponTitle);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}   
	}


	public void deleteExpiredCoupons (java.sql.Date expirationDate) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			//"On delete CASCADE" was applied in MySQL, therefore no need to delete purchases prior to coupons;
			String sqlStatement = "delete from coupons where end_date < ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setDate(1, expirationDate);

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to delete coupons with end date: " + expirationDate);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}

	public List<Coupon> getPurchasedCouponsByMaxPrice(long userId, float maxPrice) throws ApplicationException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {

			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from coupons where price <= ? and id in (select coupon_id from purchases where user_id = ?);";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setFloat(1, maxPrice);
			statement.setLong(2, userId);

			// Executing the query
			resultSet = statement.executeQuery();

			//Calling a method to create a list from the resultSet and return it
			return listCreation(resultSet);

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get purchased coupons: user Id - " + userId + ", MaxPrice - " + maxPrice);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}

	///////////////////
	//Private methods//
	///////////////////

	//Private method for list creation from a result set
	private List<Coupon> listCreation(ResultSet resultSet) throws SQLException{

		// Creating a list to store all the purchases
		List<Coupon> coupons = new ArrayList<Coupon>();

		//Creating purchases from the received data and adding to the ArrayList
		while(resultSet.next()) {
			coupons.add(resulSetCouponCreation(resultSet));
		}
		return coupons;
	}

	//Private method for object creation from the result set
	private Coupon resulSetCouponCreation(ResultSet resultSet) throws SQLException {

		long id = (resultSet.getLong("id"));
		long companyId = resultSet.getLong("company_id");
		Category category = Category.valueOf(resultSet.getString("category"));
		String title = resultSet.getString("title");
		String description = resultSet.getString("description");
		Date startDate = resultSet.getDate("start_date");
		Date endDate = resultSet.getDate("end_date");
		int amount = resultSet.getInt("amount");
		float price = resultSet.getFloat("price");
		String image = resultSet.getString("image");
		Coupon coupon = new Coupon(id, companyId, category, title, description, startDate, endDate, amount, price, image);
		return coupon;
	}
}
