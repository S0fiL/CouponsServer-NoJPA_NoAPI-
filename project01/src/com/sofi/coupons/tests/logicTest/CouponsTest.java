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

public class CouponsTest {

	private static CouponsController couponsCon = new CouponsController();
	private static CompaniesController companiesCon = new CompaniesController();
	private static UsersController usersCon = new UsersController();
	private static PurchasesController purchasesCon = new PurchasesController();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		testCreateCoupon();
//		testGetCoupon();
//		testUpdateCoupon();
//		testDeleteCoupon();
//		testGetAllCoupons();
//		testGetCouponsByCompany();
//		testGetCouponsByCategory();
//		testGetPurchasedCouponsByMaxPrice();

	}

	private static void testCreateCoupon() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		System.out.println("Title test: "); 
		System.out.print("Title already exists: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
				System.out.println("Title already exists test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Invalid: ");
		try {
			long companyId = companiesCon.createCompany(new Company("bbbb", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, null, null, startDate, endDate, 2, (float)0.01, null));
				System.out.println("Invalid title test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nInvalid company ID test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("cccc", "a@a.aaa", "111111", "a"));
			companiesCon.deleteCompany(companyId);
			couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "bbb", null, startDate, endDate, 2, (float)0.01, null));
			System.out.println("Invalid company ID test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Description too Long test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("dddd", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "eee", "cdfsdfdsfsdfdsfsdcdsvdsvsdvdsfdsfdsfksdfsdfgsdjgkdshfjjbsdjkfghjghsbdfgsdkjhjkfshdkjkhsjdkfjkbjjkgdsjfbbjkjbskdfnjksndfjkdfdsjhgjfkdjfngjfkvnfjgmhjgkff", startDate, endDate, 2, (float)0.01, null));
				System.out.println("Invalid description test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nInvalid end date test: ");
		Calendar InvalidEnd = new GregorianCalendar(2020, 10, 16);
		java.sql.Date InvalidEndDate = new java.sql.Date(InvalidEnd.getTimeInMillis());
		try {
			long companyId = companiesCon.createCompany(new Company("eeee", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "fff", null, startDate, InvalidEndDate, 2, (float)0.01, null));
				System.out.println("Invalid end date test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nInvalid amount test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("ffff", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "ggg", null, startDate, endDate, -12, (float)0.01, null));
				System.out.println("Invalid amount test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nInvalid price test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("gggg", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "hhh", null, startDate, endDate, 2, (float)-0.01, null));
				System.out.println("Invalid price test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nImage link too long test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("hhhh", "a@a.aaa", "111111", "a"));
			try {
				couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "iii", null, startDate, endDate, 2, (float)0.01, "cdfsdfdsfsdfdsfsdcdsvdsvsdvdsfdsfdsfsdfsdfgsdjgkdshfjjbsdjkfghjghsbdfgsdkjhjkfshdkjkhsjdkfjkbjjkgdsjfbbjkjbskdfnjksndfjkdfdsjhgjfkdjfngjfkvnfjgmhjgkffcdfsdfdsfsdfdsfsdcdsvdsvsdvdsfdsfdsfsdfsdfgsdjgkdshfjjbsdjkfghjghsbdfgsdkjhjkfshdkjkhsjdkfjkbjjkgdsjfbbjkjbskdfnjksndfjkd"));
				System.out.println("Invalid image link test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nValid coupon test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("iiii", "a@a.aaa", "111111", "a"));
			Coupon coupon = new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj");
			long id = couponsCon.createCoupon(coupon);
			if(couponsCon.getCoupon(id) != null) {
				System.out.println("Coupon created successfully");
			} else {
				System.out.println("Coupon creation test failed");
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCoupon() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		try {
			long companyId = companiesCon.createCompany(new Company("iiii", "a@a.aaa", "111111", "a"));
			Coupon coupon = new Coupon(companyId, Category.VIDEO_GAMES, "jjj", null, startDate, endDate, 2, (float)0.01, "dhj");
			long id = couponsCon.createCoupon(coupon);
			Coupon coupon2 = couponsCon.getCoupon(id);
			if(coupon.getTitle().equals(coupon2.getTitle()) && coupon.getCompanyId() == coupon2.getCompanyId()
					&& coupon.getDescription() == (coupon2.getDescription()) && coupon.getCategory() == coupon2.getCategory()
					&& coupon.getAmount() == coupon2.getAmount() && coupon.getPrice() == coupon2.getPrice() 
					//&& coupon.getStartDate().equals(coupon2.getStartDate()) && coupon.getEndDate().equals(coupon2.getEndDate()) 
					&& coupon.getImage().equals(coupon2.getImage()))
				System.out.println("Coupon created successfully");
			else
				System.out.println("Coupon creation test failed");
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testUpdateCoupon() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		System.out.print("Invalid data test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			try {
				long id = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
				Coupon coupon = couponsCon.getCoupon(id);
				coupon.setPrice(-2);
				couponsCon.updateCoupon(coupon);
				System.out.println("Invalid data update test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Invalid coupon test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			try {
				long id = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
				Coupon coupon = couponsCon.getCoupon(id);
				couponsCon.deleteCoupon(id);
				coupon.setAmount(5);
				couponsCon.updateCoupon(coupon);
				System.out.println("Invalid coupon update test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Valid coupon test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			long id = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
			Coupon coupon = couponsCon.getCoupon(id);
			coupon.setAmount(5);
			couponsCon.updateCoupon(coupon);
			Coupon coupon2 = couponsCon.getCoupon(id);
			if(coupon.getAmount() == coupon2.getAmount()){
				System.out.println("Coupon updated successfully");
			} else {
				System.out.println("Invalid coupon update test failed");
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testDeleteCoupon() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		System.out.print("Invalid coupon test: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			try {
				long id = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
				couponsCon.deleteCoupon(id);
				couponsCon.deleteCoupon(id);
				System.out.println("Invalid coupon delete test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Valid coupon: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			long id = couponsCon.createCoupon(new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null));
			couponsCon.deleteCoupon(id);
			if(couponsCon.getCoupon(id) == null) {
				System.out.println("Coupon deleted successfully");
			} else {
				System.out.println("Coupon delete test failed");
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetAllCoupons() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			Coupon coupon = new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null);
			couponsCon.createCoupon(coupon);
			List<Coupon> coupons = couponsCon.getAllCoupons();
			if(!coupons.isEmpty()) {
				System.out.println("Coupons list was created");
			} else {
				System.out.println("Get all coupons test failed");
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}


	private static void testGetCouponsByCompany() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		System.out.print("Invalid company ID: ");
		try {
			Company company = new Company("aaaa", "a@a.aaa", "111111", "a");
			long companyId1 = companiesCon.createCompany(company);
			companiesCon.deleteCompany(companyId1);
			List<Coupon> coupons = couponsCon.getCouponsByCompany(companyId1);
			System.out.println("Invalid company ID test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			long companyId = companiesCon.createCompany(new Company("aaaa", "a@a.aaa", "111111", "a"));
			Coupon coupon = new Coupon(companyId, Category.VIDEO_GAMES, "aaa", null, startDate, endDate, 2, (float)0.01, null);
			couponsCon.createCoupon(coupon);
			List<Coupon> coupons = couponsCon.getAllCoupons();
			if(!coupons.isEmpty()) {
				System.out.println("Coupons list was created");
			} else {
				System.out.println("Get coupons by company test failed");
			}
			companiesCon.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}


	private static void testGetCouponsByCategory() {

		Calendar start = new GregorianCalendar(2020, 11, 01);
		Calendar end = new GregorianCalendar(2021, 00, 01);
		Date startDate = new Date(start.getTimeInMillis());
		Date endDate = new Date(end.getTimeInMillis());

		try {
			Company company = new Company("aaaa", "a@a.aaa", "111111", "a");
			long companyId = companiesCon.createCompany(company);
			Category category1 = Category.TECHNOLOGY;
			Category category2 = Category.SPORTS;
			Coupon coupon1 = new Coupon(companyId, category1, "aaa", null, startDate, endDate, 2, (float)0.01, null);
			Coupon coupon2 = new Coupon(companyId, category2, "bbb", null, startDate, endDate, 2, (float)0.01, null);
			couponsCon.createCoupon(coupon1);
			couponsCon.createCoupon(coupon2);
			List<Coupon> coupons = couponsCon.getCouponsByCategory(category1);
			boolean isCategory1 = true;
			for (Coupon coupon : coupons) {
				if(coupon.getCategory()!=category1) {
					isCategory1 = false;
					System.out.println("Get coupons by category test failed");
					break;
				}
			}
			if(isCategory1) {
				System.out.println("The coupons were extracted by category");
				companiesCon.deleteCompany(companyId);
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
		
		System.out.print("Invalid price: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aa", "111", "aaa"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VACATION, "aaa", "aaa", startDate, endDate, 5, 100, null));
			long userId1 = usersCon.createUser(new User("a@a.aa", "aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase = new Purchase(userId1, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase);
			float maxPrice = -50;
			try {
				List<Coupon> coupons = couponsCon.getPurchasedCouponsByMaxPrice(userId1, maxPrice);
			} catch (Exception e){
				System.out.println(e.getMessage());
			}	
			companiesCon.deleteCompany(companyId);
			usersCon.deleteUser(userId1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Valid user and max price: ");
		try {
			long companyId = companiesCon.createCompany(new Company("aaa", "a@a.aa", "111", "aaa"));
			long couponId = couponsCon.createCoupon(new Coupon(companyId, Category.VACATION, "aaa", "aaa", startDate, endDate, 5, 100, null));
			long couponId2 = couponsCon.createCoupon(new Coupon(companyId, Category.VACATION, "bbb", "aaa", startDate, endDate, 5, 101, null));
			long userId1 = usersCon.createUser(new User("a@a.aa", "aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			long userId2 = usersCon.createUser(new User("b@b.bb", "aaaa1111", UserType.CUSTOMER, null, "aaa", "aaa"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			purchase1 = new Purchase(userId1, couponId2, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId2, 1, timestamp);
			purchasesCon.createPurchase(purchase1);
			float maxPrice = 100;
			List<Coupon> coupons = couponsCon.getPurchasedCouponsByMaxPrice(userId1, maxPrice);
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
					System.out.println("The coupons were extracted by user ID and max price");
				}
				companiesCon.deleteCompany(companyId);
				usersCon.deleteUser(userId1);
				usersCon.deleteUser(userId2);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

