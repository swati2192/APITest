package testClasses;

import org.testng.annotations.Test;

import DTO.CommonTestDataDTO;

import org.json.simple.JSONObject;
import org.testng.Assert;

import baseSetup.APICalls;
import baseSetup.ResponseParser;
import baseSetup.RestClient;
import io.restassured.response.Response;

public class PlaceOrderTest extends RestClient {
	// Verify the status code of the order with valid values
	@Test(priority = 1)
	public void verifyValidResponse() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyValidResponse");
		Response res = APICalls.postRequest(obj);
		orderId = ResponseParser.getOrderId(res);
		RestClient.setOrderId(orderId);
		Assert.assertEquals(201, res.getStatusCode());
	}

	// Verify the status code of the order with invalid values of origin or
	// destination
	@Test(priority = 2)
	public void verifyInValidResponse() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyInValidResponse");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(503, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("errorServiceMessage"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid values of 3 coordinates
	@Test(priority = 2)
	public void verifyInValidResponsePayload() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyResponseWithInvalidPayload2");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("errorMessageInavlidResponse"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify the status code of the order with invalid payload
	@Test(priority = 2)
	public void verifyResponseWithInvalidPayload() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyResponseWithInvalidPayload");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("errorMessageInavlidResponse"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// Verify driving distance between each 2 stops is in integers
	@Test(priority = 2)
	public void verifyDistance() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyDrivingDistance");
		String st[] = new String[2];
		st = ResponseParser.getAttributeValueDistance(obj,"drivingDistancesInMeters");
		String dist[] = new String[2];
		dist[0] = CommonTestDataDTO.getTestDataPropertyValue("distance1");
		dist[1] = CommonTestDataDTO.getTestDataPropertyValue("distance2");
		int i = 0;
		for (String str : st) {
			Assert.assertEquals(str, dist[i]);
			++i;
		}
	}

	// Verify the count of driving distance array
	@Test(priority = 2)
	public void verifyDistanceValuesCount() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyDrivingDistanceCount");
		int count = ResponseParser.getDistanceValueCount(obj,"drivingDistancesInMeters");
		Assert.assertEquals(String.valueOf(count), CommonTestDataDTO.getTestDataPropertyValue("count"));
	}

	// Verify the currency of fare according to calculations
	@Test(priority = 3)
	public void verifyFareCurrency() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyCurrency");
		Response res=APICalls.postRequest(obj);
		String currency = ResponseParser.getFareValues(res.getBody().asString(), "currency");
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("currency"), currency);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyAmount");
		Response res=APICalls.postRequest(obj);
		String amount = ResponseParser.getFareValues(res.getBody().asString(), "amount");
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("amount"), amount);
	}

	// Verify trip fare when traveling time is not between 9PM to 5AM
	@Test(priority = 3)
	public void verifyTripFare9to5() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyAmount9to5");
		Response res=APICalls.postRequest(obj);
		String amount = ResponseParser.getFareValues(res.getBody().asString(), "amount");
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("amount9to5"), amount);
	}

	// Verify response when request is having past time
	@Test(priority = 3)
	public void VerifyBadRequest() throws Exception {
		JSONObject obj = RestClient.getPayload("VerifyBadRequest");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("messagePastOrder"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// verify response when request is having longitude value 0
	@Test(priority = 3)
	public void VerifyBadRequestAttribute() throws Exception {
		JSONObject obj = RestClient.getPayload("VerifyAttributeBadRequest");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(400, res.getStatusCode());
		Assert.assertEquals(CommonTestDataDTO.getTestDataPropertyValue("errorMessage"),
				ResponseParser.getMessage(res.getBody().asString()));
	}

	// verify response when request with future time also
	@Test(priority = 4)
	public void verifyValidResponseWithTime() throws Exception {
		JSONObject obj = RestClient.getPayload("verifyValidResponseWithTime");
		Response res = APICalls.postRequest(obj);
		Assert.assertEquals(201, res.getStatusCode());
	}

}
