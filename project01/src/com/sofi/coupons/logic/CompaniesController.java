package com.sofi.coupons.logic;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.dao.CompaniesDao;
import com.sofi.coupons.enums.ExceptionType;
import com.sofi.coupons.exceptions.ApplicationException;

public class CompaniesController {

	private  CompaniesDao companiesDao;

	public CompaniesController() {
		companiesDao = new CompaniesDao();
	}

	///////////////////////////////////////////////
	//Public methods that call validation methods//
	///////////////////////////////////////////////

	public long createCompany(Company company) throws ApplicationException {
		createCompanyValidation(company);
		return companiesDao.createCompany(company);
	}

	public Company getCompany (long id) throws ApplicationException {
		Company company = companiesDao.getCompany(id);
		if(company == null) {
			throw new ApplicationException(ExceptionType.INVALID_ID, "Invalid company ID: " + id);
		}
		return company;
	}

	public void updateCompany (Company company) throws ApplicationException {
		updateCompanyValidation(company);
		companiesDao.updateCompany(company);
	}

	public void deleteCompany (long id) throws ApplicationException {
		companiesDao.deleteCompany(id);
	}

	public List<Company> getAllCompanies() throws ApplicationException{
		return companiesDao.getAllCompanies();
	}

	//////////////////////////////
	//Private validation methods//
	//////////////////////////////	

	private void createCompanyValidation(Company company) throws ApplicationException {

		//Check if all the data is valid
		companyInfoValidation(company);

		//Can't create two companies with the same name
		if(companiesDao.isCompanyNameExists(company.getName())) {
			throw new ApplicationException(ExceptionType.NAME_TAKEN, "Company name already exists: " + company.getName());
		}
		//Can't create two companies with the same e-mail
		if(companiesDao.isCompanyEmailExists(company.getEmail())) {
			throw new ApplicationException(ExceptionType.EMAIL_TAKEN, "Company e-mail already exists: " + company.getEmail());
		}
	}


	private void updateCompanyValidation(Company company) throws ApplicationException {

		//Check if all the data is valid
		companyInfoValidation(company);

		//Get company's original info 
		Company oldCompany = companiesDao.getCompany(company.getId());
		//If updating company name, need to check if the new name isn't already taken
		if(!company.getName().equals(oldCompany.getName()) && companiesDao.isCompanyNameExists(company.getName())) { 
			throw new ApplicationException(ExceptionType.NAME_TAKEN, "New company name is already taken: " + company.getName());
		}
		//If updating company e-mail, need to check if the new email is'nt already taken
		if(!company.getEmail().equals(oldCompany.getEmail()) && companiesDao.isCompanyEmailExists(company.getEmail())) { 
			throw new ApplicationException(ExceptionType.EMAIL_TAKEN, "New company e-mail already exists: " + company.getEmail());
		}
	}


	private void companyInfoValidation(Company company) throws ApplicationException {

		//Company name has to contain between 2 and 45 characters
		if(isOutOfRange(company.getName(), 2, 45)) { 
			throw new ApplicationException(ExceptionType.INVALID_COMPANY_NAME, "Invalid name: " + company.getName());
		}
		//Company e-mail has to contain between 6 and 45 characters and symbols . and @
		if(isOutOfRange(company.getEmail(), 6, 45) || !company.getEmail().contains("@") || !company.getEmail().contains(".")) {
			throw new ApplicationException(ExceptionType.INVALID_EMAIL, "Invalid e-mail: " + company.getEmail());
		}
		//Company phone number has to contain between 3 and 14 characters
		if(isOutOfRange(company.getPhone(), 3, 14)) { 
			throw new ApplicationException(ExceptionType.INVALID_PHONE, "Invalid phone number: " + company.getPhone());
		}
		//Creating a pattern of all the symbols that a phone number can't contain and matching it
		Pattern p = Pattern.compile("[^0-9*()-]");
		Matcher m = p.matcher(company.getPhone());

		//Company phone number can't contain any of the symbols in the pattern above
		if(m.find()) { 
			throw new ApplicationException(ExceptionType.INVALID_PHONE, "Invalid phone number: " + company.getPhone());
		}
		//Company address can't be longer than 45 characters (Can be null)
		if(company.getAddress().length()>45) {
			throw new ApplicationException(ExceptionType.INVALID_ADDRESS, "Address is too long: " + company.getAddress());
		}
	}
	 private static boolean isOutOfRange(String text, int minChar, int maxChar) {
	    	
	    	if(text == null || text.length() < minChar || text.length() > maxChar) {
	    		return true;
	    	}
	    	return false;
	    }
}



