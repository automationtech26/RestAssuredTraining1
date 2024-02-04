package sequence6_EcommerceAPIExample;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import payload.POJO.CreateProductResponse;
import payload.POJO.LoginRequest;
import payload.POJO.LoginResponse;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.HashMap;

/*
 * 
 * Usage of JWT session token
 * Usage of multiPart
 * Relaxed HTTPS   	.relaxedHTTPSValidation()  by passes invalid SSL certification
 * 
 * */

public class TestEcommerce {

		
	
	@Test
	public void createProduct() {
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String resource = "/api/ecom/auth/login";
		
		LoginRequest loginRequest = LoginRequest.builder()
									.userEmail("pratyush.kumar26@gmail.com")
									.userPassword("India@123")
									.build();		
		
		LoginResponse loginResponse = given()
										.log().all()
										.relaxedHTTPSValidation()
										.header("Content-Type", "application/json")
										.body(loginRequest)
									.when()
										.post(resource)
									.then()
										.log().all()
										.assertThat().statusCode(200)
										.extract().response().as(LoginResponse.class);
		
		System.out.println("loginResponse token: " + loginResponse.getToken());
		System.out.println("loginResponse userId: " + loginResponse.getUserId());
		
		
		
		
		
		
		
		//Create Product
		resource = "/api/ecom/product/add-product";
		
		HashMap<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", loginResponse.getToken());
		
	
		HashMap<String, Object> formMap = new HashMap<>();
		formMap.put("productName", "qwerty");
		formMap.put("productAddedBy", loginResponse.getUserId());
		formMap.put("productCategory", "fashion");
		formMap.put("productSubCategory", "shirts");
		formMap.put("productPrice", "11500");
		formMap.put("productDescription", "Addias Originals");
		formMap.put("productFor", "men");
	//	formMap.put("productImage", new File(System.getProperty("user.dir") + "\\src\\test\\resources\\screenshot\\forCreateProduct.png"));

		
		CreateProductResponse createProductResponse = given()
							.log().all()
							.headers(headerMap)
							.formParams(formMap)
							.multiPart("productImage", new File(System.getProperty("user.dir") + "\\src\\test\\resources\\screenshot\\forCreateProduct.png"))
						.when()
							.post(resource)
						.then()
							.log().all()
							.assertThat().statusCode(201)
							.extract().response().as(CreateProductResponse.class);
						
			
						
						
						
						
		//Delete
		resource = "/api/ecom/product/delete-product/{productId}";	
          
					given()
						.log().all()
		            	.headers(headerMap)
		            	.pathParam("productId", createProductResponse.getProductId())
		            .when()
		            	.delete(resource)
		            .then()
		            	.log().all()
		            	.assertThat().statusCode(200);
	}
}
