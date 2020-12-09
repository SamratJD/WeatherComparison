package org.TestVagrant.WeatherReportingComparator;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UILIbrary extends UIBase {
	public UILIbrary() throws Exception {
		driver = setUp();
	}

	public boolean openURL() {
		String url = prop.getProperty("UI_URL");
		try {
			driver.get(url);
			if (!(driver.getCurrentUrl()).equals(null)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verifyText(By objLocator, String expected) throws Throwable {
		try {
			WebElement w = driver.findElement(objLocator);
			if (w.getText() == null || w.getAttribute("value") == null) {
				return false;
			} else {
				if (w.getText().equals(expected)) {
					return true;
				} else if (w.getAttribute("value").equals(expected)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean clickOnElement(String elementName, By objLocator) {
		try {
			Actions actions = new Actions(driver);
			WebElement w = driver.findElement(objLocator);
			if (w.isEnabled() && w.isDisplayed()) {
				actions.moveToElement(w);
				actions.perform();
				actions.click().build().perform();
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean verifyElementPresent(String elementName, By objLocator) {
		try {
			if (driver.findElements(objLocator).size() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean setText(By objLocator, String text) {
		try {
			Actions actions = new Actions(driver);
			actions.sendKeys(driver.findElement(objLocator), text);
			actions.build().perform();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean waitForElementPresent(By objLocator, int timeToWait) {
		try {

			WebDriverWait wait = new WebDriverWait(driver, timeToWait);
			wait.until(ExpectedConditions.visibilityOfElementLocated(objLocator));
			wait.until(ExpectedConditions.elementToBeClickable(objLocator));
			wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(objLocator));

			if (driver.findElements(objLocator).size() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getcurrentdateandtime() {
		String str = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss:SSS");
			Date date = new Date();
			str = dateFormat.format(date);
			str = str.replace(" ", "").replaceAll("/", "").replaceAll(":", "");
		} catch (Exception e) {
		}
		return str;
	}

	public String captureScreen(String fileName) {
		try {
			TakesScreenshot screen = (TakesScreenshot) driver;
			File src = screen.getScreenshotAs(OutputType.FILE);
			String dest = System.getProperty("user.dir") + "\\TestResults\\Screenshots\\Screenshot_" + fileName
					+ getcurrentdateandtime() + ".png";
			File target = new File(dest);
			FileUtils.copyFile(src, target);
			return dest;
		} catch (Exception e) {
			return "";
		}
	}
}
