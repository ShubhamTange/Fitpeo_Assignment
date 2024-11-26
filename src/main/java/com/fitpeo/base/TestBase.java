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

public class TestBase {
	
	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties props;
	static String filename = "src/main/resources/config/config.properties";
	
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
	
	public static void navigateToURL() throws IOException{
		fis = new FileInputStream(filename);
		props = new Properties();
		props.load(fis);
		driver.manage().window().maximize();
		driver.get(props.getProperty("URL"));
		
		
	}
	
	public static void clearingField(WebElement elem) {
		String initialVal = elem.getAttribute("value");
		while (!initialVal.equals("")) {
			elem.sendKeys(Keys.BACK_SPACE);
			initialVal = elem.getAttribute("value");
		}
	}
	
	public void waitingForElement(By xpath) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));

		}
		catch (Exception ex) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
		}
			
	}
	
	public static String captureScreenshots(String scrName) {

		System.out.println("Screenshot for " + scrName);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH_mm_ss");
		String timeStamp = sdf.format(date);

		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		String scnShotFileName = "src/test/resources/output/screenshot_"+scrName+"_" + timeStamp + ".png";
		File DestFile = new File(scnShotFileName);
		try {
			FileUtils.copyFile(SrcFile, DestFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scnShotFileName;

	}
	
}
