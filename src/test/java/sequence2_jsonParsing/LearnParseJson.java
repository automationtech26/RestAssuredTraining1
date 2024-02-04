package sequence2_jsonParsing;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.RequestPayload;

public class LearnParseJson {

	
	
	
	//Count number of courses
	@Test
	public void testNumberOfCourses() {		
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		System.out.println("count: " + jsonPath.getList("courses").size());
		jsonPath.getInt("courses.size()");
	}
	
	
	
	//Print purchase Amount
	@Test
	public void printPurchaseAmount() {		
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		System.out.println("PurchaseAmount: " + jsonPath.getString("dashboard.purchaseAmount"));
	}
	
	
	
	
	@Test
	public void printTitleOfFirstCourse() {
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		System.out.println("Title of first course: " + jsonPath.getString("courses.title[0]"));
	}
	
	
	
	@Test
	public void printAllCourseTitleAndPrice() {
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		int size = jsonPath.getList("courses.title").size();
		for(int i=0; i< size ; i++) {
			System.out.println("course title: " + jsonPath.getString("courses.title[" + i + "]"));
			System.out.println("course price: " + jsonPath.getString("courses.price[" + i + "]"));

		}		
	}
	
	
	
	@Test
	public void printNumberOfCopiesSoldRPA() {
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		int size = jsonPath.getList("courses.title").size();
		for(int i=0; i< size ; i++) {
			if(jsonPath.getString("courses.title[" + i + "]").equals("RPA")) {
				System.out.println("copies: " + jsonPath.getString("courses.copies[" + i + "]"));
			}
		}
	}
	
	
	
	
	
	@Test
	public void validateSumOfCoursePricesAndPurcaseAmount() {
		String response = RequestPayload.complexJsonExamplePayload();
		JsonPath jsonPath = new JsonPath(response);
		int size = jsonPath.getList("courses.price").size();
		int sum = 0;
		for(int i=0; i< size; i++) {
			sum = sum + jsonPath.getInt("courses.price[" + i + "]") * jsonPath.getInt("courses.copies[" + i + "]");
		}
		Assert.assertEquals(sum, jsonPath.getInt("dashboard.purchaseAmount"));
		
		
		
	}
	
	
	
	//Get JSON from file

		@Test
		public void testDataFromFile() throws IOException {
			
			RestAssured.baseURI = "https://rahulshettyacademy.com";
			String resource = "/Library/Addbook.php";
			
			given()
				.log().all()
				.header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "\\src\\test\\resources\\payload\\payloadFile.json"))))
			.when()
				.post(resource)
			.then()
				.log().all()
				.assertThat().statusCode(200);
			
			
		}
	
	
	
}
