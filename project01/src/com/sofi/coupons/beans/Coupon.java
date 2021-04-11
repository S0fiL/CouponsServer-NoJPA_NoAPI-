package com.sofi.coupons.beans;

import java.sql.Date;

import com.sofi.coupons.enums.Category;

public class Coupon {

	private long id;
	private long companyId;
	private Category category;
	private String title;
	private String description;
	private Date startDate;
	private Date endDate;
	private int amount;
	private float price;
	private String image;

	public Coupon() {
		super();
	}

	public Coupon(long companyId, Category category, String title, String description, Date startDate,
			Date endDate, int amount, float price, String image) {
		super();
		this.companyId = companyId;
		this.category = category;
		this.title = title;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.price = price;
		this.image = image;
	}

	public Coupon(long id, long companyId, Category category, String title, String description, Date startDate,
			Date endDate, int amount, float price, String image) {
		this(companyId, category, title, description, startDate, endDate, amount, price, image);
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "ID: " + id + ", Company ID: " + companyId + ", Category: " + category + ", Title: " + title
				+ ", Description: " + description + ", Start Date: " + startDate + ", End Date: " + endDate + ", Amount: "
				+ amount + ", Price: " + price + ", Image: " + image;
	}




}
