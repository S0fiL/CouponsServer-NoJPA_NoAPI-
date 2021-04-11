package com.sofi.coupons.enums;

public enum ExceptionType {

	GENERAL_ERROR(643, "Genral Error", true),
	INVALID_ID(644, "This ID is invalid, please enter a valid ID", false),
	INVALID_UPDATE(645, "The data you are trying to update can't be changed", false),
	NAME_TAKEN(646, "This Company name is already taken", false),
	EMAIL_TAKEN(646, "This e-mail is already taken", false),
	INVALID_PHONE(647,"You've entered an invalid phone number", false),
	INVALID_ADDRESS(648, "Invalid address, make sure it doesn't contain more than 45 characters", false),
	INVALID_COMPANY_NAME(649, "Invalid company name, make sure it contains between 2 and 45 characters", false),
	INVALID_EMAIL(650, "You've entered an invalid e-mail", false),
	USER_NAME_TAKEN(651, "This user name is already taken", false),
	INVALID_PASSWORD_LENGTH(652, "Your password has to contain between 8 and 18 characters", false),
	INVALID_PASSWORD(653, "Your password has to contain at least one number, one letter and no special characters", false),
	USER_TYPE_MISSMATCH(654, "This user type can't have a company ID", false),
	INVALID_FIRST_NAME(655, "You've entered an invalid first name", false),
	INVALID_LAST_NAME(656, "You've entered an invalid last name", false),
	LOGIN_FAILED(657, "Failed to login, please try again", false),
	TITLE_ALREADY_EXISTS(658, "A coupon with this title already exists in your company, please choose a different title", false),
	INVALID_TITLE(659, "This title is invalid, make sure it's not empty and doesn't contain more than 45 characters", false),
	INVALID_DESCRIPTION(660, "The description is too long, make sure it doesn't contain more than 150 characters", false),
	INVALID_AMOUNT(661, "Can't add or update coupons with amount less than 1", false),
	INVALID_PRICE(662, "The price has to be a positive number", false),
	NOT_ENOUGH_IN_STOCK(663, "There is not enough coupons in stock", false),
	INVALID_DATE(664, "The dates you've entered are invalid", false),
	INVALID_DATE_UPDATE(665, "Can't shorten the coupon validity period", false),
	INVALID_LINK(666, "This image link is too long, make sure it doesn't contain more than 250 characters", false),
	COUPON_EXPIRED(667,"This coupon has expired",false),
	INVALID_PURCHASE(667,"You have to purchase at least 1 coupon", false);
	
	
	private int exceptionNumber;
	private String message;
	boolean isPrintStackTrace;

	private ExceptionType(int exceptionNumber, String message, boolean isPrintStackTrace) {
		this.exceptionNumber = exceptionNumber;
		this.message = message;
		this.isPrintStackTrace = isPrintStackTrace;
	}

	public int getExceptionNumber() {
		return exceptionNumber;
	}

	public String getMessage() {
		return message;
	}

	public boolean isPrintStackTrace() {
		return isPrintStackTrace;
	}

}
