package testClasses;

import org.testng.annotations.Test;

import DTO.CommonTestDataDTO;
import baseSetup.APICalls;
import baseSetup.ResponseParser;
import baseSetup.RestClient;
import io.restassured.response.Response;

import org.testng.Assert;

public class FetchOrderTest extends RestClient {

	// Verify the status code is 200 with valid order
	@Test(priority = 1)
	public void getValidOrderResponse() throws Exception {
		Response res = APICalls.getResponse(String.valueOf(orderId));
		Assert.assertEquals(200, res.getStatusCode());
	}

	// Verify status code is 404 if order does not exist
	@Test(priority = 2)
	public void getInValidResponse() {
		String orderId = "0";
		Response res = APICalls.getResponse(orderId);
		Assert.assertEquals(404, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("messageOrderNotFound"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

}
