package com.sofi.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;
import com.sofi.coupons.utils.ConnectionPool;

public class CompaniesDao {

	private ConnectionPool connections;

	public CompaniesDao() {
		connections = ConnectionPool.getInstance();
	}


	public long createCompany(Company company) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			// Company ID is defined as a primary key and auto incremented           
			String sqlStatement = "insert into companies (name, email, phone, address) values (?,?,?,?);";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement, PreparedStatement.RETURN_GENERATED_KEYS);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPhone());
			statement.setString(4, company.getAddress());

			// Executing the update
			statement.executeUpdate();


			// If the company was created, returning the company ID
			resultSet = statement.getGeneratedKeys();

			if (resultSet.next()) {
				long id = resultSet.getLong(1);
				return id;
			}
			throw new ApplicationException(ExceptionType.GENERAL_ERROR, "Failed to generate ID");

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to create company: " + company.toString());

		}finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	} 

	public Company getCompany(long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from companies where id =?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the query
			resultSet = statement.executeQuery();

			//Creating and returning a company from the received data 
			if(resultSet.next()) {
				return ResultSetCompanyCreation(resultSet);
			}else {
				return null;
			}

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get company by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public void updateCompany(Company company) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			// CompanyID is defined as a primary key and auto incremented           
			String sqlStatement = "update companies set name =?, email = ?, phone = ?, address = ? where id = ?;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setString(1, company.getName());
			statement.setString(2, company.getEmail());
			statement.setString(3, company.getPhone());
			statement.setString(4, company.getAddress());
			statement.setLong(5, company.getId());

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to update company: " + company.toString());

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public void deleteCompany (long id) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		
		try {
			// Establish a connection from the connection manager
			connection = connections.getConnection();

			// Creating the SQL query 
			//"On delete CASCADE" was applied in MySQL, therefore no need to delete coupons and purchases prior to company;
			String sqlStatement = "delete from companies where id = ?;";

			// Combining between the syntax and our connection
			statement= connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			statement.setLong(1, id);

			// Executing the update
			statement.executeUpdate();

		} catch (SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to delete company by ID: " + id);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement);
		}
	}


	public List<Company> getAllCompanies() throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select * from companies;";

			// Combining between the syntax and our connection
			statement = connection.prepareStatement(sqlStatement);

			// Executing the query
			resultSet = statement.executeQuery();

			// Creating a list to store all the companies
			List<Company> companies = new ArrayList<Company>();

			//Creating companies from the received data and adding to the ArrayList
			while(resultSet.next()) {
				companies.add(ResultSetCompanyCreation(resultSet));
			}
			return companies;

		}catch(SQLException e) {
			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to get all companies");

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}


	public boolean isCompanyNameExists(String name) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select name from companies where name = ?;";

			// Combining between the syntax and our connection
			PreparedStatement stt = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			stt.setString(1, name);

			// Executing the query
			resultSet = stt.executeQuery();

			//if company by this name was found return true, if not, return false
			if(resultSet.next()) {
				return true;
			}
			return false;

		}catch(SQLException e) {

			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to check if company's name exists: " + name);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}

	public boolean isCompanyEmailExists(String email) throws ApplicationException {

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			// Establish a connection trough the connection pool
			connection = connections.getConnection();

			// Creating the SQL query
			String sqlStatement = "select email from companies where email = ?;";

			// Combining between the syntax and our connection
			PreparedStatement stt = connection.prepareStatement(sqlStatement);

			// Replacing the question marks in the statement above with the relevant data
			stt.setString(1, email);

			// Executing the query
			resultSet = stt.executeQuery();

			//if company by this name was found return true, if not, return false
			if(resultSet.next()) {
				return true;
			}
			return false;

		}catch(SQLException e) {

			// If there was an exception in the "try" block above, it is caught here and notifies the level above.
			throw new ApplicationException(e, ExceptionType.GENERAL_ERROR, "Failed to check if company's e-mail exists: " + email);

		} finally {
			//Returning connection to the connection pool
			connections.closeResources(connection, statement, resultSet);
		}
	}

	///////////////////
	//Private methods//
	///////////////////

	//Private method for object creation from the result set
	private Company ResultSetCompanyCreation(ResultSet resultSet ) throws SQLException {

		long id = resultSet.getLong("id");
		String name = resultSet.getString("name");
		String email = resultSet.getString("email");
		String phone =resultSet.getString("phone");
		String address =  resultSet.getString("address");
		Company company = new Company(id, name, email, phone, address);
		return company;
	}
}
