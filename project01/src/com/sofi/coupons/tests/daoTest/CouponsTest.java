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

public class CouponsTest {

	private static PurchasesDao purchasesDao = new PurchasesDao();
	private static CouponsDao couponsDao = new CouponsDao();
	private static CompaniesDao companiesDao = new CompaniesDao();
	private static UsersDao usersDao = new UsersDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//						testCreateCoupon();
		//						testGetCoupon();
		//						testUpdateCoupon();
		//						testDeleteCoupon();
		//						testGetAllCoupons();
		//						testGetCouponsByCompany();
		//						testGetCouponsByCategory();
		//						testIsCouponTitleExists();
		//		                testGetPurchasedCouponsByMaxPrice();

	} 


	public static void testCreateCoupon() {

		long startMillis = new GregorianCalendar(2000, 01, 01).getTimeInMillis();
		long endMillis = new GregorianCalendar(3000, 11, 30).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Best Company", "goodBoi@pets.paw", "10-100-101010", "Heaven");
			long companyId = companiesDao.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.FOR_PETS, "The whole world", "everything your pet ever wanted", startDate, endDate, 10000, (float)00.00, "CUTE.DOGOS.AND.CATS.IMAGE");
			long id = couponsDao.createCoupon(coupon);
			if(id != 0) {
				System.out.println("Coupon was created by the id - "+id);
			} else {
				System.out.println("Coupon creation test failed");
			}
			companiesDao.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCoupon() {

		long startMillis = new GregorianCalendar(2003, 06, 25).getTimeInMillis();
		long endMillis = new GregorianCalendar(2042, 00, 01).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Cyberdyne Systems", "skynet@corpor.com", "800-1000-3000", "18144 Al Camino Real, Sunnyvale, California");
			long companyId = companiesDao.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "Judgement day", "How about a machine revolution?", startDate, endDate, 1, (float)123.00, "APOCALYPSE.IMAGE");
			long id = couponsDao.createCoupon(coupon);
			Coupon coupon2 = couponsDao.getCoupon(id);
			System.out.println(coupon.getStartDate()+" "+coupon2.getStartDate()+"\n"+coupon.getEndDate()+" "+coupon2.getEndDate());
			if(coupon.getCompanyId()==(coupon2.getCompanyId()) && coupon.getCategory().equals(coupon2.getCategory())
					&& coupon.getTitle().equals(coupon2.getTitle()) && coupon.getDescription().equals(coupon2.getDescription())
					&& coupon.getStartDate().equals(coupon2.getStartDate()) && coupon.getEndDate().equals(coupon2.getEndDate())
					&& coupon.getAmount()==coupon2.getAmount() && coupon.getPrice()==coupon2.getPrice() 
					&& coupon.getImage().equals(coupon2.getImage())) {
				System.out.println("The coupon was extracted");
			} else {
				System.out.println("Get coupon test failed");
			}
			companiesDao.deleteCompany(companyId);
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
			long companyId = companiesDao.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.TECHNOLOGY, "For Science", "Become a test subject for an unknow period of time", startDate, endDate, 10000, (float)50.99, "DEER.IMAGE");
			long id = couponsDao.createCoupon(coupon);
			coupon = couponsDao.getCoupon(id);
			coupon.setTitle("For cake");
			couponsDao.updateCoupon(coupon);
			Coupon coupon2 = couponsDao.getCoupon(id);

			if(coupon.getCompanyId()==(coupon2.getCompanyId()) && coupon.getCategory().equals(coupon2.getCategory())
					&& coupon.getTitle().equals(coupon2.getTitle()) && coupon.getDescription().equals(coupon2.getDescription())
					&& coupon.getStartDate().equals(coupon2.getStartDate()) && coupon.getEndDate().equals(coupon2.getEndDate())
					&& coupon.getAmount()==coupon2.getAmount() && coupon.getPrice()==coupon2.getPrice() 
					&& coupon.getImage().equals(coupon2.getImage())) {
				System.out.println("The coupon was updated");
			} else {
				System.out.println("Coupon update test failed");
			}
			//			companiesDao.deleteCompany(companyId);
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
			Company company = new Company("Nuka-cola", "nuka.cola@quantum.com", "10-2076-2044", "United states of america");
			long companyId = companiesDao.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.FOOD, "A blast of tastes", "50% sale on all drink variants", startDate, endDate, 1000000, (float)523.80, "LOGO.IMAGE");
			long id = couponsDao.createCoupon(coupon);
			couponsDao.deleteCoupon(id);
			if(couponsDao.getCoupon(id)==null) {
				System.out.println("Coupon was deleted");
			} else {
				System.out.println("Delete coupon test failed");
			}
			companiesDao.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	} 


	private static void testGetAllCoupons() {

		long startMillis = new GregorianCalendar(1996, 1, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(1999, 1, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Paper street Soap co.", "all.natural@soap.com", "(285)555-0153", "537 Paper Street. Bradford");
			long companyId = companiesDao.createCompany(company);
			Coupon coupon = new Coupon(companyId, Category.HYGIENE, "Feel like yourself again", "20% sale on all handmade products" , startDate, endDate, 20, (float)20.99, "SOAP.IMAGE");
			couponsDao.createCoupon(coupon);
			List<Coupon> coupons = couponsDao.getAllCoupons();
			if(!coupons.isEmpty()) {
				System.out.println("Coupons list was created");
			} else {
				System.out.println("Get all coupons test failed");
			}
			companiesDao.deleteCompany(companyId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
	}


	private static void testGetCouponsByCompany() {

		long startMillis1 = new GregorianCalendar(1995, 4, 20).getTimeInMillis();
		long endMillis1 = new GregorianCalendar(2020, 3, 17).getTimeInMillis();
		long startMillis2 = new GregorianCalendar(1980, 5, 23).getTimeInMillis();
		long endMillis2 = new GregorianCalendar(2003, 0, 1).getTimeInMillis();
		Date startDate1 = new Date(startMillis1);
		Date endDate1 = new Date(endMillis1);
		Date startDate2 = new Date(startMillis2);
		Date endDate2 = new Date(endMillis2);

		try {
			Company company = new Company("Monster Inc.", "mon.inc@scares.com", "22-222-22", "Monstropolis");
			Company company2 = new Company("Umbrella Corporation", "umbrella.phrma@corp.com", "99-999-99", "Britain, Racoon city");
			long companyId1 = companiesDao.createCompany(company);
			long companyId2 = companiesDao.createCompany(company2);
			Coupon coupon1 = new Coupon(companyId1, Category.FOR_KIDS, "Scares", "1 week visits", startDate1, endDate1, 12, (float)660.6, "MONSTER.IMAGE");
			Coupon coupon2 = new Coupon(companyId2, Category.HYGIENE, "Surprise in a flask", "Daily flask supply with unknow substance", startDate2,endDate2, 3000, (float)3299.99, "MONSTER.IMAGE");
			couponsDao.createCoupon(coupon1);
			couponsDao.createCoupon(coupon2);
			List<Coupon> coupons = couponsDao.getCouponsByCompany(companyId1);
			boolean isCompanyId1=true;
			for (Coupon coupon : coupons) {
				if(coupon.getCompanyId()!=companyId1) {
					isCompanyId1 = false;
					System.out.println("Get coupons by company ID test failed");
					break;
				}   
			}
			if (isCompanyId1) {
				System.out.println("The coupons were extracted by company ID");
			}
			companiesDao.deleteCompany(companyId1);
			companiesDao.deleteCompany(companyId2);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}


	}


	private static void testGetCouponsByCategory() {

		long startMillis1 = new GregorianCalendar(1990, 2, 10).getTimeInMillis();
		long endMillis1 = new GregorianCalendar(2000, 10, 10).getTimeInMillis();
		long startMillis2 = new GregorianCalendar(1990, 2, 10).getTimeInMillis();
		long endMillis2 = new GregorianCalendar(2000, 10, 10).getTimeInMillis();
		Date startDate1 = new Date(startMillis1);
		Date endDate1 = new Date(endMillis1);
		Date startDate2 = new Date(startMillis2);
		Date endDate2 = new Date(endMillis2);

		try {
			Company company = new Company("Wayne Enterprises", "WayneCorp@totalynotbatman.com", "307-1979", "America, Gotham City");
			long companyId = companiesDao.createCompany(company);
			Category category1 = Category.TECHNOLOGY;
			Category category2 = Category.SPORTS;
			Coupon coupon1 = new Coupon(companyId, category1, "Batvehicles", "30% on all Batmobiles ang Batcycles", startDate1, endDate1, 200, (float)100000.00, "BATLOGO.IMAGE");
			Coupon coupon2 = new Coupon(companyId, category2, "Batequipment", "30% on all Batequipment for Batworkouts", startDate2, endDate2, 200, (float)30000.00, "BATLOGO.IMAGE");
			couponsDao.createCoupon(coupon1);
			couponsDao.createCoupon(coupon2);
			List<Coupon> coupons = couponsDao.getCouponsByCategory(category1);

			boolean isCategory1 = true;
			for (Coupon coupon : coupons) {
				if(coupon.getCategory()!=category1) {
					isCategory1 = false;
					System.out.println("Get coupons by category test failed");
					break;
				}
				if(isCategory1) {
					System.out.println("The coupons were extracted by category");
				}
				companiesDao.deleteCompany(companyId);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}    


	private static void testIsCouponTitleExists() {

		long startMillis = new GregorianCalendar(2130, 5, 1).getTimeInMillis();
		long endMillis = new GregorianCalendar(2230, 5, 1).getTimeInMillis();
		Date startDate = new Date(startMillis);
		Date endDate = new Date(endMillis);

		try {
			Company company = new Company("Starfleet", "star.fleet@united.com", "1701", "San Francisco, United Earth");
			long companyId = companiesDao.createCompany(company);
			String title = "Journey through stars";
			Coupon coupon = new Coupon(companyId, Category.VACATION, title, "Explore the galaxy on Enterprise Starship", startDate, endDate, 3, 1000000000, "GALAXY.IMAGE");
			couponsDao.createCoupon(coupon);
			if(couponsDao.isCouponTitleExists(title, companyId)) {
				System.out.println("Coupon title was identified");
			} else {
				System.out.println("Is title exists test failed");
			}
			companiesDao.deleteCompany(companyId);
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
			long companyId = companiesDao.createCompany(new Company("Cerberus", "all.for.humanity@holonet.com", "264-45545-455", "Unknown"));
			long couponId = couponsDao.createCoupon(new Coupon(companyId, Category.VACATION, "Sanctuary", "A safe place for all your family", startDate, endDate, 150000, 12000, "ESCAPEPOD.IMAGE"));
			long couponId2 = couponsDao.createCoupon(new Coupon(companyId, Category.VACATION, "Sanctuary2", "A safer place for all your family", startDate, endDate, 150000, 20000, "ESCAPEPOD.IMAGE"));
			long userId1 = usersDao.createUser(new User("Overlord", "ItAllSeemdHarmless", UserType.CUSTOMER, companyId, "David", "Archer"));
			long userId2 = usersDao.createUser(new User("Ori", "stopItMiranda", UserType.CUSTOMER, companyId, "Oriana", "Lawson"));
			Purchase purchase1 = new Purchase(userId1, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			purchase1 = new Purchase(userId1, couponId2, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			purchase1 = new Purchase(userId2, couponId2, 1, timestamp);
			purchasesDao.createPurchase(purchase1);
			float maxPrice = 13000;
			List<Coupon> coupons = couponsDao.getPurchasedCouponsByMaxPrice(userId1, maxPrice);
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
					System.out.println("The purchases were extracted by user ID and max price");
				}
				companiesDao.deleteCompany(companyId);
				usersDao.deleteUser(userId1);
				usersDao.deleteUser(userId2);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
