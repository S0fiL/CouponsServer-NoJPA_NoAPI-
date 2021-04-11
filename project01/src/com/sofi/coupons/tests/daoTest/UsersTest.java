package com.sofi.coupons.tests.daoTest;

import java.util.List;

import com.sofi.coupons.beans.User;
import com.sofi.coupons.dao.UsersDao;
import com.sofi.coupons.enums.UserType;

public class UsersTest {

	private static UsersDao userDao = new UsersDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//				testCreateUser();
//				testUpdateUser();
//				testDeleteUser();
//				testGetAllUsers();
//				testGetUserById();
//				testIsUserNameExists();
//				testLogin();
	}


	private static void testCreateUser() {

		User user = new User("SunBro", "praiseTheSun", UserType.COMPANY, null, "Solaire", "of Astora");
		try {
			long id = userDao.createUser(user);
			if(id != 0) {			
				System.out.println("User was created by the id - "+id);
			} else {
				System.out.println("User creation test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetUserById() {

		User user = new User("ShadowBrocker", "byTheGodess", UserType.ADMIN, null, "liara", "T'sony");
		try {
			long id = userDao.createUser(user);
			User user2 = userDao.getUser(id);
			if(user.getUserName().equals(user2.getUserName()) && user.getPassword().equals(user2.getPassword()) 
					&& user.getUserType().equals(user2.getUserType()) && user.getCompanyId()==(user2.getCompanyId()) 
					&& user.getFirstName().equals(user2.getFirstName()) && user.getLastName().equals(user2.getLastName())) { 
				System.out.println("The user was extracted by ID");
			} else {
				System.out.println("Get user test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testUpdateUser() {

		User user = new User("FalseShepherd", "findTheGirl", UserType.CUSTOMER, null, "Booker", "Dewitt");
		try {
			long id = userDao.createUser(user);
			user = userDao.getUser(id);
			user.setLastName("father comstock");
			userDao.updateUser(user);
			User user2 = userDao.getUser(id);
			if(user.getUserName().equals(user2.getUserName()) && user.getPassword().equals(user2.getPassword()) 
					&& user.getUserType().equals(user2.getUserType()) && user.getCompanyId()==(user2.getCompanyId()) 
					&& user.getFirstName().equals(user2.getFirstName()) && user.getLastName().equals(user2.getLastName())) {
				System.out.println("User was updated");
			} else {
				System.out.println("User update test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testDeleteUser() {

		User user = new User("SunFirstBorn", "stormKing", UserType.CUSTOMER, null, "Nameless", "King");
		try {
			long id = userDao.createUser(user);
			userDao.deleteUser(id);
			if(userDao.getUser(id)==null) {
				System.out.println("User was deleted");
			} else {
				System.out.println("Delete user test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testGetAllUsers() {

		User user = new User("Archangel", "calibrations", UserType.CUSTOMER, null, "Garrus", "Vakarian");
		try {
			long id = userDao.createUser(user);
			List<User> users = userDao.getAllUsers();
			if(!users.isEmpty()) {
				System.out.println("User list was created");
			} else {
				System.out.println("Get all users test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testIsUserNameExists() {

		String name = "Neo";
		User user = new User(name, "thereIsNoSpoon", UserType.ADMIN, null, "Thomas", "Anderson");
		try {
			long id = userDao.createUser(user);
			if(userDao.isUserNameExists(name)) {
				System.out.println("User name was identified");
			} else {
				System.out.println("Is user name exists test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testLogin() {

		User user = new User("Runner", "origamiUnicorn", UserType.CUSTOMER, null, "Rick", "Deckard");
		try {
			long id = userDao.createUser(user);
			if(userDao.login(user.getUserName(), user.getPassword())==user.getUserType()) {
				System.out.println("User loged in");
			} else {
				System.out.println("Login test failed");
			}
			userDao.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}



