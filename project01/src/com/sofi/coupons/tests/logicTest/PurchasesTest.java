package com.sofi.coupons.tests.logicTest;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.beans.Purchase;
import com.sofi.coupons.beans.User;
import com.sofi.coupons.enums.Category;
import com.sofi.coupons.enums.UserType;
import com.sofi.coupons.logic.CompaniesController;
import com.sofi.coupons.logic.CouponsController;
import com.sofi.coupons.logic.PurchasesController;
import com.sofi.coupons.logic.UsersController;

public class PurchasesTest {

	private static CouponsController couponsCon = new CouponsController();
	private static CompaniesController companiesCon = new CompaniesController();
	private static UsersController usersCon = new UsersController();
	private static PurchasesController purchasesCon = new PurchasesController();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//				testCreatePurchase();
//				testGetPurchase();
//				testDeletePurchase();
//				testGetAllPurchases();
//				testGetPurchasesByCompany();
//				testGetPurchasesByUser();

	}

	private static void testCreatePurchase() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		System.out.print("Invalid coupon ID test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			couponsCon.deleteCoupon(couponId);
			try {
				Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
				purchasesCon.createPurchase(purchase);
				System.out.println("Invalid coupon ID test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		System.out.print("Sold out coupon test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			try {
				Purchase purchase = new Purchase(userId, couponId, 4, timestamp);
				purchasesCon.createPurchase(purchase);
				System.out.println("Sold out test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}			
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Valid purchase test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			int couponAmount = 4;
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, couponAmount, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			int purchaseAmount = 2;
			Purchase purchase = new Purchase(userId, couponId, purchaseAmount, timestamp);
			long id = purchasesCon.createPurchase(purchase);
			int newAmount = couponsCon.getCoupon(couponId).getAmount();
			if(purchasesCon.getPurchase(id) != null && newAmount == couponAmount-purchaseAmount) {
				System.out.println("Purchase created successfully");
			} else {
				System.out.println("Create purchase test failed");
			}
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetPurchase() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesCon.createPurchase(purchase);
			Purchase purchase2 = purchasesCon.getPurchase(id);
			if(purchase.getUserId() == purchase2.getUserId() && purchase.getCouponId() == purchase2.getCouponId() && purchase.getAmount() == purchase2.getAmount()) {
				System.out.println("Purchase extracted successfully");
			} else {
				System.out.println("Get purchase test failed");
			}
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testDeletePurchase() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		System.out.print("Valid purchase test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			long id = purchasesCon.createPurchase(purchase);;
			purchasesCon.deletePurchase(id);
			if(purchasesCon.getPurchase(id) == null) {
				System.out.println("Purchase deleted successfully");
			} else {
				System.out.println("Delete purchase test failed");
			}
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}

	private static void testGetAllPurchases() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);


		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase = new Purchase(userId, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase);
			List<Purchase> purchases = purchasesCon.getAllPurchases();
			if(!purchases.isEmpty()) {
				System.out.println("Purchases list created successfully");
			} else {
				System.out.println("Get all purchases test failed");
			}
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}

	private static void testGetPurchasesByCompany() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		System.out.print("Valid company test: ");
		try {
			long companyId1 = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long companyId2 = companiesCon.createCompany(new Company("bbb", "b@b.bb", "111111", "a"));
			long couponId1 = couponsCon.createCoupon(new Coupon(companyId1, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long couponId2 = couponsCon.createCoupon(new Coupon(companyId2, Category.VIDEO_GAMES, "bbb", null, startDate, endDate, 2, (float)0.01, "dhj"));
			long userId = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase1 = new Purchase(userId, couponId1, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			purchase1 = new Purchase(userId, couponId2, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			try {
				List<Purchase> purchases = purchasesCon.getPurchasesByCompany(companyId1);
				boolean isId1 = true;
				for (Purchase purchase : purchases) {
					if(purchase.getCouponId() != couponId1){
						isId1 = false;
						System.out.println("Get purchases by company ID failed");
						break;
					}
				}
				if(isId1) {
					System.out.println("Purchases extracted by company ID");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
			companiesCon.deleteCompany(companyId1);
			companiesCon.deleteCompany(companyId2);
			usersCon.deleteUser(userId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}

	private static void testGetPurchasesByUser() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());
		long now = Calendar.getInstance().getTimeInMillis();
		Timestamp timestamp = new Timestamp(now);

		System.out.print("Valid user test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aaa", "111111", "a"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 4, (float)0.01, "dhj"));
			long userId1 = usersCon.createUser(new User("a@a.aa", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			long userId2 = usersCon.createUser(new User("b@b.bb", "Aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			try {
				List<Purchase> purchases = purchasesCon.getPurchasesByUser(userId1);
				boolean isId1 = true;
				for (Purchase purchase : purchases) {
					if(purchase.getUserId() != userId1){
						isId1 = false;
						System.out.println("Get purchases by user ID failed");
						break;
					}
				}
				if(isId1) {
					System.out.println("Purchases extracted by user ID");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}	
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId1);
			usersCon.deleteUser(userId2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}
}


