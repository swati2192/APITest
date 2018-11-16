package testClasses;

import org.testng.annotations.Test;
import org.testng.Assert;

import baseSetup.RestClient;
import io.restassured.response.Response;

public class CompleteOrderTest extends RestClient {
	String status = "complete";

	// Verify response status code is 200 with valid order id
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		Response res = RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(200, res.getStatusCode());
	}

	// Verify response status code is 404 with invalid order id
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		Response res = RestClient.updateResponse("0", status);
		Assert.assertEquals(404, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageOrderNotFound"),
				RestClient.getResponseValues("0", "message", status));
	}

	// Verify response the status code is 422 if user tries to complete the order more than 1 time
	@Test(priority = 3)
	public void verifyInvalidFlowResponse() throws Exception {
		RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(422, RestClient.getStatusCode(String.valueOf(id), status));
	}

	// Verify response message if order is completed twice
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessage() throws Exception {
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageComplete"),
				RestClient.getResponseValues(String.valueOf(id), "message", "complete"));
	}

	// Verify the message if status is already "COMPLETED" and user tries to make it
	// "Cancelled"
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessageForCancelled() throws Exception {
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageAlreadyComplete"),
				RestClient.getResponseValues(String.valueOf(id), "message", "cancel"));
		Assert.assertEquals(422, RestClient.getStatusCode(String.valueOf(id), status));
	}

	// Verify the message if status is already "COMPLETED" and user tries to make it
	// "ONGOING"
	@Test(priority = 4)
	public void verifyInvalidFlowResponseMessageForOngoing() throws Exception {
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("message"),
				RestClient.getResponseValues(String.valueOf(id), "message", "take"));
		Assert.assertEquals(422, RestClient.getStatusCode(String.valueOf(id), status));
	}
}
