package sequence3_HandlingDynamicJson;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import payload.RequestPayload;

public class TestLibraryAPI {

	
	
	@DataProvider(name = "addBook")
	public Object[][] dataProviderAddBook() {
		Object[][] obj = new Object[2][2];
		obj[0][0] = "test12345";
		obj[0][1] = "123456";
		obj[1][0] = "test12346";
		obj[1][1] = "1234567";
		return obj;
	}
	
	
	@Test(dataProvider = "addBook")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resource = "/Library/Addbook.php";
	//	String isbn = "test12345";
	//	String aisle = "123456";
		String response = given()
					.log().all()
					.header("Content-Type", "application/json")
					.body(RequestPayload.addBook(isbn, aisle))
				.when()
					.post(resource)
				.then()
					.log().all()
					.assertThat().statusCode(200)
					.extract().response().asString();
		
		JsonPath jsonPath = new JsonPath(response);
		String id = jsonPath.getString("ID");
		
		
		
		
		String deleteResource = "/Library/DeleteBook.php";
		given()
			.log().all()
			.header("Content-Type", "application/json")
			.body(RequestPayload.deletePayload(id))
		.when()
			.post(deleteResource)
		.then()
			.log().all()
			.assertThat().statusCode(200)
			.body("msg", equalTo("book is successfully deleted"));

	}
	
	
	
	
	
	
	
	
	
	
}
