package com.fitpeo.page;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.fitpeo.base.TestBase;

public class RevenueCalcPage extends TestBase{
	
	static By homePage = By.xpath("//h1[contains(text(), 'Remote Patient')]");
	static By revenuePage = By.xpath("//a[@href=\"/revenue-calculator\"]");
	static By revenuePriceChart = By.xpath("//h5[contains(text(),'Total Gross Revenue')]");
	static By slider = By.xpath("//div/span[1]/span[3]/input");
	static By inputTextField = By.cssSelector(".MuiInputBase-input");
	static By totalRecurringAmount = By.xpath("//p[4]/p");
	
	public boolean goToFitpeoHomePage() {
	
		try {
			navigateToURL();
			waitingForElement(homePage);
			return driver.findElement(homePage).isDisplayed();
					
		}
		catch(Exception ex) {
			System.out.println("Something went wrong...");
			ex.printStackTrace();
			return false;
		}
	
	}
	
	public boolean goToRevenueCalcPage() {
		driver.findElement(revenuePage).click();
//		boolean isRevenuePriceChartDisplayed = false;
		waitingForElement(slider);
		try {
			driver.findElement(slider).isDisplayed();
			return true;
		}
		catch(Exception ex) {
			return false;
		}
	
	}
	
	public boolean scrollDownToSliderSection() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		JavascriptExecutor js = (JavascriptExecutor) driver;
		wait.until(ExpectedConditions.visibilityOfElementLocated(revenuePriceChart));
		//js.executeScript("window.scrollTo(0,300)");
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(slider));
		return driver.findElement(slider).isDisplayed();
		
	}
	
	public boolean moveSlider(int desiredValue) throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(slider));
		WebElement sliderElem = driver.findElement(slider);
		
		Thread.sleep(2000);
		int actValBeforeUpdate = Integer.parseInt(sliderElem.getAttribute("value"));
		System.out.println("actVal: "+ actValBeforeUpdate);
		int diff = desiredValue-actValBeforeUpdate;
		System.out.println("diff: "+ diff);
		int xOffset = (diff*3)/20;
		System.out.println("xOffset: "+ xOffset);
		Actions action = new Actions(driver);
		action.clickAndHold(sliderElem)
		.moveByOffset(xOffset, 0)
		.release()
		.perform();
		
		Thread.sleep(2000);
		int actValAfterUpdate = Integer.parseInt(sliderElem.getAttribute("value"));
		
		return actValAfterUpdate==desiredValue;
	}
	
	
	public boolean updateValuesInTextField(int val) throws InterruptedException {
		
		TestBase.clearingField(driver.findElement(inputTextField));
		driver.findElement(inputTextField).sendKeys(Integer.toString(val)+ Keys.ENTER);
		Thread.sleep(2000);
		int sliderVal = Integer.parseInt(driver.findElement(inputTextField).getAttribute("value"));
		System.out.println(sliderVal);
		return sliderVal==val;
	
	}
	
	public boolean selectCPTCodes(String[] CPTCodes) {
		//We have to Select: CPT-99091 CPT-99453 CPT-99454 CPT-99474
		//String[] CPTCodes = {"CPT-99091","CPT-99453","CPT-99454", "CPT-99474"};
		int slectedRequiredCPTCodes=0;
		int itr = 0;
		List<WebElement> checkBoxes = driver.findElements(By.xpath("//input[@type=\"checkbox\"]"));
		List<WebElement> cptCodesList = driver.findElements(By.cssSelector(".MuiTypography-root.MuiTypography-body1.inter.css-1s3unkt"));
		try {
		while(slectedRequiredCPTCodes!=CPTCodes.length) {
			if(Arrays.asList(CPTCodes).contains(cptCodesList.get(itr).getText())) {
				checkBoxes.get(itr).click();
				itr++;
				slectedRequiredCPTCodes++;
			}
			else{
				itr++;
			}
		}}
		catch(Exception ex) {
			System.out.println("CPT Code Wrong or Not Available...");
		}
		
		if(slectedRequiredCPTCodes==CPTCodes.length) {
			return true;
		}else {
			return false;
		}
		
	}
	
	public boolean validateTotalRecurringAmount(String expectedTotalRecAmt) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(revenuePriceChart));
		js.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(slider));
		String expTotalRecAmnt = expectedTotalRecAmt;
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		String actTotalRecAmnt = wait.until(ExpectedConditions.visibilityOfElementLocated(totalRecurringAmount)).getText();
		System.out.println(actTotalRecAmnt);
		return actTotalRecAmnt.equals(expTotalRecAmnt);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		RevenueCalcPage rn = new RevenueCalcPage();
		launchBrowser();
		navigateToURL();
		rn.goToRevenueCalcPage();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try{
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputTextField));
			rn.scrollDownToSliderSection();
		}catch(Exception ex) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(inputTextField));
			rn.scrollDownToSliderSection();
		}
		
		rn.moveSlider(820);
		rn.updateValuesInTextField(560);
		String[] CPTCodes = {"CPT-99091","CPT-99453","CPT-99454", "CPT-99474"};
		rn.selectCPTCodes(CPTCodes);
		rn.moveSlider(820);
		//rn.scrollDown(driver.findElement(By.xpath("//input[@type=\"checkbox\"]")));
		rn.validateTotalRecurringAmount("$110700");
	}
	
	
	
}
