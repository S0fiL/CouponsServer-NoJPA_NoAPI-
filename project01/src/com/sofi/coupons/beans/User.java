package com.sofi.coupons.beans;

import com.sofi.coupons.enums.UserType;

public class User {


	private long id;
	private String userName;
	private String password;
	private Long companyId;
	private UserType userType;
	private String firstName;
	private String lastName;


	public User() {
		super();
	}


	public User(String userName, String password, UserType userType, Long companyId,String firstName, String lastName) {
		super();
		this.userName = userName;
		this.password = password;
		this.userType = userType;
		this.companyId = companyId;
		this.firstName = firstName;
		this.lastName = lastName;

	}


	public User(long id, String userName, String password, UserType userType, Long companyId,String firstName, String lastName) {
		this(userName,password,userType,companyId,firstName,lastName);
		this.id = id;

	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public UserType getUserType() {
		return userType;
	}


	public void setUserType(UserType userType) {
		this.userType = userType;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		return "ID: " + id + ", User Name: " + userName + ", Password: " + password + ", Company ID: " + companyId
				+ ", User Type: " + userType + ", First Name: " + firstName + ", Last Name: " + lastName;
	}
}
