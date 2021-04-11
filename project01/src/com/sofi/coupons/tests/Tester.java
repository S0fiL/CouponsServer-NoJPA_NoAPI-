package com.sofi.coupons.tests;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.beans.Purchase;
import com.sofi.coupons.beans.User;
import com.sofi.coupons.dailyExpirationCheck.CouponsTimerTask;
import com.sofi.coupons.enums.Category;
import com.sofi.coupons.enums.UserType;
import com.sofi.coupons.exceptions.ApplicationException;
import com.sofi.coupons.logic.CompaniesController;
import com.sofi.coupons.logic.CouponsController;
import com.sofi.coupons.logic.PurchasesController;
import com.sofi.coupons.logic.UsersController;
import com.sofi.coupons.utils.ConnectionPool;

public class Tester {

	public static CouponsController couponsController = new CouponsController();
	public static CompaniesController companiesController = new CompaniesController();
	public static UsersController usersController = new UsersController();
	public static PurchasesController purchasesController = new PurchasesController();
	public static long now = Calendar.getInstance().getTimeInMillis();

	public static void main(String[] args) {
		// TODO Auto-generated meRhod stub

		//Timer Task
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.DAY_OF_MONTH, Calendar.DAY_OF_MONTH+1);
		java.util.Date start = cal.getTime();

		Timer timer = new Timer();
		TimerTask timerTask = new CouponsTimerTask();
		timer.schedule(timerTask, start, 1000*60*60*24);
		System.out.println("Task started");

		//Call all test methods
		testAll();

		//Close TimerTask
		timerTask.cancel();
		timer.cancel();
		System.out.println("Task stopped");

