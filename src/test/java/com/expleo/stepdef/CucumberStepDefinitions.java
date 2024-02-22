package com.expleo.stepdef;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.expleo.base.BaseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions extends BaseClass {

	@Given("prepare the request with query param value {string} and value {string}")
	public void prepare_the_request_with_query_param_value_and_value(String key, String value) {
		queryParams(reqspec, key, value);
	}

	@When("api is invoked")
	public void api_is_invoked() throws IOException {
		sendGetRequestReturnResponse(reqspec, "ENDPOINT_GET_BOOK_BY_ISBN");
	}

	@Then("verify the status code")
	public void verify_the_status_code(List<Map<String, String>> params) {

		verifyTheStatusCode(params);
	}

	@Then("verify the error code and error response")
	public void verify_the_error_code_and_error_response(List<Map<String, String>> errorParams) {
		verifyErrorCodeAndResponse(errorParams);
	}

	@Given("prepare the request")
	public void prepare_the_request() {

	}

	@When("post endpoint is invoked")
	public void post_endpoint_is_invoked() throws IOException {
		String requestBody = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";
		sendPostRequestReturnResponse(reqspec, requestBody, "REQ_RES_CREATE");
	}

}
