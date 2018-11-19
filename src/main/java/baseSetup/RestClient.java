package baseSetup;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import DTO.CommonTestDataDTO;

public class RestClient {

	public static String URI;
	static String propertyValue;
	public static String orderId;

	public static void setOrderId(String id) {
		orderId = id;
	}

	public static JSONObject getPayload(String tcName) throws Exception {
		JSONObject object = null;
		CommonTestDataDTO.initializeProperties();
		URI = CommonTestDataDTO.getConfigPropertyValue("URI") + CommonTestDataDTO.getConfigPropertyValue("APIEndPoint");
		propertyValue = CommonTestDataDTO.getTestDataPropertyValue(tcName);
		JSONParser jsonParser = new JSONParser();
		object = (JSONObject) jsonParser.parse(propertyValue);
		return object;
	}

}
