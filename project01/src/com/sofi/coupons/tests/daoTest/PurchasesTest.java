package com.sofi.coupons.tests.daoTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.beans.Purchase;
import com.sofi.coupons.beans.User;
import com.sofi.coupons.dao.CompaniesDao;
import com.sofi.coupons.dao.CouponsDao;
import com.sofi.coupons.dao.PurchasesDao;
import com.sofi.coupons.dao.UsersDao;
import com.sofi.coupons.enums.Category;
import com.sofi.coupons.enums.UserType;

public class PurchasesTest {

	private static CompaniesDao companiesDao = new CompaniesDao();
	private static UsersDao usersDao = new UsersDao();
	private static CouponsDao couponsDao = new CouponsDao();
	private static PurchasesDao purchasesDao = new PurchasesDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		testCreatePurchase();
//		testGetPurchase();
//		testDeletePurchase();
//		testGetAllPurchases();
//		testGetPurchasesByCompany();
//		testGetPurchasesByUser();

	}  

	private static void testCreatePurchase() {

		long startMillis = new GregorianCalendar(2002, 0, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2004, 0, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			Company company = new Company("Gorsvet", "day-watch@gov.com", "(495)781-16-46", "Moscow, Vasily Petuchkov st 8");
			long companyId = companiesDao.createCompany(company);
			User user = new User("Vampire", "everyoneIsEqual", UserType.CUSTOMER, null, "Kostya", "Saushkin");
			long userId = usersDao.createUser(user);
			int couponsAmount = 20;
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "Fuaran", "Make everyone equal", startDate, endDate, couponsAmount, 100000000, "TEXT.IMAGE");
			long couponId = couponsDao.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesDao.createPurchase(purchase);
			int newAmount = couponsDao.getCoupon(couponId).getAmount();
			if(id !=0 && newAmount == couponsAmount - purchase.getAmount()) {
				System.out.println("Purchase was created by the id - " + id);		
			} else {
				System.out.println("Purchase creation test failed");
			}
			companiesDao.deleteCompany(companyId);
			usersDao.deleteUser(userId);
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
			long companyId = companiesDao.createCompany(company);
			User user = new User("Demoman@team.red", "b000000m", UserType.CUSTOMER, null, "Scottish", "Cyclops");
			long userId = usersDao.createUser(user);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "boom", "evenmoreboom", startDate, endDate, 10000, (float)6000.00, "");
			long couponId = couponsDao.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase);
			if(couponId == purchase.getCouponId() && userId == purchase.getUserId() && purchase.getAmount() == purchase.getAmount()) {
				System.out.println("Purchase was extracted by ID");
			} else {
				System.out.println("Get purchase test failed");
			}
			companiesDao.deleteCompany(companyId);
			usersDao.deleteUser(userId);
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
			Company company = new Company("Blue Suns", "blue.suns@omega.com", "20-320-3230", "Omega Slums");
			long companyId = companiesDao.createCompany(company);
			User user = new User("Patriarch", "myPack", UserType.CUSTOMER, null, "", "");
			long userId = usersDao.createUser(user);
			Coupon coupon = new Coupon(companyId, Category.VACATION, "Peace and quite", "You will live another day", startDate, endDate, 10000000, 500000, "BATARIAN.IMAGE");
			long couponId = couponsDao.createCoupon(coupon);
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesDao.createPurchase(purchase);
			purchasesDao.deletePurchase(id);
			if(purchasesDao.getPurchase(id)==null) {
				System.out.println("Purchase was deleted");
			} else {
				System.out.println("Delete purchase test failed");
			}
			companiesDao.deleteCompany(companyId);
			usersDao.deleteUser(userId);
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
			long companyId = companiesDao.createCompany(new Company("US Robotics", "USR.corp@UNICOM.com", "295-3030-9653", "Schaumburg, Illinois, U.S."));
			long userId = usersDao.createUser(new User("Spooner@chicago.pd", "DRObOt5Dream", UserType.CUSTOMER, null, "Del", "Spooner"));
			long couponId = couponsDao.createCoupon(new Coupon(companyId, Category.TECHNOLOGY, "Helper bot", "Buy a set of house helpers", startDate, endDate, 50000, 5000000, "I.IMAGE"));
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase);
			List<Purchase> Purchases = purchasesDao.getAllPurchases();
			if(!Purchases.isEmpty()) {
				System.out.println("Purchases list was created");
			} else {
				System.out.println("Get all purchases test failed");
			}
			companiesDao.deleteCompany(companyId);
			usersDao.deleteUser(userId);
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
			long companyId1 = companiesDao.createCompany(new Company("Tedior", "all.for.humanity@vaults.com", "145-5643315", "Pandora"));
			long companyId2 = companiesDao.createCompany(new Company("Torgue", "BLOWIT@vaults.com", "154-4565545", "Pandora"));
			long couponId1 = couponsDao.createCoupon(new Coupon(companyId1, Category.TECHNOLOGY, "Best equipment", "50% on all mods", startDate1, endDate1, 12, (float)660.6, "LOGO.IMAGE"));
			long couponId2 = couponsDao.createCoupon(new Coupon(companyId2, Category.TECHNOLOGY, "Gold Collection", "20% on all legendary weapons", startDate2, endDate2, 3000, (float)3299.99, "LOGO.IMAGE"));
			long userId = usersDao.createUser(new User ("Zero", "ABiggerNumberThanU", UserType.CUSTOMER, null, "", ""));
			Purchase purchase1 = new Purchase(userId, couponId1, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			purchase1 = new Purchase(userId, couponId2, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			List<Purchase> purchases = purchasesDao.getPurchasesByCompany(companyId1);
			boolean isId1=true;
			for (Purchase purchase : purchases) {
				if(couponsDao.getCoupon(purchase.getCouponId()).getCompanyId()!= companyId1) {
					isId1 = false;
					System.out.println("Get purchases by company ID test failed");
					break;
				}
			}
			if (isId1) {
				System.out.println("The purchases were extracted by company ID");
			}
			companiesDao.deleteCompany(companyId1);
			companiesDao.deleteCompany(companyId2);
			usersDao.deleteUser(userId);
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
			long companyId = companiesDao.createCompany(new Company("Cerberus", "all.for.humanity@holonet.com", "264-45545-455", "Unknown"));
			long couponId = couponsDao.createCoupon(new Coupon(companyId, Category.VACATION, "Sanctuary", "A safe place for all your family", startDate, endDate, 150000, 12000, "ESCAPEPOD.IMAGE"));
			long userId1 = usersDao.createUser(new User("Overlord", "ItAllSeemdHarmless", UserType.CUSTOMER, companyId, "David", "Archer"));
			long userId2 = usersDao.createUser(new User("Ori", "stopItMiranda", UserType.CUSTOMER, companyId, "Oriana", "Lawson"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			List<Purchase> purchases = purchasesDao.getPurchasesByUser(userId1);
			boolean isId1=true;
			for (Purchase purchase : purchases) {
				if(purchase.getUserId()!=userId1) {
					isId1 = false;
					System.out.println("Get purchases by user ID test failed");
					break;
				}
			}
			if (isId1) {
				System.out.println("The purchases were extracted by user ID");
			}
			companiesDao.deleteCompany(companyId);
			usersDao.deleteUser(userId1);
			usersDao.deleteUser(userId2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
