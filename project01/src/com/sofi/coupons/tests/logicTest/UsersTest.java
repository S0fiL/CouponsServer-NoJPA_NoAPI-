package com.sofi.coupons.tests.logicTest;

import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.beans.User;
import com.sofi.coupons.enums.UserType;
import com.sofi.coupons.logic.CompaniesController;
import com.sofi.coupons.logic.UsersController;

public class UsersTest {

	private static UsersController userCon = new UsersController();
	private static CompaniesController companiesCon = new CompaniesController();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		testCreateUser();
//		testGetUser();
//		testUpdateUser();
//		testDeleteUser();
//		testGetAllUsers();
//		testLogin();
	}

	private static void testCreateUser() {

		System.out.println("User Name test");
		System.out.print("Null: ");
		try {
			userCon.createUser(new User(null, "TheLaziest0", UserType.ADMIN, null, "aaa", "aaa"));
			System.out.println("User Name null test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Short: ");
		try {
			userCon.createUser(new User("@.", "ABitLazy0", UserType.ADMIN, null, "aaa", "aaa"));
			System.out.println("User Name empty test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Long: ");
		try {
			userCon.createUser(new User("IWasReallyStruglingToFindAUserNameThatWasntAlreadyTaken@gmail.com", "letMeIn111", UserType.CUSTOMER, null, "aaa", "aaa"));
			System.out.println("User Name too long test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("User name already exists: ");
		try {
			userCon.createUser(new User("TheChosen@One.com", "The1AndOnly", UserType.ADMIN, null, "aaa", "aaa"));
			userCon.createUser(new User("TheChosen@One.com", "notAn1mposter", UserType.CUSTOMER, null, "aaa", "aaa"));
			System.out.println("User Name already exists  test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nPassword test");
		System.out.print("Null: ");
		try {
			userCon.createUser(new User("WhoNeedsA@Password.com", null, UserType.CUSTOMER, null, "aaa", "aaa"));
			System.out.println("Password null test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Short: ");
		try {
			userCon.createUser(new User("Not@Me.com", "1a", UserType.CUSTOMER, null, "aaa", "aaa"));
			System.out.println("Password short test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Long: ");
		try {
			userCon.createUser(new User("Important@Stuff.com", "TotallyNotOverProtected2020", UserType.ADMIN, null, "aaa", "aaa"));
			System.out.println("Password long test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Symbols: ");
		try {
			userCon.createUser(new User("The@Creative.One", "a1\\(=^-^=)//1a", UserType.ADMIN, null, "aaa", "aaa"));
			System.out.println("Password symbols test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("No demanded characters: ");
		try {
			userCon.createUser(new User("imFor@narchy.com", "aaaaaaaaa", UserType.ADMIN, null, "aaa", "aaa"));
			System.out.println("Password characters test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nCompany ID test");
		System.out.print("User type mismatch: ");
		try {
			long id = companiesCon.createCompany(new Company("Hansa", "hansa.station@metro.ru", "*2033", "Moscow, metro, Ring Line"));
			try {
				userCon.createUser(new User("Ranger@vdnh.ru", "1withTheDark1s", UserType.CUSTOMER, id, "Artyom", "Chyornyj"));
				System.out.println("Company ID for non company user type test failed");
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(id);
		}catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
		System.out.print("Invalid company ID: ");
		try {
			long id = companiesCon.createCompany(new Company("Unseen University", "UU@discworld.com", "000000", "Ankh-Morpork"));
			companiesCon.deleteCompany(id);
			userCon.createUser(new User("NumberZero@disc.wor", "TheLuggage7", UserType.COMPANY, id, "Rincewind", "Wizzard"));
			System.out.println("Invalid company ID test failed");
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Company user with ID null: ");
		try {
			userCon.createUser(new User("Wolf@wall.st", "toTheTop569", UserType.COMPANY, null, "Jordan", "Belfort"));
			System.out.println("Company user with no company ID test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nFirst name test");
		System.out.print("Null: ");
		try {
			userCon.createUser(new User("no@face.iu", "Ch1h1ro00", UserType.CUSTOMER, null, null, "   "));
			System.out.println("First name null test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Empty: ");
		try {
			userCon.createUser(new User("Doctor@Who.tr", "Jeronim0", UserType.CUSTOMER, null, "", "   "));
			System.out.println("First name empty test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Short: ");
		try {
			userCon.createUser(new User("L@L.dn", "f1ndk1ra", UserType.CUSTOMER, null, "L", "   "));
			System.out.println("First name short test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Long: ");
		try {
			userCon.createUser(new User("Khaleesi@westeros.7k", "Freedom47lands", UserType.CUSTOMER, null, "DaenerysStormbornOfTheHouseTargaryenFirstOfHerName", "MotherOfDragons"));
			System.out.println("First name long test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Symbols: ");
		try {
			userCon.createUser(new User("X-Ash@tesla.com", "spaceXF9", UserType.ADMIN, null, "X Æ A-Xii", "Musk"));
			System.out.println("First name symbols test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nLast name test");
		System.out.print("Null: ");
		try {
			userCon.createUser(new User("9000@space.un", "Sentinel2001", UserType.ADMIN, null, "HAL", null));
			System.out.println("Last name null test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Empty: ");
		try {
			userCon.createUser(new User("Chief@master.ha", "Navyy117", UserType.CUSTOMER, null, "John", ""));
			System.out.println("Last name empty test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Short: ");
		try {
			userCon.createUser(new User("MIB@agency.sp", "Laurana1975", UserType.CUSTOMER, null, "Mr ", "K"));
			System.out.println("Last name short test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Long: ");
		try {
			userCon.createUser(new User("5ive@lmnt.com", "multiPa55", UserType.CUSTOMER, null, "Leeloo", "Leeloominai Lekatariba Lamina Tchai Ekbat De Sebat"));
			System.out.println("Last name long test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Symbols: ");
		try {
			userCon.createUser(new User("Android@CyberLife.com", "DeviantRa9", UserType.CUSTOMER, null, "Connor", "RK900"));
			System.out.println("Last name symbols test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("\nValid user test");
		try {
			long id = userCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			if(userCon.getUser(id)!=null)
				System.out.println("User created successfully");
			else
				System.out.println("User creation failed");
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetUser() {

		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"); 
			long id =userCon.createUser(user);
			User user2 = userCon.getUser(id);
			if(user.getUserName().equals(user2.getUserName()) && user.getPassword().equals(user2.getPassword()) 
					&& user.getUserType() == user2.getUserType() && user.getCompanyId()== user2.getCompanyId()
					&& user.getFirstName().equals(user2.getFirstName()) && user.getLastName().equals(user2.getLastName()))
				System.out.println("User was identified successfully");
			else
				System.out.println("Get user failed");
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testUpdateUser() {

		System.out.print("Invalid data test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			user = userCon.getUser(id);
			user.setUserName("111");
			try {
				userCon.updateUser(user);
				System.out.println("Invalid data update test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Valid user test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			user = userCon.getUser(id);
			user.setPassword("Aaaa2222");
			userCon.updateUser(user);
			if(user.getPassword().equals(userCon.getUser(id).getPassword()))
				System.out.println("User was updated successfully");
			else
				System.out.println("User update test failed");
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testDeleteUser() {

		System.out.print("Valid user test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			userCon.deleteUser(id);
			if(userCon.getUser(id) == null)
				System.out.println("User was deleted successfully");
			else
				System.out.println("User delete test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetAllUsers() {

		try {
			long id = userCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			List<User> users = userCon.getAllUsers();
			if(!users.isEmpty())
				System.out.println("Users list created successfully");
			else
				System.out.println("List creation test failed");
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testLogin() {

		System.out.print("Invalid password test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			try {
				userCon.login(user.getUserName(), "Bbbb2222");
				System.out.println("Invalid password test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Invalid user name test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			try {
				userCon.login("b@b.bb", user.getPassword());
				System.out.println("Invalid user name test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Valid loggin test: ");
		try {
			User user = new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa");
			long id = userCon.createUser(user);
			UserType userType = userCon.login(user.getUserName(), user.getPassword());
			if(userType == user.getUserType())
				System.out.println("Logged in successfully");
			else
				System.out.println("Loggin failed");
			userCon.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

