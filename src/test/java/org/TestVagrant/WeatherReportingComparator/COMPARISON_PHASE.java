package org.TestVagrant.WeatherReportingComparator;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class COMPARISON_PHASE extends UIBase {
	public static FileInputStream fis;
	public static Properties prop = new Properties();

	@BeforeTest
	public void setup() {
		System.out.println(
				"---------------------------------------------------TESTS STARTED------------------------------------------------");
	}

	@Test
	public void comparison() throws Throwable {
		UILIbrary lib = new UILIbrary();
		API_PHASE api = new API_PHASE();
		UI_PHASE ui = new UI_PHASE();

		fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalConfig.Properties");
		prop.load(fis);
		String fileName = prop.getProperty("INPUT_OUTPUT_FILENAME");
		ExcelReader ex = new ExcelReader(System.getProperty("user.dir") + "\\TestData\\" + fileName);

		// 1. API Phase:
		List<String> apiTemp = new ArrayList<String>();
		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			apiTemp.add(
					api.fetchAPIDetails(ex.getCellData(prop.getProperty("SHEETNAME"), prop.getProperty("API_COL"), i)));

		}
		int celcius;
		float kelvin;
		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			kelvin = Float.parseFloat(apiTemp.get(i - 2));
			celcius = Math.round(kelvin - 273.15F);
			ex.setCellData(prop.getProperty("SHEETNAME"), prop.getProperty("APITEMP_COL"), i, Float.toString(celcius));

		}

		// 2. UI Phase:
		List<String> uiTemp = new ArrayList<String>();
		ui.navigateToWeatherPage(lib);
		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			uiTemp.add(ui.validateAddedCities(
					ex.getCellData(prop.getProperty("SHEETNAME"), prop.getProperty("UI_COL"), i), lib));
		}
		for (int i = 2; i <= ex.getRowCount(prop.getProperty("SHEETNAME")); i++) {
			ex.setCellData(prop.getProperty("SHEETNAME"), prop.getProperty("UIITEMP_COL"), i, uiTemp.get(i - 2));

		}

		// 3. Comparison Phase:
		if (apiTemp.size() == uiTemp.size()) {
			for (int i = 0; i < uiTemp.size(); i++) {
				kelvin = Float.parseFloat(apiTemp.get(i));
				celcius = Math.round(kelvin - 273.15F);

				if (Math.abs(Integer.parseInt(uiTemp.get(i)) - celcius) <= Integer
						.parseInt(prop.getProperty("OPTIMAL_VALUE"))) {
					ex.setCellData(prop.getProperty("SHEETNAME"), "OPTIMAL_DIFF", i + 2, "PASS");
					ex.setCellData(prop.getProperty("SHEETNAME"), "MAX_ALLOWED_DIFF", i + 2, "PASS");
				} else if (Math.abs(Integer.parseInt(uiTemp.get(i)) - celcius) <= Integer
						.parseInt(prop.getProperty("MAXALLOWED_VALUE"))) {
					ex.setCellData(prop.getProperty("SHEETNAME"), "OPTIMAL_DIFF", i + 2,
							"FAIL: temperature difference is greater than " + prop.getProperty("OPTIMAL_VALUE"));
					ex.setCellData(prop.getProperty("SHEETNAME"), "MAX_ALLOWED_DIFF", i + 2, "PASS");
				} else {
					ex.setCellData(prop.getProperty("SHEETNAME"), "OPTIMAL_DIFF", i + 2,
							"FAIL: temperature difference is greater than " + prop.getProperty("OPTIMAL_VALUE"));
					ex.setCellData(prop.getProperty("SHEETNAME"), "MAX_ALLOWED_DIFF", i + 2,
							"FAIL: temperature difference is greater than " + prop.getProperty("MAXALLOWED_VALUE"));
				}
			}
		} else {
			throw new Exception("Size mismatch between API and UI temperature list");
		}
	}

	@AfterTest
	public void tearDown() {
		driver.close();
		System.out.println(
				"---------------------------------------------------TESTS ENDED------------------------------------------------");
	}
}
