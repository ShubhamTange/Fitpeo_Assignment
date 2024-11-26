package com.fitpeo.testcases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.fitpeo.base.TestBase;
import com.fitpeo.page.RevenueCalcPage;

public class RevenueCalcTest extends TestBase {
	RevenueCalcPage rn;

	@BeforeClass
	public void inti() throws IOException {
		launchBrowser();
		//navigateToURL();
		rn = new RevenueCalcPage();

	}

	// Navigate to Fitpeo Home Page
	@Test(priority = 1) // Validating you revenue calculator page is displayed.
	public void navigateTOFitpeoHomePage() throws IOException {

		assertTrue(rn.goToFitpeoHomePage());
	}

	@Test(priority = 2) // Validating you revenue calculator page is displayed.
	public void navigateTORevnueCalcPage() {

		assertTrue(rn.goToRevenueCalcPage());
	}

	// Scroll Down TO the slider section
	@Test(priority = 3)
	public void valdateNavigateToSliderSection() {

		assertTrue(rn.scrollDownToSliderSection());
	}

	// adjust the slider
	@Test(priority = 4)
	public void validateAdjustTheSlider() {

		try {
			assertTrue(rn.moveSlider(820));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// validating the text field
	@Test(priority = 5)
	public void validateSliderTextFiled() {

		try {
			assertTrue(rn.updateValuesInTextField(560));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Selecting CPT-Codes
	@Test(priority = 6, dataProvider = "cptCodeArray")
	public void validateSelectCPTCodes(String[] arr) {
		for(String i : arr) {
			System.out.println(i);
		}
		assertTrue(rn.selectCPTCodes(arr));
	}
	
	//validating the total recurring amount
	@Test(priority = 7)
	public void validateTotalrecurringAmnt() {
		try {
			rn.moveSlider(820);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(rn.validateTotalRecurringAmount("$110700"));
	}
	
	
	@DataProvider
	public Object[][] cptCodeArray(){
		return new Object[][]{
			{new String[] {"CPT-99091","CPT-99453","CPT-99454", "CPT-99474"}}
		};
		
	}

}
