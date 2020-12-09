package org.TestVagrant.WeatherReportingComparator;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class UIBase {

	public static WebDriver driver;
	public static FileInputStream fis;
	public static Properties prop = new Properties();
	public static String browser;

	public WebDriver setUp() throws Exception {
		fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalConfig.Properties");
		prop.load(fis);
		String browserName = prop.getProperty("BROWSER");
		String globalWaitTime = prop.getProperty("WAIT_TIME");
		long waitTime = Long.parseLong(globalWaitTime);

		if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\Drivers\\chromedriver.exe");
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		} else if (browserName.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\Drivers\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "\\Drivers\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
		return driver;
	}

}
