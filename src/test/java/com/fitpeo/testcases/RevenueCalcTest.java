package com.fitpeo.testcases;

import org.testng.annotations.Test;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import org.testng.annotations.DataProvider;
import com.fitpeo.base.TestBase;
import com.fitpeo.page.RevenueCalcPage;

public class RevenueCalcTest extends TestBase {
	RevenueCalcPage rn;

	//this is the init method to launch browser
	@BeforeClass
	public void inti() throws IOException {
		launchBrowser();
		//navigateToURL();
		rn = new RevenueCalcPage();

	}

	// Navigate to Fitpeo Home Page and validating home page.
	@Test(priority = 1) 
	public void navigateTOFitpeoHomePage() throws IOException {

		AssertJUnit.assertTrue(rn.goToFitpeoHomePage());
	}

	// Validating you revenue calculator page is displayed.
	@Test(priority = 2) 
	public void navigateTORevnueCalcPage() {

		assertTrue(rn.goToRevenueCalcPage());
	}

	// validating Scroll Down to a slider section method 
	@Test(priority = 3)
	public void valdateNavigateToSliderSection() {

		assertTrue(rn.scrollDownToSliderSection());
	}

	// validating adjusting the slider properly
	@Test(priority = 4)
	public void validateAdjustTheSlider() {

		try {
			AssertJUnit.assertTrue(rn.moveSlider(820));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// validating the text field values is properly updating
	@Test(priority = 5)
	public void validateSliderTextFiled() {

		try {
			assertTrue(rn.updateValuesInTextField(560));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//validating Selecting CPT-Codes is selected properly
	@Test(priority = 6, dataProvider = "cptCodeArray")
	public void validateSelectCPTCodes(String[] arr) {
		for(String i : arr) {
			System.out.println(i);
		}
		assertTrue(rn.selectCPTCodes(arr));
	}
	
	//validating the total recurring amount is shown on header
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
	
	//providing data for choosing cpt codes
	@DataProvider
	public Object[][] cptCodeArray(){
		return new Object[][]{
			{new String[] {"CPT-99091","CPT-99453","CPT-99454", "CPT-99474"}}
		};
		
	}
	
	//Closing the current window
	@AfterSuite
	public void teardown() {
		driver.quit();
	}

}
