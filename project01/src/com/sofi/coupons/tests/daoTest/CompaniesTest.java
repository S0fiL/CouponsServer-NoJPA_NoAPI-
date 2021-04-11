package com.sofi.coupons.tests.daoTest;

import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.dao.CompaniesDao;

public class CompaniesTest {

	private static CompaniesDao companiesDao = new CompaniesDao();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//		testCreateCompany();
		//		testGetCompany();
		//		testUpdateCompany();
		//		testDeleteCompany();
		//		testGetAllCompanies();
		//		testIsCompanyNameExists();
		//		testIsCompanyEmailExists();
		//		testIsCompanyIdExists();

	}


	private static void testCreateCompany() {

		Company company = new Company("Sun Covenant", "jolly.cooperation@lordran.com", "send a raven", "under the red drake");
		try {
			long id = companiesDao.createCompany(company);
			if(id != 0) {
				System.out.println("Company was created by the id - "+id);
			} else {
				System.out.println("Company creation test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCompany() {

		Company company = new Company("Black Mesa", "black.mesa@valve.com", "00110011", "New Mexico, USA");
		try {
			long id = companiesDao.createCompany(company);
			Company company2 = companiesDao.getCompany(id);
			if(company.getName().equals(company2.getName()) && company.getEmail().equals(company2.getEmail())
					&& company.getPhone().equals(company2.getPhone()) && company.getAddress().equals(company2.getAddress())) {
				System.out.println("The company was extracted by ID");
			} else {
				System.out.println("Get company test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testUpdateCompany() {

		Company company = new Company("Umbrella Academy", "umbrella@ua.com", "12-345-67", "London 4 King Street East");
		try {
			long id = companiesDao.createCompany(company);
			company = companiesDao.getCompany(id);
			company.setName("Sparrow Academy");
			companiesDao.updateCompany(company);
			Company company2 = companiesDao.getCompany(id);
			if(company.getName().equals(company2.getName()) && company.getEmail().equals(company2.getEmail())
					&& company.getPhone().equals(company2.getPhone()) && company.getAddress().equals(company2.getAddress())) {
				System.out.println("Company was updated");
			} else {
				System.out.println("Company update test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testDeleteCompany() {

		Company company = new Company("Ryan Industries", "Would.You.Kindly@doit.now", "22-555-333" , "Rapture");
		try {
			long id = companiesDao.createCompany(company);
			companiesDao.deleteCompany(id);
			if(companiesDao.getCompany(id)==null) {
				System.out.println("Company was deleted");
			} else {
				System.out.println("Delete company test failed");
			}	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetAllCompanies() {

		Company company = new Company("Stark Industries", "iron.man@avengers.com", "11-111-1111", "Avengers tower");
		try {
			long id = companiesDao.createCompany(company);
			List<Company> companies = companiesDao.getAllCompanies();
			if(!companies.isEmpty()) {
				System.out.println("Company list was created");
			} else {
				System.out.println("Get all companies test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testIsCompanyNameExists() {

		String name = "Hyperion";
		Company company = new Company(name, "pandoras.future@vaults.com", "1-000-000", "Helios station");
		try { 	    
			long id = companiesDao.createCompany(company);
			if(companiesDao.isCompanyNameExists(name)) {
				System.out.println("Company name was identified");
			} else {
				System.out.println("Is name exist test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testIsCompanyEmailExists() {

		String email = "pandoras.future@vaults.com";
		Company company = new Company("Hyperion", email, "1-000-000", "Helios station");
		try { 	    
			long id = companiesDao.createCompany(company);
			if(companiesDao.isCompanyEmailExists(email)) {
				System.out.println("Company e-mail was identified");
			} else {
				System.out.println("Is e-mail exist test failed");
			}
			companiesDao.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

