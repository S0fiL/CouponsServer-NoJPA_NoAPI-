package com.sofi.coupons.tests.logicTest;

import java.util.List;

import com.sofi.coupons.beans.Company;
import com.sofi.coupons.logic.CompaniesController;

public class CompaniesTest {

	private static CompaniesController companiesCon = new CompaniesController();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		testCreateCompany();
//		testGetCompany();
//		testUpdateCompany();
//		testDeleteCompany();
//		testGetAllCompanies();
	}

	private static void testCreateCompany () {

		System.out.println("Name test");
		System.out.print("Null: ");
		try {
			Company company = new Company(null, "null@gmail.com", "*0000", "none");
			companiesCon.createCompany(company);
			System.out.println("Company name null test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Empty: ");
		try {
			Company company = new Company("", "null@gmail.com", "*0000", "none");
			companiesCon.createCompany(company);
			System.out.println("Company name empty test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Short: ");
		try {
			Company company = new Company("I", "buy@gmail.com", "*1010", "none");
			companiesCon.createCompany(company);
			System.out.println("Company name too short test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Long: ");
		try {
			companiesCon.createCompany(new Company("Supreme Headquarters, International Espionage and Law-Enforcement Division", "s.h.i.e.l.d@fury.com", "*1010", "Helicarrier"));
			System.out.println("Company name too long test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Already exists: ");
		try {
			Company company = new Company("aa", "a@a.aa", "1111", "aaa");
			long id1 = companiesCon.createCompany(company);
			try {
			companiesCon.createCompany(company);
			System.out.println("Company already exists test failed");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			companiesCon.deleteCompany(id1);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nE-mail test");
		System.out.print("Null: ");
		try {
			Company company = new Company("Sirius Cybernetics Corp.", null, "*42", " LLC, Washington State USA.");
			companiesCon.createCompany(company);
			System.out.println("Company email null test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("Invalid: ");
		try {
			Company company = new Company("Tyrell Corp", "mhth", "*2026", "Los Angeles");
			companiesCon.createCompany(company);
			System.out.println("Company invalid email test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nPhone test");
		System.out.print("Null: ");
		try {
			Company company = new Company("Blue Sun Corporation", "miranda@unionOfAP.com", null, "United Space");
			companiesCon.createCompany(company);
			System.out.println("Company phone null test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("Invalid: ");
		try {
			Company company = new Company("Reliable Excavation Demolition", "RED@demolition.com", "a&", "US");
			companiesCon.createCompany(company);
			System.out.println("Company invalid phone test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nAddress test");
		System.out.print("Long: ");
		try {
			Company company = new Company("ccc", "a@a.aa", "000", "sdfsdfdsdsfdsfdsfsdfsdfsdfsddfsdfsdfsdfsdfsdfsdfsdfsdfsdf");
			companiesCon.createCompany(company);
			System.out.println("Company invalid address test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.print("\nValid company: ");
		try {
			Company company = new Company("ddd", "a@a.aa", "000", "a");
			long id = companiesCon.createCompany(company);
			if(companiesCon.getCompany(id) != null)
				System.out.println("Company was created successfully");
			else
				System.out.println("Company creation test failed");
			companiesCon.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testGetCompany() {

		try {
			Company company = new Company("iii", "a@a.aa", "000","a");
			long id = companiesCon.createCompany(company);
			Company company2 = companiesCon.getCompany(id);
			if(company.getName().equals(company2.getName()) && company.getEmail().equals(company2.getEmail())
					&& company.getPhone().equals(company2.getPhone()) && company.getAddress().equals(company2.getAddress()))
				System.out.println("Company was identified successfully");
			else
				System.out.println("Get company test failed");
			companiesCon.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
	}


	private static void testUpdateCompany () {

		System.out.print("Invalid data: ");
		try {
			Company company = new Company("eee", "a@a.aa", "000","a");
			companiesCon.createCompany(new Company("lll", "a@a.aa", "000","a"));
			long id = companiesCon.createCompany(company);
			company = companiesCon.getCompany(id);
			company.setName("111");
			companiesCon.updateCompany(company);
			System.out.println("Invalid data update test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("\nInvalid company: ");
		try {
			Company company = new Company("fff", "a@a.aa", "000","a");
			long id = companiesCon.createCompany(company);
			company = companiesCon.getCompany(id);
			companiesCon.deleteCompany(id);
			companiesCon.updateCompany(company);
			System.out.println("Invalid company update test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("\nValid company: ");
		try {
			Company company = new Company("ggg", "a@a.aa", "000","a");
			long id = companiesCon.createCompany(company);
			company = companiesCon.getCompany(id);
			company.setAddress("b");
			companiesCon.updateCompany(company);
			if(company.getAddress().equals(companiesCon.getCompany(id).getAddress()))
				System.out.println("Company was updated successfully");
			else
				System.out.println("Company update test failed");
			companiesCon.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


	private static void testDeleteCompany() {

		System.out.print("Invalid company: ");
		try {
			Company company = new Company("jjj", "a@a.aa", "000","a");
			long id = companiesCon.createCompany(company);
			companiesCon.deleteCompany(id);
			companiesCon.deleteCompany(id);
			System.out.println("Inexistent company test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.print("\nValid company: ");
		try {
			Company company = new Company("jjj", "a@a.aa", "000","a");
			long id = companiesCon.createCompany(company);
			companiesCon.deleteCompany(id);
			if(companiesCon.getCompany(id) == null)
				System.out.println("Company was deleted successfully");
			else
				System.out.println("Company delete test failed");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private static void testGetAllCompanies() {

		try {
			long id = companiesCon.createCompany(new Company("jjj", "a@a.aa", "000","a"));
			List<Company> companies = companiesCon.getAllCompanies();
			if(!companies.isEmpty())
				System.out.println("Companies list created successfully");
			else
				System.out.println("Get all companies test failed");
			companiesCon.deleteCompany(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}


