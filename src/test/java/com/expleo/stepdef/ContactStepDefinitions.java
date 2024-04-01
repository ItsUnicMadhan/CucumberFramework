package com.expleo.stepdef;

import static io.restassured.RestAssured.baseURI;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.openqa.selenium.By;

import com.expleo.base.BaseClass;

import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ContactStepDefinitions extends BaseClass {

	@BeforeAll
	public static void beforeAll() {
		baseURI = getPropertyFileValue("baseURI");
		getDriver(getPropertyFileValue("browser"));
		maximize();
		loadURL(getPropertyFileValue("baseURI"));
		implicitwait();
		loginPage();
		networkDetails();
	}

	@Given("prepare the request body")
	public void prepare_the_request_body(List<Map<String, String>> request) {
		for (Map<String, String> req : request) {
			createContact.setFirstName(req.get("firstName"));
			createContact.setLastName(req.get("lastName"));
		}

	}

	@When("create contact endpoint is invoked")
	public void create_contact_endpoint_is_invoked() {
		callCreateContactAndReturnResponse(endpoint, createContact);
	}

	@Then("verify contact created")
	public void verify_contact_created() {
		pageReferesh();
		Assert.assertTrue((driver.findElement(By.xpath("//*[@id=\"myTable\"]/tr/td[2]")).getText())
				.contains(createContact.getLastName()));
	}

	@When("get contact endpoint is invoked")
	public void get_contact_endpoint_is_invoked() {
		callGetContactAndReturnResponse(endpoint + getContact.get_id());
	}

	@Given("prepare the request body for updating the contact")
	public void prepare_the_request_body_for_updating_the_contact(List<Map<String, String>> request) {
		for (Map<String, String> req : request) {
			createContact.setLastName(req.get("lastName"));
		}
	}

	@When("update contact endpoint is invoked")
	public void update_contact_endpoint_is_invoked() {
		callUpdateContactAndReturnResponse(endpoint + getContact.get_id(), createContact);
	}

	@Then("verify contact updated")
	public void verify_contact_updated() {
		pageReferesh();
		Assert.assertTrue((driver.findElement(By.xpath("//*[@id=\"myTable\"]/tr/td[2]")).getText())
				.contains(createContact.getLastName()));
	}

	@When("delete contact endpoint is invoked")
	public void delete_contact_endpoint_is_invoked() {
		callDeleteContactAndReturnResponse(endpoint + getContact.get_id());
	}

	@Then("verify contact deleted")
	public void verify_contact_deleted() {
		pageReferesh();
		Assert.assertFalse((driver.findElement(By.xpath("/html/body/div/div/table/thead")).getText())
				.contains(createContact.getLastName()));
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
		driver.findElement(By.id("logout")).click();
		quitBrowser();
	}

}
