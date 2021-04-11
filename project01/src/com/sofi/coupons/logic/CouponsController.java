package com.sofi.coupons.logic;

import java.util.Calendar;
import java.util.List;

import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.dao.CouponsDao;
import com.sofi.coupons.enums.Category;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;

public class CouponsController {

	private CompaniesController companiesController;
	private CouponsDao couponsDao;

	public CouponsController() {
		companiesController = new CompaniesController();
		couponsDao = new CouponsDao();
	}

	///////////////////////////////////////////////
	//Public methods that call validation methods//
	///////////////////////////////////////////////

	public long createCoupon(Coupon coupon) throws ApplicationException {
		createCouponValidation(coupon);
		return couponsDao.createCoupon(coupon);
	}


	public Coupon getCoupon(long id) throws ApplicationException {
		Coupon coupon = couponsDao.getCoupon(id);
		if(coupon == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid coupon ID: " + id);
		}
		return coupon;
	}


	public void updateCoupon(Coupon coupon) throws ApplicationException {
		updateCouponValidation(coupon);
		couponsDao.updateCoupon(coupon);
	}

	public void deleteCoupon (long id) throws ApplicationException {
		couponsDao.deleteCoupon(id);
	}


	public List<Coupon> getAllCoupons() throws ApplicationException{
		return couponsDao.getAllCoupons();
	}


	public List<Coupon> getCouponsByCategory(Category category) throws ApplicationException{
		return couponsDao.getCouponsByCategory(category);
	}


	public List<Coupon> getCouponsByCompany(long companyId) throws ApplicationException{
		return couponsDao.getCouponsByCompany(companyId);
	}
	
	public List<Coupon> getPurchasedCouponsByMaxPrice(long userId, float maxPrice) throws ApplicationException{
		getPurchasedCouponsByMaxPriceValidation(userId, maxPrice);
		return couponsDao.getPurchasedCouponsByMaxPrice(userId, maxPrice);
	}

	//////////////////////////////
	//Private validation methods//
	//////////////////////////////

	private void createCouponValidation(Coupon coupon) throws ApplicationException {

		//Check if all the data is valid
		couponInfoValidation(coupon);	
		
		//Start date can't be before the coupon creation date
		if(coupon.getStartDate().before(new java.sql.Date(Calendar.getInstance().getTimeInMillis()))) {
			throw new ApplicationException(ExceptionType.INVALID_DATE, "Invalid start date: " + coupon.getStartDate());
		}
		//Can't create two coupons with the same title for the same company
		if(couponsDao.isCouponTitleExists(coupon.getTitle(), coupon.getCompanyId())) {
			throw new ApplicationException(ExceptionType.TITLE_ALREADY_EXISTS, "Title already exists: " + coupon.getTitle());
		}
	}


	private void updateCouponValidation(Coupon coupon) throws ApplicationException {

		//Check if all the data is valid
		couponInfoValidation(coupon);
		
		//Get coupon's original info 
		Coupon oldCoupon = couponsDao.getCoupon(coupon.getId());
		//Can't update company ID, can't decrease coupons amount
		if(coupon.getCompanyId() != oldCoupon.getCompanyId() || oldCoupon.getAmount() > coupon.getAmount()) {
			throw new ApplicationException(ExceptionType.INVALID_UPDATE, "Invalid coupon update: " + coupon.toString());
		}
		//If updating coupon title, need to check if the new title isn't already taken
		if(!coupon.getTitle().equals(oldCoupon.getTitle()) && couponsDao.isCouponTitleExists(coupon.getTitle(), coupon.getCompanyId())) {
			throw new ApplicationException(ExceptionType.TITLE_ALREADY_EXISTS, "New coupon title is already taken: " + coupon.getTitle());
		}
		//Can't shorten coupon validity period
		if(coupon.getStartDate().after(oldCoupon.getStartDate()) || coupon.getEndDate().before(oldCoupon.getEndDate())) {
			throw new ApplicationException(ExceptionType.INVALID_DATE_UPDATE, "Invalid date updates: "+coupon.toString());
		}
		//Start date can't be updated to a date before the update date (!!!equals instead of before!!!)
		if(!coupon.getStartDate().equals(oldCoupon.getStartDate()) && coupon.getStartDate().before(Calendar.getInstance().getTime())) {
			throw new ApplicationException(ExceptionType.INVALID_DATE, "Invalid date: " + coupon.getStartDate());
		}
	}
	
	
	private void getPurchasedCouponsByMaxPriceValidation(long userId, float maxPrice) throws ApplicationException {

		//Price has to be positive
		if(maxPrice<=0) {
			throw new ApplicationException(ExceptionType.INVALID_PRICE, "Invalid price: "+maxPrice);
		}
	}


	private void couponInfoValidation(Coupon coupon) throws ApplicationException {

		//Title has to contain between 1 and 45 characters
		if(coupon.getTitle()==null || coupon.getTitle().isEmpty() || coupon.getTitle().length()>45) {
			throw new ApplicationException(ExceptionType.INVALID_TITLE, "Invalid title: " + coupon.getTitle());
		}	
		//Can't have an invalid company ID ???
		if(companiesController.getCompany(coupon.getCompanyId()) == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid ID: " + coupon.getCompanyId());
		}
		//Description has to be shorter than 150 characters (Can be null)
		if(coupon.getDescription() != null && coupon.getDescription().length()>150) {
			throw new ApplicationException(ExceptionType.INVALID_DESCRIPTION, "Description is too long: " + coupon.getDescription());
		}
		//Coupon expiration date can't be before the start date
		if(coupon.getEndDate().before(coupon.getStartDate())) {
			throw new ApplicationException(ExceptionType.INVALID_DATE, "Invalid dates: " + coupon.getStartDate() + " " + coupon.getEndDate());
		}
		//Amount has to be a positive number, also can't update a coupon that sold out without adding more to buy
		if(coupon.getAmount()<=0) {
			throw new ApplicationException(ExceptionType.INVALID_AMOUNT, "Invalid amount: " + coupon.getAmount());
		}
		//Price has to be positive number 
		if(coupon.getPrice()<=0) {
			throw new ApplicationException(ExceptionType.INVALID_PRICE, "Invalid price: " + coupon.getPrice());
		}
		//Image URL link can't be longer than 256 characters (Can be null)
		if(coupon.getImage() != null && coupon.getImage().length()>225) {
			throw new ApplicationException(ExceptionType.INVALID_LINK, "Image URL is too long: " + coupon.getImage());
		}
	}
}
