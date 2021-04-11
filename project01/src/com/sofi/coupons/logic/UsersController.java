package com.sofi.coupons.logic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sofi.coupons.beans.User;
import com.sofi.coupons.dao.UsersDao;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.enums.UserType;
import com.sofi.coupons.exceptions.ApplicationException;

public class UsersController {

	private UsersDao usersDao;
	private CompaniesController companiesCon;

	public UsersController() {
		usersDao = new UsersDao();
		companiesCon = new CompaniesController();
	}

	///////////////////////////////////////////////
	//Public methods that call validation methods//
	///////////////////////////////////////////////

	public long createUser (User user) throws ApplicationException {
		createUserValidation(user);
		return usersDao.createUser(user);
	}

	public User getUser (long id) throws ApplicationException {
		
		User user = usersDao.getUser(id);
		if(user == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid user ID" + id);
		}
		return user;
	}

	public void updateUser (User user) throws ApplicationException {
		updateUserValidation(user);
		usersDao.updateUser(user);
	}

	public void deleteUser (long id) throws ApplicationException {
		usersDao.deleteUser(id);
	}

	public List<User> getAllUsers() throws ApplicationException {
		return usersDao.getAllUsers();
	}

	public UserType login (String userName, String password) throws ApplicationException {

		UserType userType = usersDao.login(userName, password);
		if(userType == null) {
			throw new ApplicationException(ExceptionType.LOGIN_FAILED, "Invalid username or password");
		}
		return userType;
	}

	//////////////////////////////
	//Private validation methods//
	//////////////////////////////

	private void createUserValidation(User user) throws ApplicationException {

		//Check if all the data is valid
		userInfoValidation(user);

		//User that doesn't work in a company can't have a company ID
		if(user.getUserType() != UserType.COMPANY && user.getCompanyId() != null) {
			throw new ApplicationException(ExceptionType.USER_TYPE_MISSMATCH, "User type and company ID missmatch: user type - " + user.getUserType()+", company ID - "+user.getCompanyId());
		}
		//Can't create two users with the same user name 
		if(usersDao.isUserNameExists(user.getUserName())) {
			throw new ApplicationException(ExceptionType.USER_NAME_TAKEN, "User name already taken: " + user.getUserName());
		}
		//If user works in a company he has to enter a valid company ID 
		if(user.getUserType() == UserType.COMPANY && (user.getCompanyId() == null || companiesCon.getCompany(user.getCompanyId()) == null)) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid company ID: " + user.getCompanyId());
		}
	}


	private void updateUserValidation (User user) throws ApplicationException {

		//Check if all the data is valid
		userInfoValidation(user);

		//Get user's original info 
		User oldUser = usersDao.getUser(user.getId());
		//Can't update company ID or User type (if user switched jobs he has to create a new account for a new work place, and a different account as a customer)
		if(user.getCompanyId() != oldUser.getCompanyId() || user.getUserType() != oldUser.getUserType()) {
			throw new ApplicationException(ExceptionType.INVALID_UPDATE, "Invalid update: " + user.toString());
		}
		//If updating user name, need to check if the new user name isn't already taken
		if(!user.getUserName().equals(oldUser.getUserName()) && usersDao.isUserNameExists(user.getUserName())) {
			throw new ApplicationException(ExceptionType.USER_NAME_TAKEN, "New user name is already taken: " + user.getUserName());
		}
	}


	private void userInfoValidation (User user) throws ApplicationException {

		//Assuming user name has to be an e-mail, it has to contain between 6 and 45 characters, the characters . and @
		if(isOutOfRange(user.getUserName(), 6, 45) || !user.getUserName().contains("@") 
				|| !user.getUserName().contains(".")) {
			throw new ApplicationException(ExceptionType.INVALID_EMAIL, "Invalid user name: " + user.getUserName());
		}
		//Password has to contain between 8 and 18 characters
		if(isOutOfRange(user.getPassword(), 8, 18)) { 
			throw new ApplicationException(ExceptionType.INVALID_PASSWORD_LENGTH, "Invalid password length");
		}
		//Creating a pattern of characters that the password can't contain and two that it has to contain and matching them
		Pattern symbols = Pattern.compile("[^A-Za-z0-9]");
		Pattern numbers = Pattern.compile("[0-9]");
		Pattern letter = Pattern.compile("[A-Za-z]");
		Matcher matchS = symbols.matcher(user.getPassword());
		Matcher matchN = numbers.matcher(user.getPassword());
		Matcher matchL = letter.matcher(user.getPassword());
		//Password has to contain one letter, one number and no symbols
		if(matchS.find() || !matchN.find() || !matchL.find()) {
			throw new ApplicationException(ExceptionType.INVALID_PASSWORD, "Invalid password");
		}
		//Last name and first name have to contain between 2 and 45 characters
		if(isOutOfRange(user.getFirstName(), 2, 45)) {
			throw new ApplicationException(ExceptionType.INVALID_FIRST_NAME, "Invalid first name: " + user.getFirstName());  
		}
		if(isOutOfRange(user.getLastName(), 2, 45)) {
			throw new ApplicationException(ExceptionType.INVALID_LAST_NAME, "Invalid last name: " + user.getLastName());
		}
		//Creating a pattern of symbols that last name and first name can't contain and matching it 
		Pattern Name = Pattern.compile("[^A-Za-z '-]");
		Matcher matchFn = Name.matcher(user.getFirstName());
		Matcher matchLn = Name.matcher(user.getLastName());

		 //Last name and first name can't contain the symbols in the pattern above
		if(matchFn.find()) {
			throw new ApplicationException(ExceptionType.INVALID_FIRST_NAME, "Invalid first name: " + user.getFirstName());  
		}
		if(matchLn.find()) {
			throw new ApplicationException(ExceptionType.INVALID_LAST_NAME, "Invalid last name: " + user.getLastName());
		}
	}

    private static boolean isOutOfRange(String text, int minChar, int maxChar) {
    	
    	if(text == null || text.length() < minChar || text.length() > maxChar) {
    		return true;
    	}
    	return false;
    }
}
