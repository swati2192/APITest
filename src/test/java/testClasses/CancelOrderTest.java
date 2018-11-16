package testClasses;

import org.testng.annotations.Test;

import org.json.simple.JSONObject;
import org.testng.Assert;
import baseSetup.RestClient;
import io.restassured.response.Response;

public class CancelOrderTest extends RestClient {
	String status = "cancel";

	// Verify response status code is 200 with valid order id
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyValidResponse", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		id = RestClient.getOrderId(res);
		RestClient.setId(id);
		Response resCancel = RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(200, resCancel.getStatusCode());
	}

	// Verify response the status code is 404 if order does not exist
	@Test(priority = 2)
	public void verifyInValidOrderResponse() throws Exception {
		Response res = RestClient.updateResponse(String.valueOf(id) + 1050, status);
		Assert.assertEquals(404, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageOrderNotFound"),
				RestClient.getResponseValues(String.valueOf(id) + 1050, "message", status));
	}

	// Verify the response status code if status is already "CANCELLED" and user
	// tries to make it "COMPLETE"
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessageForCancelled() throws Exception {
		Response res = RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messageComplete"),
				RestClient.getResponseValues(String.valueOf(id), "message", "complete"));
		Assert.assertEquals(422, RestClient.updateResponse(String.valueOf(id), "complete").getStatusCode());
	}

	// Verify the response status code if status is already "CANCELLED" and user
	// tries to make it "ONGOING"
	@Test(priority = 3)
	public void verifyInvalidFlowResponseMessageForOngoing() throws Exception {
		RestClient.updateResponse(String.valueOf(id), status);
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("message"),
				RestClient.getResponseValues(String.valueOf(id), "message", "take"));
		Assert.assertEquals(422, RestClient.updateResponse(String.valueOf(id), "take").getStatusCode());
	}
}
