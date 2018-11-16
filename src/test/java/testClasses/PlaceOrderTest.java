package testClasses;

import org.testng.annotations.Test;

import org.json.simple.JSONObject;
import org.testng.Assert;

import baseSetup.RestClient;
import io.restassured.response.Response;

public class PlaceOrderTest extends RestClient {
	// Verify the status code of the order with valid values
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyValidResponse", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		id = RestClient.getOrderId(res);
		RestClient.setId(id);
		Assert.assertEquals(201, res.getStatusCode());
	}

	// Verify the status code of the order with invalid values of origin or destination
	@Test(priority = 2)
	public void verifyInValidResponse() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyInValidResponse", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(503, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("errorServiceMessage"),
				RestClient.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid values of 3 coordinates
	@Test(priority = 2)
	public void verifyInValidResponsePayload() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyResponseWithInvalidPayload2", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("errorMessageInavlidResponse"),
				RestClient.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid payload
	@Test(priority = 2)
	public void verifyResponseWithInvalidPayload() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyResponseWithInvalidPayload", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("errorMessageInavlidResponse"),
				RestClient.getMessage(res.getBody().asString()));
	}

	// Verify driving distance between each 2 stops is in metres
	@Test(priority = 2)
	public void verifyDistance() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyDrivingDistance", "TestData.properties");
		String st[] = new String[2];
		st = RestClient.getAttributeValueDistance("drivingDistancesInMeters", obj);
		String dist[] = new String[2];
		dist[0] = RestClient.loadProperties("TestData.properties").getProperty("distance1");
		dist[1] = RestClient.loadProperties("TestData.properties").getProperty("distance2");
		int i = 0;
		for (String str : st) {
			Assert.assertEquals(str, dist[i]);
			++i;
		}
	}

	// Verify the count of driving distance array
	@Test(priority = 2)
	public void verifyDistanceValuesCount() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyDrivingDistanceCount", "TestData.properties");
		int count = RestClient.getDistanceValueCount("drivingDistancesInMeters", obj);
		Assert.assertEquals(String.valueOf(count),
				RestClient.loadProperties("TestData.properties").getProperty("count"));
	}

	// Verify the currency of fare according to calculations
	@Test(priority = 3)
	public void verifyFareCurrency() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyCurrency", "TestData.properties");
		String currency = RestClient.getCurrency(obj, "currency");
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("currency"), currency);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyAmount", "TestData.properties");
		String amount = RestClient.getCurrency(obj, "amount");
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("amount"), amount);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare9to5() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyAmount9to5", "TestData.properties");
		String amount = RestClient.getCurrency(obj, "amount");
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("amount9to5"), amount);
	}

	// Verify response when request is having past time
	@Test(priority = 3)
	public void VerifyBadRequest() throws Exception {
		JSONObject obj = RestClient.getPayload("VerifyBadRequest", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("messagePastOrder"),
				RestClient.getMessage(res.getBody().asString()));
	}

	// verify response when request is having longitude value 0
	@Test(priority = 3)
	public void VerifyBadRequestAttribute() throws Exception {
		JSONObject obj = RestClient.getPayload("VerifyAttributeBadRequest", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(RestClient.loadProperties("TestData.properties").getProperty("errorMessage"),
				RestClient.getMessage(res.getBody().asString()));
	}

	// verify response when request with future time also
	@Test(priority = 4)
	public void verifyValidResponseWithTime() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyValidResponseWithTime", "TestData.properties");
		Response res = RestClient.postRequest(obj);
		Assert.assertEquals(201, res.getStatusCode());
	}

}