		//Close all connections
		ConnectionPool connectionPool = new ConnectionPool();
		try {
			connectionPool.closeAllConnections();
			System.out.println("Connections closed");
		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}

	}

	private static void testAll() {

		testCreateCompany();
		testGetCompany();
		testUpdateCompany();
		testDeleteCompany();
		testGetAllCompanies();

		testCreateUser();
		testGetUser();
		testUpdateUser();
		testDeleteUser();
		testGetAllUsers();
		testLogin();

		testCreateCoupon();
		testGetCoupon();
		testUpdateCoupon();
		testDeleteCoupon();
		testGetAllCoupons();
		testGetCouponsByCompany();
		testGetCouponsByCategory();
		testGetPurchasedCouponsByMaxPrice();

		testCreatePurchase();
		testGetPurchase();
		testDeletePurchase();
		testGetAllPurchases();
		testGetPurchasesByCompany();
		testGetPurchasesByUser();
	}

	/////////////////
	//Company tests//
	/////////////////  

	private static void testCreateCompany() {

		Company company = new Company("Sun Covenant", "jolly.cooperation@lordran.com", "5049" , "under the red drake");
		try {
			long id = companiesController.createCompany(company);
			if(id != 0) {
				System.out.println("Company was created by the id - "+id);
			} else {
				System.out.println("Company creation test failed");
			}
			companiesController.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCompany() {

		Company company = new Company("Black Mesa", "black.mesa@valve.com", "00110011", "New Mexico, USA");
		try {
			long id = companiesController.createCompany(company);
			Company company2 = companiesController.getCompany(id);
			if(company.getName().equals(company2.getName()) && company.getEmail().equals(company2.getEmail())
					&& company.getPhone().equals(company2.getPhone()) && company.getAddress().equals(company2.getAddress())) {
				System.out.println("Company was extracted by ID");
			} else {
				System.out.println("Get company test failed");
			}
			companiesController.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testUpdateCompany() {

		Company company = new Company("Umbrella Academy", "umbrella@ua.com", "12-345-67", "London 4 King Street East");
		try {
			long id = companiesController.createCompany(company);
			company = companiesController.getCompany(id);
			company.setName("Sparrow Academy");
			companiesController.updateCompany(company);
			Company company2 = companiesController.getCompany(id);
			if(company.getName().equals(company2.getName()) && company.getEmail().equals(company2.getEmail())
					&& company.getPhone().equals(company2.getPhone()) && company.getAddress().equals(company2.getAddress())) {
				System.out.println("Company was updated");
			} else {
				System.out.println("Company update test failed");
			}
			companiesController.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testDeleteCompany() {

		Company company = new Company("Ryan Industries", "Would.You.Kindly@doit.now", "22-555-333" , "Rapture");
		try {
			long id = companiesController.createCompany(company);
			companiesController.deleteCompany(id);
			try {
				companiesController.getCompany(id);
			} catch (Exception e) {
				System.out.println("Company was deleted");
			}
		} catch (Exception e1) {
			System.out.println(e1.getMessage());
		}
	}


	private static void testGetAllCompanies() {

		Company company = new Company("Stark Industries", "iron.man@avengers.com", "11-111-1111", "Avengers tower");
		try {
			long id = companiesController.createCompany(company);
			List<Company> companies = companiesController.getAllCompanies();
			if(!companies.isEmpty()) {
				System.out.println("List of companies was created");
			} else {
				System.out.println("Get all companies test failed");
			}
			companiesController.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	///////////////
	//Users tests//
	///////////////

	private static void testCreateUser() {

		User user = new User("SunBro@lordran.drg", "pra1seTheSun", UserType.ADMIN, null, "Solaire", "of Astora");
		try {
			long id = usersController.createUser(user);
			if(id != 0) {			
				System.out.println("User was created by the id - "+id);
			} else {
				System.out.println("User creation test failed");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetUser() {

		User user = new User("sayWhat@md.fk", "Ezekiel2517", UserType.ADMIN, null, "Jules", "Winnfield");
		try {
			long id = usersController.createUser(user);
			User user2 = usersController.getUser(id);
			if(user.getUserName().equals(user2.getUserName()) && user.getPassword().equals(user2.getPassword()) 
					&& user.getUserType().equals(user2.getUserType()) && user.getCompanyId()==(user2.getCompanyId()) 
					&& user.getFirstName().equals(user2.getFirstName()) && user.getLastName().equals(user2.getLastName())) { 
				System.out.println("User was extracted by ID");
			} else {
				System.out.println("Get user test failed");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testUpdateUser() {

		User user = new User("False@Shepherd.co", "findTheG1rl", UserType.CUSTOMER, null, "Booker", "Dewitt");
		try {
			long id = usersController.createUser(user);
			user = usersController.getUser(id);
			user.setLastName("Father Comstock");
			usersController.updateUser(user);
			User user2 = usersController.getUser(id);
			if(user.getUserName().equals(user2.getUserName()) && user.getPassword().equals(user2.getPassword()) 
					&& user.getUserType().equals(user2.getUserType()) && user.getCompanyId()==(user2.getCompanyId()) 
					&& user.getFirstName().equals(user2.getFirstName()) && user.getLastName().equals(user2.getLastName())) {
				System.out.println("User was updated");
			} else {
				System.out.println("User update test failed");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testDeleteUser() {

		User user = new User("FirstBorn@sun.gw", "st0rmKing", UserType.CUSTOMER, null, "Nameless", "King");
		try {
			long id = usersController.createUser(user);
			usersController.deleteUser(id);
			try {
				if(usersController.getUser(id)==null);
			} catch (Exception e1) {
				System.out.println("User was deleted");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testGetAllUsers() {

		User user = new User("archangel@omega.sl", "calibration5", UserType.CUSTOMER, null, "Garrus", "Vakarian");
		try {
			long id = usersController.createUser(user);
			List<User> users = usersController.getAllUsers();
			if(!users.isEmpty()) {
				System.out.println("User list was created");
			} else {
				System.out.println("Get all users test failed");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testLogin() {

		User user = new User("Runner@tyrell.cor", "origam1corn", UserType.CUSTOMER, null, "Rick", "Deckard");
		try {
			long id = usersController.createUser(user);
			if(usersController.login(user.getUserName(), user.getPassword())==user.getUserType()) {
				System.out.println("User loged in");
			} else {
				System.out.println("Login test failed");
			}
			usersController.deleteUser(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/////////////////
	//Coupons tests//
	/////////////////

	public static void testCreateCoupon() {

		long startMillis = new GregorianCalendar(2021, 01, 01).getTimeInMillis();
		long endMillis = new GregorianCalendar(3000, 11, 30).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Best Company", "goodBoi@pets.paw", "10-100-101010", "Heaven");
			long companyId = companiesController.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.FOR_PETS, "The whole world", "everything your pet ever wanted", startDate, endDate, 10000, (float)00.01, "CUTE.DOGOS.AND.CATS.IMAGE");
			long id = couponsController.createCoupon(coupon);
			if(id != 0) {
				System.out.println("Coupon was created by the id - "+id);
			} else {
				System.out.println("Coupon creation test failed");
			}
			companiesController.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCoupon() {

		long startMillis = new GregorianCalendar(2020, 11, 11).getTimeInMillis();
		long endMillis = new GregorianCalendar(2042, 00, 01).getTimeInMillis();
		java.sql.Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Cyberdyne Systems", "skynet@corpor.com", "800-1000-3000", "18144 Al Camino Real, Sunnyvale, California");
			long companyId = companiesController.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "Judgement day", "How about a machine revolution?", startDate, endDate, 1, (float)123.00, "APOCALYPSE.IMAGE");
			long id = couponsController.createCoupon(coupon);
			Coupon coupon2 = couponsController.getCoupon(id);
			if(coupon.getCompanyId()==(coupon2.getCompanyId()) && coupon.getCategory().equals(coupon2.getCategory())
					&& coupon.getTitle().equals(coupon2.getTitle()) && coupon.getDescription().equals(coupon2.getDescription())
					//					&& coupon.getStartDate().equals(coupon2.getStartDate()) && coupon.getEndDate().equals(coupon2.getEndDate())
					&& coupon.getAmount()==coupon2.getAmount() && coupon.getPrice()==coupon2.getPrice() 
					&& coupon.getImage().equals(coupon2.getImage())) {
				System.out.println("Coupon was extracted by ID");
			} else {
				System.out.println("Get coupon test failed");
			}
			companiesController.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testUpdateCoupon() {

		long startMillis = new GregorianCalendar(2023, 5, 18).getTimeInMillis();
		long endMillis = new GregorianCalendar(2050, 5, 18).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Aperture Science", "GlaDos.assist@enrichment-center.com", "(513)273-77242", "Cleveland, Ohio");
			long companyId = companiesController.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "For Science", "Become a test subject for an unknow period of time", startDate, endDate, 10000, (float)50.99, "DEER.IMAGE");
			long id = couponsController.createCoupon(coupon);
			coupon = couponsController.getCoupon(id);
			coupon.setTitle("For cake");
			couponsController.updateCoupon(coupon);
			Coupon coupon2 = couponsController.getCoupon(id);

			if(coupon.getCompanyId()==(coupon2.getCompanyId()) && coupon.getCategory().equals(coupon2.getCategory())
					&& coupon.getTitle().equals(coupon2.getTitle()) && coupon.getDescription().equals(coupon2.getDescription())
					&& coupon.getStartDate().equals(coupon2.getStartDate()) && coupon.getEndDate().equals(coupon2.getEndDate())
					&& coupon.getAmount()==coupon2.getAmount() && coupon.getPrice()==coupon2.getPrice() 
					&& coupon.getImage().equals(coupon2.getImage())) {
				System.out.println("Coupon was updated");
			} else {
				System.out.println("Coupon update test failed");
			}
			companiesController.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testDeleteCoupon() {

		long startMillis = new GregorianCalendar(2044, 10, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2080, 11, 15).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Nuka-cola", "nuka.cola@quantum.com", "10-2076-2044", "United states of America");
			long companyId = companiesController.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.FOOD, "A blast of tastes", "50% sale on all drink variants", startDate, endDate, 1000000, (float)523.80, "LOGO.IMAGE");
			long id = couponsController.createCoupon(coupon);
			couponsController.deleteCoupon(id);
			try {
				if(couponsController.getCoupon(id)==null);
			} catch (Exception e1) {
				System.out.println("Coupon was deleted");
			}
			companiesController.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 


	private static void testGetAllCoupons() {

		long startMillis = new GregorianCalendar(2996, 1, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2999, 1, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Paper street Soap co.", "all.natural@soap.com", "(285)555-0153", "537 Paper Street. Bradford");
			long companyId = companiesController.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.HYGIENE, "Feel like yourself again", "20% sale on all handmade products" , startDate, endDate, 20, (float)20.99, "SOAP.IMAGE");
			couponsController.createCoupon(coupon);
			List<Coupon> coupons = couponsController.getAllCoupons();
			if(!coupons.isEmpty()) {
				System.out.println("Coupons list was created");
			} else {
				System.out.println("Get all coupons test failed");
			}
			companiesController.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}


	private static void testGetCouponsByCompany() {

		long startMillis1 = new GregorianCalendar(2056, 4, 20).getTimeInMillis();
		long endMillis1 = new GregorianCalendar(2057, 3, 17).getTimeInMillis();
		long startMillis2 = new GregorianCalendar(2021, 5, 23).getTimeInMillis();
		long endMillis2 = new GregorianCalendar(2033, 0, 1).getTimeInMillis();
		Date startDate1 = new Date(startMillis1);
		Date endDate1 = new Date(endMillis1);
		Date startDate2 = new Date(startMillis2);
		Date endDate2 = new Date(endMillis2);

		try {
			Company company = new Company("Blue Sun Corporation", "miranda@unionOfAP.com", "000", "United Space");
			Company company2 = new Company("Umbrella Corporation", "umbrella.phrma@corp.com", "99-999-99", "Britain, Racoon city");
			long companyId1 = companiesController.createCompany(company);
			long companyId2 = companiesController.createCompany(company2);
			Coupon coupon1 = new Coupon(companyId1, Category.FOR_KIDS, "Snacks", "Didn't see it coming", startDate1, endDate1, 12, (float)660.6, "MONSTER.IMAGE");
			Coupon coupon2 = new Coupon(companyId2, Category.HYGIENE, "Surprise in a flask", "Daily flask supply with unknow substance", startDate2,endDate2, 3000, (float)3299.99, "MONSTER.IMAGE");
			couponsController.createCoupon(coupon1);
			couponsController.createCoupon(coupon2);
			List<Coupon> coupons = couponsController.getCouponsByCompany(companyId1);
			boolean isCompanyId1=true;
			for (Coupon coupon : coupons) {
				if(coupon.getCompanyId()!=companyId1) {
					isCompanyId1 = false;
					System.out.println("Get coupons by company ID test failed");
					break;
				}   
			}
			if (isCompanyId1) {
				System.out.println("Coupons were extracted by company ID");
			}
			companiesController.deleteCompany(companyId1);
			companiesController.deleteCompany(companyId2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}


	private static void testGetCouponsByCategory() {

		long startMillis1 = new GregorianCalendar(2021, 2, 10).getTimeInMillis();
		long endMillis1 = new GregorianCalendar(2021, 10, 10).getTimeInMillis();
		long startMillis2 = new GregorianCalendar(2021, 2, 10).getTimeInMillis();
		long endMillis2 = new GregorianCalendar(2021, 10, 10).getTimeInMillis();
		Date startDate1 = new Date(startMillis1);
		Date endDate1 = new Date(endMillis1);
		Date startDate2 = new Date(startMillis2);
		Date endDate2 = new Date(endMillis2);

		try {
			Company company = new Company("Wayne Enterprises", "WayneCorp@totalynotbatman.com", "307-1979", "America, Gotham City");
			long companyId = companiesController.createCompany(company);
			Category category1 = Category.TECHNOLOGY;
			Category category2 = Category.SPORTS;
			Coupon coupon1 = new Coupon(companyId, category1, "Batvehicles", "30% on all Batmobiles ang Batcycles", startDate1, endDate1, 200, (float)100000.00, "BATLOGO.IMAGE");
			Coupon coupon2 = new Coupon(companyId, category2, "Batequipment", "30% on all Batequipment for Batworkouts", startDate2, endDate2, 200, (float)30000.00, "BATLOGO.IMAGE");
			couponsController.createCoupon(coupon1);
			couponsController.createCoupon(coupon2);
			List<Coupon> coupons = couponsController.getCouponsByCategory(category1);

			boolean isCategory1 = true;
			for (Coupon coupon : coupons) {
				if(coupon.getCategory()!=category1) {
					isCategory1 = false;
					System.out.println("Get coupons by category test failed");
					break;
				}
				if(isCategory1) {
					System.out.println("Coupons were extracted by category");
				}
				companiesController.deleteCompany(companyId);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testGetPurchasedCouponsByMaxPrice() {

		long startMillis = new GregorianCalendar(2037, 8, 19).getTimeInMillis();
		long endMillis = new GregorianCalendar(2200, 1, 12).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			long companyId = companiesController.createCompany(new Company("Matrix", "thereIsNo@spoon.net", "1111", "Virtual reality"));
			long couponId = couponsController.createCoupon(new Coupon(companyId, Category.FOOD, "Red pill", "you know the deal", startDate, endDate, 2, 1000, "-.IMAGE"));
			long couponId2 = couponsController.createCoupon(new Coupon(companyId, Category.VACATION, "Blue pill", "you know the deal", startDate, endDate, 2, (float)0.01, "-.IMAGE"));
			long userId1 = usersController.createUser(new User("neo@spoon.net", "TheChosenOne1", UserType.CUSTOMER, null, "Thomas ", "Anderson"));
			long userId2 = usersController.createUser(new User("Cypher@spoon.net", "LetMeIn1", UserType.CUSTOMER, null, "Mr", "Reagan"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			purchase1 = new Purchase(userId1, couponId2, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId2, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			float maxPrice = 999;
			List<Coupon> coupons = couponsController.getPurchasedCouponsByMaxPrice(userId1, maxPrice);
			if(coupons.size()>1) {
				System.out.println("Get purchases by user ID and max price test failed");
			} else {
				boolean isIdAndPrice = true;
				for (Coupon coupon : coupons) {
					if(coupon.getPrice()>maxPrice) {
						isIdAndPrice = false;
						System.out.println("Get purchases by user ID and max price test failed");
						break;
					}
				}
				if (isIdAndPrice) {
					System.out.println("Coupons were extracted by user ID and max price");
				}
				companiesController.deleteCompany(companyId);
				usersController.deleteUser(userId1);
				usersController.deleteUser(userId2);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	///////////////////
	//Purchases tests//
	///////////////////

	private static void testCreatePurchase() {

		long startMillis = new GregorianCalendar(2022, 0, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2024, 0, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			Company company = new Company("Gorsvet", "day-watch@gov.com", "(495)781-16-46", "Moscow, Vasily Petuchkov st 8");
			long companyId = companiesController.createCompany(company);
			User user = new User("Vampire@gov.com", "every0neIsEqual", UserType.CUSTOMER, null, "Kostya", "Saushkin");
			long userId = usersController.createUser(user);
			int couponsAmount = 20;
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "Fuaran", "Make everyone equal", startDate, endDate, couponsAmount, 100000000, "TEXT.IMAGE");
			long couponId = couponsController.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesController.createPurchase(purchase);
			int newAmount = couponsController.getCoupon(couponId).getAmount();
			if(id !=0 && newAmount == couponsAmount - purchase.getAmount()) {
				System.out.println("Purchase was created by the id - " + id);		
			} else {
				System.out.println("Purchase creation test failed");
			}
			companiesController.deleteCompany(companyId);
			usersController.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetPurchase() {

		long startMillis = new GregorianCalendar(2035, 0, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2260, 0, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			Company company = new Company("Reliable Excavation Demolition", "RED@demolition.com", "10-10-10", "US");
			long companyId = companiesController.createCompany(company);
			User user = new User("Demoman@team.red", "b000000m", UserType.CUSTOMER, null, "Scottish", "Cyclops");
			long userId = usersController.createUser(user);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "boom", "evenmoreboom", startDate, endDate, 10000, (float)6000.00, "SADDRUNK.IMAGE");
			long couponId = couponsController.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase);
			if(couponId == purchase.getCouponId() && userId == purchase.getUserId() && purchase.getAmount() == purchase.getAmount()) {
				System.out.println("Purchase was extracted by ID");
			} else {
				System.out.println("Get purchase test failed");
			}
			companiesController.deleteCompany(companyId);
			usersController.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testDeletePurchase() {

		long startMillis = new GregorianCalendar(2035, 0, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2260, 0, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			Company company = new Company("Hyperion", "pandoras.future@vaults.com", "1-000-000", "Helios station");
			long companyId = companiesController.createCompany(company);
			User user = new User("rhysCEO@atlas.com", "Hands0meJack", UserType.CUSTOMER, null, "Rhys", "Strongfork");
			long userId = usersController.createUser(user);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "Loader bot", "...", startDate, endDate, 20, 5000000, "HAPPYBOT.IMAGE");
			long couponId = couponsController.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesController.createPurchase(purchase);
			purchasesController.deletePurchase(id);
			try {
				purchasesController.getPurchase(id);
				System.out.println("Delete purchase test failed");
			} catch (Exception e) {
				System.out.println("Purchase was deleted");
			}
			companiesController.deleteCompany(companyId);
			usersController.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}   


	private static void testGetAllPurchases() {

		long startMillis = new GregorianCalendar(2035, 0, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2040, 0, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			long companyId = companiesController.createCompany(new Company("US Robotics", "USR.corp@UNICOM.com", "295-3030-9653", "Schaumburg, Illinois, U.S."));
			long userId = usersController.createUser(new User("Spooner@chicago.pd", "DRObOt5Dream", UserType.CUSTOMER, null, "Del", "Spooner"));
			long couponId = couponsController.createCoupon(new Coupon(companyId, Category.TECHNOLOGY, "Helper bot", "Buy a set of house helpers", startDate, endDate, 50000, 5000000, "I.IMAGE"));
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase);
			List<Purchase> Purchases = purchasesController.getAllPurchases();
			if(!Purchases.isEmpty()) {
				System.out.println("Purchases list was created");
			} else {
				System.out.println("Get all purchases test failed");
			}
			companiesController.deleteCompany(companyId);
			usersController.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 


	private static void testGetPurchasesByCompany() {

		long startMillis1 = new GregorianCalendar(2037, 6, 9).getTimeInMillis();
		long endMillis1 = new GregorianCalendar(2050, 11, 15).getTimeInMillis();
		long startMillis2 = new GregorianCalendar(2037, 6, 9).getTimeInMillis();
		long endMillis2 = new GregorianCalendar(2050, 10, 30).getTimeInMillis();
		Date startDate1 = new Date(startMillis1);
		Date endDate1 = new Date(endMillis1);
		Date startDate2 = new Date(startMillis2);
		Date endDate2 = new Date(endMillis2);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			long companyId1 = companiesController.createCompany(new Company("Tedior", "THROWIT@vaults.net", "145-5643315", "Pandora"));
			long companyId2 = companiesController.createCompany(new Company("Torgue", "BLOWIT@vaults.net", "154-4565545", "Pandora"));
			long couponId1 = couponsController.createCoupon(new Coupon(companyId1, Category.TECHNOLOGY, "Best equipment", "50% on all mods", startDate1, endDate1, 12, (float)660.6, "LOGO.IMAGE"));
			long couponId2 = couponsController.createCoupon(new Coupon(companyId2, Category.TECHNOLOGY, "Gold Collection", "20% on all legendary weapons", startDate2, endDate2, 3000, (float)3299.99, "LOGO.IMAGE"));
			long userId = usersController.createUser(new User ("zer0@vaults.net", "AB1ggerNumberThanU", UserType.CUSTOMER, null, "Zero", "zerO"));
			Purchase purchase1 = new Purchase(userId, couponId1, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			purchase1 = new Purchase(userId, couponId2, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			List<Purchase> purchases = purchasesController.getPurchasesByCompany(companyId1);
			boolean isId1=true;
			for (Purchase purchase : purchases) {
				if(couponsController.getCoupon(purchase.getCouponId()).getCompanyId()!= companyId1) {
					isId1 = false;
					System.out.println("Get purchases by company ID test failed");
					break;
				}
			}
			if (isId1) {
				System.out.println("Purchases were extracted by company ID");
			}
			companiesController.deleteCompany(companyId1);
			companiesController.deleteCompany(companyId2);
			usersController.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	} 


	private static void testGetPurchasesByUser() {

		long startMillis = new GregorianCalendar(2037, 8, 19).getTimeInMillis();
		long endMillis = new GregorianCalendar(2200, 1, 12).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			long companyId = companiesController.createCompany(new Company("Cerberus", "all.for.humanity@holonet.com", "264-45545-455", "Unknown"));
			long couponId = couponsController.createCoupon(new Coupon(companyId, Category.VACATION, "Sanctuary", "A safe place for all your family", startDate, endDate, 150000, 12000, "ESCAPEPOD.IMAGE"));
			long userId1 = usersController.createUser(new User("Overlord@holonet.com", "ItAllSeemdHarm1ess", UserType.CUSTOMER, null, "David", "Archer"));
			long userId2 = usersController.createUser(new User("Ori@holonet.com", "st0pItMiranda", UserType.CUSTOMER, null, "Oriana", "Lawson"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesController.createPurchase(purchase1);
			List<Purchase> purchases = purchasesController.getPurchasesByUser(userId1);
			boolean isId1=true;
			for (Purchase purchase : purchases) {
				if(purchase.getUserId()!=userId1) {
					isId1 = false;
					System.out.println("Get purchases by user ID test failed");
					break;
				}
			}
			if (isId1) {
				System.out.println("Purchases were extracted by user ID");
			}
			companiesController.deleteCompany(companyId);
			usersController.deleteUser(userId1);
			usersController.deleteUser(userId2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
