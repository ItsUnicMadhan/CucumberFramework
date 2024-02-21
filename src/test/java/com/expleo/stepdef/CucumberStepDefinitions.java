package com.expleo.stepdef;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class CucumberStepDefinitions {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification reqspec = new RequestSpecBuilder().build();
	JsonPath jsonPath;

	private String ENDPOINT_GET_BOOK_BY_ISBN = "https://www.googleapis.com/books/v1/volumes";
	


	@Given("prepare the request")
	public void prepare_the_request() {
		reqspec.queryParam("q", "isbn:9781451648546");
	}

	@When("api is invoked")
	public void api_is_invoked() {
			response=RestAssured.given().log().all(true).spec(reqspec).when().get(ENDPOINT_GET_BOOK_BY_ISBN).then().extract().response();
	}

	@Then("verify the status code")
	public void verify_the_status_code(List<Map<String,String>> params) {
		
		json =response.then().statusCode(Integer.parseInt(params.get(0).get("StatusCode")));
	}

	@Then("verify the error code and error response")
	public void verify_the_error_code_and_error_response(List<Map<String,String>> errorParams) {
		jsonPath=response.jsonPath();
		response.then().statusCode(Integer.parseInt(errorParams.get(0).get("StatusCode")));
		assertEquals(jsonPath.get(errorParams.get(0).get("ErrorKey")), errorParams.get(0).get("ErrorValue").toString());
	}

}

