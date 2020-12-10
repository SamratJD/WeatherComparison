# WeatherComparison
1. Fetches city names from excel sheet.
2. Verifies and fetches the temperatures from NDTV weather site.
3. Executes weather API and fetches the temperatures from response.
4. Compares the temperatures between NDTV website and from weather API response and returns PASS/FAIL.
5. Allowed variance of temperature(as per acceptance criteria) can be set in the configurations file.

## Pre-requisites for this project:
Requires Java, Maven and Eclipse (or any suitable IDE) to be installed.

## Maven Dependencies:
1. Selenium-java
2. Rest-assured
3. TestNG
4. Apache POI

## Test Data specification:
Data sheets are kept in `/TestData/` path
1. CITY_WEATHER_LIST - for storing and report generation of the main End-to-End city-wise temperature validation.
2. API_REG_LIST - for storing API regression suite data
3. UI_REG_LIST for storing UI regression suite data

## Global properties file:
Configurations like URL, API Key etc. are kept in the `GlobalConfig.Properties` file.

## 1. To execute via TestNG, run the below files as TestNG Suite:
1. E2E_Runner - for End-to-end suite
2. API_Regression_Runner - for API regression suite
3. UI_Regression_Runner - for UI regression suite

## 2. To run via Maven command use the 3 Maven commands below:
1. `mvn -DrunnerName=E2E_Runner test`
2. `mvn -DrunnerName=API_Regression_Runner test`
3. `mvn -DrunnerName=UI_Regression_Runner test`

By default, *E2E_Runner* will be executed if `mvn test` is run.
