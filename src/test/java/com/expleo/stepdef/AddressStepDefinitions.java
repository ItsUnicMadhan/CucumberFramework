package com.expleo.stepdef;

import static io.restassured.RestAssured.baseURI;

import java.util.List;
import java.util.Map;

import org.junit.Assert;

import com.expleo.base.BaseClass;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AddressStepDefinitions extends BaseClass {

	@BeforeAll
	public static void beforeAll() {
		baseURI = getPropertyFileValue("baseURI");
		getDriver(getPropertyFileValue("browser"));
		maximize();
		loadURL(getPropertyFileValue("loginURL"));
		implicitwait();
		loginPage();
	}

	@Given("prepare the request body")
	public void prepare_the_request_body(List<Map<String, String>> request) {
		for (Map<String, String> req : request) {
			createAddress.setId(req.get("id"));
			createAddress.setName(req.get("name"));
			createAddress.setAddressLine1(req.get("addressLine1"));
			createAddress.setCity(req.get("city"));
			createAddress.setRegion(req.get("region"));
			createAddress.setCountry(req.get("country"));
			location.setLatitude(Double.valueOf(req.get("latitude")));
			location.setLatitude(Double.valueOf(req.get("longitude")));
			createAddress.setLocation(location);
		}
	}

	@When("create address endpoint is invoked")
	public void create_address_endpoint_is_invoked() {
		callCreateAddressAndReturnResponse(CREATE_ADDRESSES, createAddress);
		addressId = response.asString();
	}

	@Then("verify address created")
	public void verify_address_created() {
		Assert.assertEquals(getAddressText(), createAddress.getName());
	}

	@When("get address endpoint is invoked {string}")
	public void get_address_endpoint_is_invoked(String key) {
		pathParams(reqspec, key, addressId);
		callGetAddressAndReturnResponse(GET_ADDRESS);
	}

	@When("delete address endpoint is invoked {string}")
	public void delete_address_endpoint_is_invoked(String key) {
		pathParams(reqspec, key, addressId);
		callDeleteAddressAndReturnResponse(DELETE_ADDRESS);
	}

	@Then("verify address deleted")
	public void verify_address_deleted() {
		Assert.assertFalse(verifyDeleteAddress().contains(createAddress.getName()));
	}

	@Then("verify the status code")
	public void verify_the_status_code(List<Map<String, String>> params) {
		verifyTheStatusCode(params);
	}

	@Given("prepare the request")
	public void prepare_the_request() {
	}

	@AfterAll
	public static void afterAll() {
		logout();
		quitBrowser();
	}

}
