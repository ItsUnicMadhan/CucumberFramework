package com.expleo.base;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.expleo.endpoint.Endpoints;
import com.expleo.model.CreateAddress;
import com.expleo.model.Location;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class BaseClass implements Endpoints {

	public static WebDriver driver;
	public static Response response;
	public static ValidatableResponse json;
	public RequestSpecification reqspec = new RequestSpecBuilder().build();
	public static JsonPath jsonPath;
	public static Properties properties;
	public static CreateAddress createAddress = new CreateAddress();
	public static Location location = new Location();
	public static String addressId;

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

	public void queryParams(RequestSpecification reqspec, String key, String value) {
		reqspec.queryParam(key, value);
	}

	public void pathParams(RequestSpecification reqspec, String key, String value) {
		reqspec.pathParam(key, value);
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

	public Response callCreateAddressAndReturnResponse(String endpoint, CreateAddress createAddress) {
		return response = RestAssured.given().header("Authorization", "Basic " + getPropertyFileValue("token"))
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(createAddress).when().post(endpoint).then().extract().response();
	}

	public Response callGetAddressAndReturnResponse(String endpoint) {
		return response = RestAssured.given().header("Authorization", "Basic " + getPropertyFileValue("token")).when()
				.log().all().spec(reqspec).get(endpoint).then().extract().response();
	}

	public Response callDeleteAddressAndReturnResponse(String endpoint) {
		return response = RestAssured.given().header("Authorization", "Basic " + getPropertyFileValue("token")).when()
				.spec(reqspec).delete(endpoint).then().extract().response();
	}

	public static void loginPage() {
		driver.findElement(By.xpath("//div[@class='input-group login-margin-bottom']//following-sibling::input"))
				.sendKeys(getPropertyFileValue("username"));
		driver.findElement(By.xpath("//div[@class='input-group']//following-sibling::input"))
				.sendKeys(getPropertyFileValue("password"));
		driver.findElement(By.xpath(
				"//button[@class='btn d-flex justify-content-center align-items-center w-100 h-100 btn-primary btn-action-primary']"))
				.click();
	}

	public static String getAddressText() {
		pageReferesh();
		driver.findElement(By.xpath("//div[@class='personal-label']")).click();
		return driver.findElement(By.xpath("//div[@class='row no-gutters']/child::div[2]/child::h3")).getText();
	}

	public static String verifyDeleteAddress() {
		pageReferesh();
		return driver.findElement(By.xpath("//div[@class='d-flex flex-column flex-sm-row']//child::div//child::h2"))
				.getText();
	}

	public static void logout() {
		driver.findElement(By.xpath("//a[@id='logout-trigger']//child::icon")).click();
	}

}
