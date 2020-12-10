package org.TestVagrant.WeatherReportingComparator;

import java.io.FileInputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class UI_REG extends UIBase {

	public static FileInputStream fis;
	public static Properties prop = new Properties();

	// Object properties:
	public By NDTVHOME_SUBMENU_LINK = By.id("h_sub_menu");
	public By NDTV_POPUP_BUTTON = By.xpath("//*[text()='No Thanks']");
	public By NDTVHOME_WEATHEROPTION_LINK = By.xpath("//a[text()='WEATHER']");
	public By NDTVWEATHER_SEARCHBOX_TEXTBOX = By.id("searchBox");
	public By NDTVWEATHER_CHECKEDCITIES_CHECKBOX = By.xpath("//*[@class='defaultChecked']");
	public By NDTVWEATHER_NEWCITY_CHECKBOX = By.xpath("//*[@class='defaultChecked']");

	@Test
	public void validateAddedCities() throws Throwable {
		fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalConfig.Properties");
		prop.load(fis);
		String fileName = prop.getProperty("UI_FILENAME");
		ExcelReader ex = new ExcelReader(System.getProperty("user.dir") + "\\TestData\\" + fileName);

		UILIbrary lib = new UILIbrary();
		// Navigate to Weather page
		lib.openURL();
		lib.waitForElementPresent(NDTV_POPUP_BUTTON, 15);
		lib.clickOnElement("No thanks button", NDTV_POPUP_BUTTON);
		lib.waitForElementPresent(NDTVHOME_SUBMENU_LINK, 15);
		lib.clickOnElement("Submenu Link", NDTVHOME_SUBMENU_LINK);
		lib.waitForElementPresent(NDTVHOME_WEATHEROPTION_LINK, 10);
		lib.clickOnElement("Weather Link", NDTVHOME_WEATHEROPTION_LINK);
		lib.waitForElementPresent(NDTVWEATHER_SEARCHBOX_TEXTBOX, 10);

		// Entering city names
		lib.clickOnElement("Search box", NDTVWEATHER_SEARCHBOX_TEXTBOX);
		String cityName = null;
		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			cityName = ex.getCellData(prop.getProperty("SHEETNAME"), prop.getProperty("UI_COL"), i);

			lib.setText(NDTVWEATHER_SEARCHBOX_TEXTBOX, cityName);
			String checkCityCheckboxXpath = "//*[@id='" + cityName + "']";
			if (!driver.findElement(By.xpath(checkCityCheckboxXpath)).isSelected()) {
				lib.clickOnElement("City Checkbox", By.xpath(checkCityCheckboxXpath));
			}

			// Validating corresponding cities on the map
			String checkCityMapXpath = "//*[@class='cityText'][text()='" + cityName + "']";
			lib.verifyText(By.xpath(checkCityMapXpath), cityName);
			lib.clickOnElement("City on Map", By.xpath(checkCityMapXpath));
			lib.captureScreen(cityName);

			String tempXpath = "//*[@id='map_canvas']//div[@class='cityText'][text()='" + cityName
					+ "']/../div/span[1]";
			// Removing unused character
			String cityTempNumber = driver.findElement(By.xpath(tempXpath)).getText();
			cityTempNumber = cityTempNumber.substring(0, cityTempNumber.length() - 1);

			// To clear previous data
			driver.findElement(NDTVWEATHER_SEARCHBOX_TEXTBOX).clear();
		}
		
		// Close browser
		driver.quit();

	}

}
