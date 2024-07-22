package telran.net;

import org.json.JSONObject;

public interface Protocol {
	Response getResponse(String type, String requestData);
default String getResponseWithJSON(String requestJSON) {
	JSONObject jsonRequestObject = new JSONObject(requestJSON);
	String type = jsonRequestObject.getString("requestType");
	String requestData = jsonRequestObject.getString("requestData");
	
	Response response = getResponse(type, requestData);
	JSONObject responseJSONObj = new JSONObject();
	responseJSONObj.put("responseData", response.responseData());
	responseJSONObj.put("responseCode", response.code());
	return responseJSONObj.toString();
}
}
