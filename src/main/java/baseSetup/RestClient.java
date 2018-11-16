package baseSetup;

import static io.restassured.RestAssured.get;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestClient {

	public static String URI = getValue("URI", "config.properties") + "/v1/orders";

	public static String id;

	public static void setId(String num) {
		id = num;
	}

	public static Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		try {

			prop.load(new FileReader(System.getProperty("user.dir") + "/Resources/" + fileName));

		} catch (FileNotFoundException e) {
			throw new RuntimeException("Properties file not found ");
		} catch (IOException e) {
			throw new RuntimeException("Unable to load properties file: ");
		}
		return prop;

	}

	public static String getValue(String Key, String fileName) {

		String value = loadProperties(fileName).getProperty(Key);

		return value;
	}

	public static JSONObject getPayload(String tcName, String fileName) throws Exception {
		JSONObject object = null;

		String proValue = getValue(tcName, fileName);

		JSONParser jsonParser = new JSONParser();
		object = (JSONObject) jsonParser.parse(proValue);

		return object;
	}

	public static Response updateResponse(String orderId, String status) {
		String URL = URI + "/" + orderId + "/" + status;
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		Response res = request.put(URL);
		return res;
	}

	public static int getStatusCode(String orderId, String status) {
		int code = 0;
		Response res = updateResponse(orderId, status);
		code = res.getStatusCode();
		return code;
	}

	public static String getResponseValues(String orderId, String attribute, String statusURI) {
		String strJson = updateResponse(orderId, statusURI).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get(attribute);
		String status = value.toString();
		return status;
	}

	public static Response getResponse(String orderId) {
		String URL = URI + "/" + orderId;
		Response res = get(URL);
		return res;
	}

	public static Response postRequest(JSONObject obj) {
		RequestSpecification req = RestAssured.given();
		req.header("Content-Type", "application/json");
		req.body(obj.toString());
		Response res = req.post(URI);
		return res;
	}

	public static String[] getAttributeValueDistance(String attribute, JSONObject obj) {
		String strJson = postRequest(obj).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get("drivingDistancesInMeters");
		String st = value.toString();
		st.substring(1, st.length() - 1);
		String s1[] = st.substring(1, st.length() - 1).split(",");
		return s1;
	}

	public static int getDistanceValueCount(String attribute, JSONObject obj) {
		String strJson = postRequest(obj).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get("drivingDistancesInMeters");
		String st = value.toString();
		st.substring(1, st.length() - 1);
		String s1[] = st.substring(1, st.length() - 1).split(",");
		return s1.length;
	}

	public static String getOrderId(Response res) {
		String str = res.getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(str);
		Object value = object.get("id");
		String id = value.toString();
		return id;
	}

	public static String getCurrency(JSONObject obj, String attribute) {
		String strJson = postRequest(obj).getBody().asString();
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get("fare");
		String fare = value.toString();
		JSONObject object2 = (JSONObject) JSONValue.parse(fare);
		Object value2 = object2.get(attribute);
		return value2.toString();
	}

	public static String getMessage(String strJson) {
		JSONObject object = (JSONObject) JSONValue.parse(strJson);
		Object value = object.get("message");
		String message = value.toString();
		return message.toString();
	}
}
