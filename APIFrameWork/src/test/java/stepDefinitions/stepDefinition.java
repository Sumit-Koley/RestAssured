package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.Utils;
import resources.testDataBuild;

public class stepDefinition extends Utils {
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response;
	testDataBuild Data = new testDataBuild();
	static String place_id;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

	 resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		 res=given().spec(requestSpecification())
		.body(Data.addPlacePayload(name,language,address));
	}
	@When("User Calls {string} With {string} http request")
	public void user_calls_with_http_request(String resource, String method) {
		
		//Constructor will be called with the value of response which we pass from feature file
	APIResources resourceAPI =	APIResources.valueOf(resource);
	System.out.println(resourceAPI.getResource());
	
	resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
	if(method.equalsIgnoreCase("POST"))
		 response =res.when().post(resourceAPI.getResource());
	else if (method.equalsIgnoreCase("GET"))
		 response =res.when().get(resourceAPI.getResource());
				
	}
	@Then("The API call got success and Status code is {int}")
	public void the_api_call_got_success_and_status_code_is(Integer int1) {

		assertEquals(response.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void is_response_body_is(String keyvalue, String Expectedvalue) {

		
		assertEquals(getJsonPath(response,keyvalue).toString(),Expectedvalue);
	}
	@Then("verify place_Id Created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		//request Spec
		place_id = getJsonPath(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id",place_id);
		user_calls_with_http_request(resource,"GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
	}

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
		res =given().spec(requestSpecification()).body(Data.deletePlacePayload(place_id));
	}

}
