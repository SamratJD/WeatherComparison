package org.TestVagrant.WeatherReportingComparator;

import java.io.FileInputStream;
import java.util.Properties;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class APIBase {
	public static FileInputStream fis;
	public static Properties prop = new Properties();

	public Response getResponse(String city) throws Exception {
		fis = new FileInputStream(System.getProperty("user.dir") + "\\GlobalConfig.Properties");
		prop.load(fis);

		RestAssured.baseURI = prop.getProperty("API_URI");
		String apiKey = prop.getProperty("API_KEY");
		RequestSpecification httpRequest = RestAssured.given();

		Response response = httpRequest.request(Method.GET, "/data/2.5/weather?q=" + city + "&appid=" + apiKey);
		return response;
	}

}
