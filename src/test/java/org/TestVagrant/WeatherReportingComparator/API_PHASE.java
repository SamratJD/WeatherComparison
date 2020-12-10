package org.TestVagrant.WeatherReportingComparator;

import java.util.Map;

public class API_PHASE extends APIBase {

	public String fetchAPIDetails(String city) throws Exception {
		String cityTemp = null;

		// Parsing the 4 temperatures
		Map main = getResponse(city).jsonPath().getMap("main");

		// Storing the 4 temperatures
		cityTemp = main.get("temp").toString();
//		cityTemp.add(main.get("temp").toString());
//		cityTemp.add(main.get("feels_like").toString());
//		cityTemp.add(main.get("temp_min").toString());
//		cityTemp.add(main.get("temp_max").toString());

		return cityTemp;
	}

}
