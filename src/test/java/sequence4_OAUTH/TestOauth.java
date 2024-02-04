package sequence4_OAUTH;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
public class TestOauth {

	
	/*
	 * OAUTH Types - for giving access token
	 * By client credentials - client ID, client secret
	 * By password type - username, password
	 * By authorization code
	 * 
	 * 
	 * 
	 * form params can be passed in body like we passed json
	 * 
	 * Send data as form parameters in post call
	 * 
	 * */
	
	
	
	@Test
	public void generateOauthToken() {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resource = "/oauthapi/oauth2/resourceOwner/token";
	
		HashMap<String, String> formMap = new HashMap<>();
		formMap.put("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com");
		formMap.put("client_secret", "erZOWM9g3UtwNRj340YYaK_W");
		formMap.put("grant_type", "client_credentials");
		formMap.put("scope", "trust");
		String response = given()
						.log().all()
						.formParams(formMap)
					.when()
						.post(resource)
					.then()
						.log().all()
						.assertThat().statusCode(200)
						.extract().response().asString();
	
	
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");
		
	
		
		
		String getCourseResource = "/oauthapi/getCourseDetails";
		response = given()
						.log().all()
						.queryParam("access_token", accessToken)
					.when()
						.get(getCourseResource)
					.then()
						.log().all()
						.extract().response().asString();
			
		System.out.println("response: " + response);
					
		
	}
	
}
