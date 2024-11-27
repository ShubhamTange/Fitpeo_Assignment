package com.fitpeo.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;
//this class contains common methods shared by all classes which we are using in general
public class TestBase {
	
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties props;
	
	//this is the filename from where we are reading the configuration like browser name and url
	static String filename = "src/main/resources/config/config.properties";
	
	//method to launch a a prticular browser
	public static void launchBrowser() throws IOException {
		fis = new FileInputStream(filename);
		props = new Properties();
		props.load(fis);
		
		if(props.getProperty("Browser").equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}else if(props.getProperty("Browser").equalsIgnoreCase("edge")) {
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}else {
			System.out.println("Browser name is invalid");
		}
	}
	
	//method to navigate to the url
	public static void navigateToURL() throws IOException{
		fis = new FileInputStream(filename);
		props = new Properties();
		props.load(fis);
		driver.manage().window().maximize();
		driver.get(props.getProperty("URL"));
		
		
	}
	
	//method to clear the text box
	public static void clearingField(WebElement elem) {
		String initialVal = elem.getAttribute("value");
		while (!initialVal.equals("")) {
			elem.sendKeys(Keys.BACK_SPACE);
			initialVal = elem.getAttribute("value");
		}
	}
	
	//method to wait for particular element
	public void waitingForElement(By xpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));

		}
		catch (Exception ex) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		}
			
	}
	
}

