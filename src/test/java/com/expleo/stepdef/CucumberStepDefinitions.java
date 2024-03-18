package com.expleo.stepdef;

import static io.restassured.RestAssured.baseURI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;

import com.expleo.base.BaseClass;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CucumberStepDefinitions extends BaseClass {
	

	@BeforeAll
	public static void beforeAll() throws IOException {
		baseURI = getPropertyFileValue("baseURI");
	}

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

	@Given("prepare the request body")
	public void prepare_the_request_body(List<Map<String,String>> requests) {
		
		for (Map<String, String> req : requests) {
			createRepReq.setDescription(req.get("Description"));
			createRepReq.setName(req.get("name"));
			createRepReq.setHomepage(req.get("homepage"));
			createRepReq.setHas_issues(Boolean.parseBoolean(req.get("has_issues")));
			createRepReq.setHas_projects(Boolean.parseBoolean(req.get("has_projects")));
			createRepReq.setHas_wiki(Boolean.parseBoolean(req.get("has_wiki")));
			createRepReq.setMyprivate(Boolean.parseBoolean(req.get("private")));
	    }
		
	}

	@When("getall repo endpoint is invoked")
	public void getall_repo_endpoint_is_invoked() {
		callGetallrepoAndReturnResponse();		
	}



	@When("create repo endpoint is invoked")
	public void create_repo_endpoint_is_invoked() throws IOException {
		callCreaterepoAndReturnResponse(createRepReq);
		
	}

	@When("update repo endpoint is invoked")
	public void update_repo_endpoint_is_invoked() throws IOException {
		callUpdaterepoAndReturnResponse(createRepReq);
		
	}


	@When("delete repo endpoint is invoked")
	public void delete_repo_endpoint_is_invoked() throws IOException {
		callDeleterepoAndReturnResponse();
	}

	@Given("prepare the request body for updating the repo")
	public void prepare_the_request_body_for_updating_the_repo(List<Map<String,String>> requests) {
		
		for (Map<String, String> req : requests) {
			createRepReq.setDescription(req.get("Description"));
			createRepReq.setName(req.get("name"));
			createRepReq.setHomepage(req.get("homepage"));
			createRepReq.setHas_issues(Boolean.parseBoolean(req.get("has_issues")));
			createRepReq.setHas_projects(Boolean.parseBoolean(req.get("has_projects")));
			createRepReq.setHas_wiki(Boolean.parseBoolean(req.get("has_wiki")));
			createRepReq.setMyprivate(Boolean.parseBoolean(req.get("private")));
	    }
	   
	}


}
