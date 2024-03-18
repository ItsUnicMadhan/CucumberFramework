package com.expleo.base;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.expleo.model.CreateRepoReq;
import com.expleo.utils.RestUtils;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.expleo.*;

public class BaseClass extends RestUtils{

	public Response response;
	public ValidatableResponse json;
	public RequestSpecification reqspec = new RequestSpecBuilder().build();
	public JsonPath jsonPath;
	public CreateRepoReq createRepReq= new CreateRepoReq();

	public static String getPropertyFileValue(String value) throws IOException {
		FileInputStream file = new FileInputStream("src\\test\\resources\\app.properties");
		Properties p = new Properties();
		p.load(file);
		return p.getProperty(value);
	}

	public void queryParams(RequestSpecification reqspec, String key, String value) {
		reqspec.queryParam(key, value);
	}

	public void pathParams(RequestSpecification reqspec, String key, String value) {
		reqspec.pathParam(key, value);
	}

	public Response sendGetRequestReturnResponse(RequestSpecification reqspec, String endpoint) throws IOException {

		return response = RestAssured.given().log().all(true).spec(reqspec).when().get(getPropertyFileValue(endpoint))
				.then().extract().response();
	}

	public Response sendPostRequestReturnResponse(RequestSpecification reqspec, String requestBody, String endpoint) throws IOException {
		 
		return response = RestAssured.given().log().all(true).spec(reqspec).when().body(requestBody)
				.post(getPropertyFileValue(endpoint))
				.then().extract().response();
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
	

	public Response callGetallrepoAndReturnResponse() {
		response = RestAssured.given().spec(reqspec).when().log().all().get("users/ItsUnicMadhan/repos").then().extract().response();
		System.out.println(response.asPrettyString());
		return response;
		
		
	}
	
	public Response callCreaterepoAndReturnResponse(CreateRepoReq createRepoReq) throws IOException {
		response = RestAssured.given().header("Authorization", "Bearer " + decodeToken(getPropertyFileValue("token")))
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(createRepoReq).when().post("user/repos").then().extract().response();
		System.out.println(response.asPrettyString());
		return response;
		
	}
	

	public Response callDeleterepoAndReturnResponse() throws IOException {
		response = RestAssured.given().header("Authorization", "Bearer " + decodeToken(getPropertyFileValue("token"))).when()
		.delete("repos/ItsUnicMadhan/RESTAssured").then().extract().response();
		System.out.println(response.asPrettyString());
		return response;
		
	}
	
	public Response callUpdaterepoAndReturnResponse(CreateRepoReq createRepoReq) throws IOException {
		response = RestAssured.given().header("Authorization", "Bearer " + decodeToken(getPropertyFileValue("token")))
				.header("Content-Type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(createRepoReq).when()
				.patch("repos/ItsUnicMadhan/REST").then().extract().response();
		System.out.println(response.asPrettyString());
		return response;
		
	}
}
