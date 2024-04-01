package com.expleo.base;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v121.network.*;
import org.openqa.selenium.devtools.v121.network.model.Headers;

import com.expleo.model.CreateContact;
import com.expleo.model.GetContact;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BaseClass {

	public static WebDriver driver;
	public static Response response;
	public static ValidatableResponse json;
	public static RequestSpecification reqspec = new RequestSpecBuilder().build();
	public static JsonPath jsonPath;
	public static Properties properties;
	public static CreateContact createContact = new CreateContact();
	public static GetContact getContact = new GetContact();
	public static String endpoint = "contacts/";
	public static String bearerToken = null;

	public static String getPropertyFileValue(String value) {
		try {
			FileInputStream file = new FileInputStream("src\\test\\resources\\app.properties");
			properties = new Properties();
			properties.load(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return properties.getProperty(value);
	}

	public static void getDriver(String browserName) {
		switch (browserName) {
		case "chrome":
//			ChromeOptions options = new ChromeOptions();
//			LoggingPreferences logPrefs = new LoggingPreferences();
//			logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
//			options.setCapability( "goog:loggingPrefs", logPrefs );
//			WebDriverManager.chromedriver().clearDriverCache().setup();
//			driver = new ChromeDriver(options);    

			WebDriverManager.chromedriver().clearDriverCache().setup();
			driver = new ChromeDriver();
			break;
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
		default:
			break;
		}
	}

	public static void logPref() {
		List<LogEntry> entries = driver.manage().logs().get(LogType.PERFORMANCE).getAll();
		System.out.println(entries.size() + " " + LogType.PERFORMANCE + " log entries found");
		for (LogEntry entry : entries) {
			System.out.println(entry.getMessage());
		}
	}

	public static void networkDetails() {
		DevTools devTools = ((ChromeDriver) driver).getDevTools();
		devTools.createSession();
		devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
		devTools.addListener(Network.requestWillBeSent(), response -> {
			Headers headers = response.getRequest().getHeaders();
			if (headers != null && headers.get("Authorization") != null) {
				bearerToken = String.valueOf(headers.get("Authorization"));
				System.out.println(bearerToken);
			}
		});
	}

	public static void elementVisibility(WebElement e) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	public static void maximize() {
		driver.manage().window().maximize();
	}

	public static void loadURL(String url) {
		driver.get(url);
	}

	public static void implicitwait() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	public static void quitBrowser() {
		driver.quit();
	}

	public static void pageReferesh() {
		driver.navigate().refresh();
	}

	public void verifyTheStatusCode(List<Map<String, String>> params) {
		json = response.then().statusCode(Integer.parseInt(params.get(0).get("StatusCode")));
		assertEquals(String.valueOf(response.getStatusCode()), params.get(0).get("StatusCode"));
	}

	public void verifyErrorCodeAndResponse(List<Map<String, String>> errorParams) {
		jsonPath = response.jsonPath();
		response.then().statusCode(Integer.parseInt(errorParams.get(0).get("StatusCode")));
		assertEquals(jsonPath.get(errorParams.get(0).get("ErrorKey")), errorParams.get(0).get("ErrorValue").toString());
	}

	public Response callCreateContactAndReturnResponse(String endpoint, CreateContact createContact) {
		response = RestAssured.given().header("Authorization", bearerToken).header("Content-Type", "application/json")
				.contentType(ContentType.JSON).accept(ContentType.JSON).body(createContact).when().post(endpoint).then()
				.extract().response();
		getContact = response.getBody().as(GetContact.class);
		return response;

	}

	public Response callGetContactAndReturnResponse(String endpoint) {
		return response = RestAssured.given().header("Authorization", bearerToken).when().log().all().get(endpoint)
				.then().extract().response();
	}

	public Response callDeleteContactAndReturnResponse(String endpoint) {
		return response = RestAssured.given().header("Authorization", bearerToken).when().delete(endpoint).then()
				.extract().response();
	}

	public Response callUpdateContactAndReturnResponse(String endpoint, CreateContact createContact) {
		return response = RestAssured.given().header("Authorization", bearerToken)
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(createContact).when().patch(endpoint).then().extract().response();
	}

	public static void loginPage() {
		driver.findElement(By.id("email")).sendKeys(getPropertyFileValue("username"));
		driver.findElement(By.id("password")).sendKeys(getPropertyFileValue("password"));
		driver.findElement(By.id("submit")).click();
	}

}
