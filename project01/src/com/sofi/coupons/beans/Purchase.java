package com.sofi.coupons.beans;

import java.sql.Timestamp;

public class Purchase {

	private long id;
	private long userId;
	private long couponId;
	private int amount;
	private Timestamp timestamp;


	public Purchase() {
		super();
	}

	public Purchase(long userId, long couponId, int amount, Timestamp timestamp) {
		super();
		this.userId = userId;
		this.couponId = couponId;
		this.amount = amount;
		this.timestamp = timestamp;
	}

	public Purchase(long id, long userId, long couponId, int amount, Timestamp timestamp) {
		this(userId, couponId, amount, timestamp);
		this.id = id;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", User ID: " + userId + ", Coupon ID: " + couponId + ", Amount: " + amount
				+ ", Timestamp: " + timestamp;
	}

}
