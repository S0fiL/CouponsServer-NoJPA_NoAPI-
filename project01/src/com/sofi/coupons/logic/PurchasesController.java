package com.sofi.coupons.logic;

import java.util.List;

import com.sofi.coupons.beans.Coupon;
import com.sofi.coupons.beans.Purchase;
import com.sofi.coupons.dao.PurchasesDao;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;

public class PurchasesController {

	private PurchasesDao purchasesDao;
	private CouponsController couponsController;

	public PurchasesController() {
		purchasesDao = new PurchasesDao();
		couponsController = new CouponsController();
	}

	///////////////////////////////////////////////
	//Public methods that call validation methods//
	///////////////////////////////////////////////

	public long createPurchase(Purchase purchase) throws ApplicationException {
		purchaseCreationValidation(purchase);
		return  purchasesDao.createPurchase(purchase);
	}

	public Purchase getPurchase(long id) throws ApplicationException {
		Purchase purchase = purchasesDao.getPurchase(id);
		if(purchase == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid purchase ID: " + id);
		}
		return purchase;
	}

	public void deletePurchase(long id) throws ApplicationException {
		purchasesDao.deletePurchase(id);
	}

	public List<Purchase> getAllPurchases() throws ApplicationException {
		return purchasesDao.getAllPurchases();
	}

	public List<Purchase> getPurchasesByCompany(long companyId) throws ApplicationException {
		return purchasesDao.getPurchasesByCompany(companyId);
	}

	public List<Purchase> getPurchasesByUser(long userId) throws ApplicationException {
		return purchasesDao.getPurchasesByUser(userId);
	}

	//////////////////////////////
	//Private validation methods//
	//////////////////////////////

	private void purchaseCreationValidation(Purchase purchase) throws ApplicationException {

		Coupon coupon = couponsController.getCoupon(purchase.getCouponId());
		
		//Check if the coupon exists
		if(coupon == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid coupon ID: " + purchase.getId());
		}
		//Check if the coupon expired
		if(coupon.getEndDate().before(purchase.getTimestamp())) {
			throw new ApplicationException(ExceptionType.COUPON_EXPIRED, "Coupon has expired: coupon - " + coupon.getId());
		}
		//Amount has to be a positive number
		if(purchase.getAmount() <= 0) {
			throw new ApplicationException(ExceptionType.INVALID_PURCHASE, "Invalid amount: " + purchase.getAmount());
		}
		//Can't purchase coupons that are sold out
		if(coupon.getAmount() < purchase.getAmount()) {
			throw new ApplicationException(ExceptionType.NOT_ENOUGH_IN_STOCK, "Requested amount is bigger than coupons amount: requested amount - " + purchase.getAmount() + ", coupons amount - " + coupon.getAmount());
		}
	}
}
