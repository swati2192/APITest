package testClasses;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Assert;
import org.testng.annotations.Test;

import baseSetup.RestClient;
import io.restassured.response.Response;

public class TakeOrderTest extends RestClient {
	String status = "take";

	// Verify response status code is 200 with valid order id
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		Response res = RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(200, res.getStatusCode());
	}

	// Verify response the status code is 404 if order does not exist
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		Assert.assertEquals(404, RestClient.getStatusCode("0", status));
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageOrderNotFound"),
				RestClient.getResponseValues("0", "message", status));
	}

	// Verify response the status code is 422 if with custom message if logic flow
	// is violated
	@Test(priority = 3)
	public void verifyInvalidFlowResponse() throws Exception {
		RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(422, RestClient.getStatusCode(String.valueOf(id), status));
	}

	// Verify the message if status is already "ONGOING" and again makes it's status
	// as ongoing
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessage() throws Exception {
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("message"),
				RestClient.getResponseValues(String.valueOf(id), "message", status));
	}

}
