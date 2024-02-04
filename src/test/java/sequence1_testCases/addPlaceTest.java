package sequence1_testCases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.RequestPayload;

public class addPlaceTest {

	/*
	 * given ----pass headers, cookies, body
	 * 
	 * when ---- put, post, get, delete
	 * then ----- add assertions
	 * 
	 * */
	
	
	
	
	
	
	/*
	 * Add place --> Update place API with new Address --> Get place API to retrive
	 * 
	 * */
	
	@Test
	public void testAddPlace(){		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		//Add place
		String resource = "maps/api/place/add/json";
		
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
					
		given()
			.log().all()
			.queryParam("key", "qaclick123")
			.headers(headerMap)
			.body(RequestPayload.addPlace())			
		 .when()
			.post(resource)
		 .then()
			.log().all()
			.assertThat().
				statusCode(200)
				.body("scope", equalTo("APP"))
				.header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();
		
		
	
		
		
	}
	
	
	
	
	
	@Test
	public void testPutPlace(){		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		//Add place
		String resource = "maps/api/place/add/json";
		
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
					
		String response = given()
							.log().all()
							.queryParam("key", "qaclick123")
							.headers(headerMap)
							.body(RequestPayload.addPlace())			
						 .when()
							.post(resource)
						 .then()
							.log().all()
							.assertThat().
								statusCode(200)
								.body("scope", equalTo("APP"))
								.header("Server", "Apache/2.4.52 (Ubuntu)")
								.extract().response().asString();
		
		
		//JsonPath will convert response String to Json
		
		JsonPath jsonPath = new JsonPath(response);
		String placeId = jsonPath.getString("place_id");
		
		
		//Update place
		resource = "maps/api/place/update/json";
		
		response = given()
					.log().all()
					.headers(headerMap)
					.queryParam("key", "qaclick123")
					.body(RequestPayload.updatePlace(placeId))
				.when()
					.put(resource)
				.then()
					.log().all()
					.assertThat().statusCode(200)
					.body("msg", equalTo("Address successfully updated"))
					.extract().response().asString();
		
		
		jsonPath = new JsonPath(response);
		jsonPath.getString("msg");
		
		
	}
	
	
	
	
	
	
	@Test
	public void testGetPlace(){		
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		//Add place
		String resource = "maps/api/place/add/json";
		
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Content-Type", "application/json");
					
		String response = given()
							.log().all()
							.queryParam("key", "qaclick123")
							.headers(headerMap)
							.body(RequestPayload.addPlace())			
						 .when()
							.post(resource)
						 .then()
							.log().all()
							.assertThat().
								statusCode(200)
								.body("scope", equalTo("APP"))
								.header("Server", "Apache/2.4.52 (Ubuntu)")
								.extract().response().asString();
		
		
		//JsonPath will convert response String to Json
		
		JsonPath jsonPath = new JsonPath(response);
		String placeId = jsonPath.getString("place_id");
		
		
		
		//GetPlace
		
		HashMap<String, String> queryParam = new HashMap<>();
		queryParam.put("key", "qaclick123");
		queryParam.put("place_id", placeId);
		
		response = given()
					.log().all()
					.headers(headerMap)
					.queryParams(queryParam)
				.when()
					.get("maps/api/place/get/json")
				.then()
					.log().all()
					.assertThat().statusCode(200)
					.extract().response().asString();
			
		
		jsonPath = new JsonPath(response);
		Assert.assertEquals(jsonPath.getString("website"), "http://google.com");
		
	}
	
	
}
