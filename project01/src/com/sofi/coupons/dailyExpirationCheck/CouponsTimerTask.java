package com.sofi.coupons.dailyExpirationCheck;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimerTask;

import com.sofi.coupons.dao.CouponsDao;
import com.sofi.coupons.exceptions.ApplicationException;

public class CouponsTimerTask extends TimerTask{

	private CouponsDao couponsDao;

	public CouponsTimerTask() {
		this.couponsDao = new CouponsDao();
	}

	@Override
	public void run() {
		Calendar cal = Calendar.getInstance();
		Date expirationDate = new Date(cal.getTimeInMillis());
		try {
			couponsDao.deleteExpiredCoupons(expirationDate);
		} catch (ApplicationException e) {
			System.out.println(e.getMessage());
		}
	}
}

