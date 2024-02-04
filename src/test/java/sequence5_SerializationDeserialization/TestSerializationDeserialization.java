package sequence5_SerializationDeserialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import payload.POJO.AddPlaceRequest;
import payload.POJO.AddPlaceResponse;
import payload.POJO.Location;

public class TestSerializationDeserialization {

	
	@Test
	public void testAddBook() {
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resource = "/maps/api/place/add/json";
		
		Location location = Location.builder()
								.lat(-38.383494)
								.lng(33.427362)
								.build();
		
		ArrayList<String> types = new ArrayList<String>();
		types.addAll(Arrays.asList("shoe park", "shop"));
		
		
		AddPlaceRequest addPlace = AddPlaceRequest.builder()
					.location(location)
					.accuracy(50)
					.name("Frontline house")
					.phone_number("(+91) 983 893 3937")
					.address("29, side layout, cohen 09")
					.types(types)
					.website(resource)
					.language(resource)
					.build();
		
		AddPlaceResponse addPlaceResponse = given()
						.log().all()
						.queryParam("key", "qaclick123")
						.body(addPlace)
					.when()
						.post(resource)
					.then()
						.log().all()
						.assertThat().statusCode(200)
						.extract().response().as(AddPlaceResponse.class);
		
		
		Assert.assertEquals(addPlaceResponse.getStatus(), "OK");
		Assert.assertEquals(addPlaceResponse.getScope(), "APP");
		
		
		
		
		
		
	}
	
	
	
}
