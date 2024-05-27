package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void BeforeScenario() throws IOException
	{
		//Write a code that gives us PlaceId 
		//Execute the code when PlaceId is Null
		stepDefinition m = new stepDefinition();
		if(stepDefinition.place_id==null)
		{
		m.add_place_payload_with("sumit","bangla","Howrah");
		m.user_calls_with_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("sumit", "GetPlaceAPI");
		}
		
	}

}
