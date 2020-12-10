package org.TestVagrant.WeatherReportingComparator;

import java.io.FileInputStream;
import java.util.Properties;

import org.testng.Assert;
import org.testng.annotations.Test;

public class API_REG extends APIBase {
	public static FileInputStream fis;
	public static Properties prop = new Properties();

	@Test
	public void fetchAPIDetails() throws Exception {
		fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalConfig.Properties");
		prop.load(fis);
		String fileName = prop.getProperty("API_FILENAME");
		ExcelReader ex = new ExcelReader(System.getProperty("user.dir") + "\\TestData\\" + fileName);
		String city = null;

		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			city = ex.getCellData(prop.getProperty("SHEETNAME"), prop.getProperty("API_COL"), i);

			// Response validation
			int statusCode = getResponse(city).getStatusCode();
			Assert.assertEquals(statusCode, 200, "Status code validation passed");

			String contentType = getResponse(city).getHeader("Content-Type");
			Assert.assertEquals(contentType, "application/json; charset=utf-8", "Content type validation passed");

			String cityName = getResponse(city).jsonPath().get("name");
			System.out.println("City: " + city);
			Assert.assertEquals(cityName, city, "City name validation passed");
		}
	}

}
