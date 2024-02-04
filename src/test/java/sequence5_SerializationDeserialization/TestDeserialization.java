package sequence5_SerializationDeserialization;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.POJO.GetCourseResponse;

public class TestDeserialization {


	/*
	 * 
	 * Import jackson-databind for deserialization
	 * 
	 * Print course titles
	 * Print price
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
		GetCourseResponse getCourseResponse = given()
											.log().all()
											.queryParam("access_token", accessToken)
										.when()
											.get(getCourseResource)
										.then()
											.log().all()
											.extract().response().as(GetCourseResponse.class);
			
		System.out.println("response: " + getCourseResponse.getInstructor());
		
		int size = getCourseResponse.getCourses().getApi().size();
		for(int i=0; i< size; i++) {
			if(getCourseResponse.getCourses().getApi().get(i).getCourseTitle().equals("SoapUI Webservices testing")) {
				System.out.println("SoapUI Webservices testing price: " + getCourseResponse.getCourses().getApi().get(i).getPrice());
			}
		}
		
		size = getCourseResponse.getCourses().getWebAutomation().size();
		for(int i=0; i< size; i++) {
			System.out.println("Title: " + getCourseResponse.getCourses().getWebAutomation().get(i).getCourseTitle());
		}
		
		
		
		
		
		//Match course title with expected course title
		String[] expectedCourse = {"Selenium Webdriver Java", "Cypress", "Protractor"};		
		ArrayList<String> actualCourse = new ArrayList<String>();
 		size = getCourseResponse.getCourses().getWebAutomation().size();
		for(int i=0; i< size; i++) {			
			actualCourse.add(getCourseResponse.getCourses().getWebAutomation().get(i).getCourseTitle());
			
	
		}
		
		Assert.assertEquals(actualCourse, Arrays.asList(expectedCourse));

		
	}
	
	
}
