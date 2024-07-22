package telran.employees.net;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lombok.AllArgsConstructor;
import telran.employees.Company;
import telran.employees.Employee;
import telran.employees.Manager;
import telran.net.Protocol;
import telran.net.Response;
import telran.net.ResponseCode;

@AllArgsConstructor
public class CompanyProtocol implements Protocol {
	Company company;

	@Override
	public Response getResponse(String type, String requestData) {
		Response response = null;
		try {
			response = switch (type) {
				case "addEmployee" -> addEmployee(requestData);
				case "getEmployee" -> getEmployee(requestData);
				case "removeEmployee" -> removeEmployee(requestData);
				case "getDepartmentBudget" -> getDepartmentBudget(requestData);
				case "getManagersWithMostFactor" -> getManagersWithMostFactor(requestData);
				case "getDepartments" -> getDepartments(requestData);
				default -> wrongTypeResponse(type);
			};
		} catch (Exception e) {
			response = wrongDataResponse(e);
		}
		return response;
	}

	private Response getDepartments(String requestData) {
		String[] departments = company.getDepartments();
		return new Response(ResponseCode.OK, String.join(";", departments));
	}

	private Response wrongDataResponse(Exception e) {
		return new Response(ResponseCode.WRONG_REQUEST_DATA, e.getMessage());
	}

	Response getManagersWithMostFactor(String requestData) {
		Manager[] managers = company.getManagersWithMostFactor();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("responseCode", ResponseCode.OK);
		jsonObject.put("responseData", Arrays.asList(toJSONStringsArray(managers)));
		return new Response(ResponseCode.OK, new JSONArray(Arrays.asList(toJSONStringsArray(managers))).toString());
	}

	private String[] toJSONStringsArray(Manager[] managers) {
		String[] result = new String[managers.length];
		for (int i = 0; i < managers.length; i++) {
			result[i] = toJSONString(managers[0]);
		}
		return result;
	}

	private String toJSONString(Manager manager) {
		Map<String, Object> map = new HashMap<>();
		manager.fillMap(map);
		return new JSONObject(map).toString();
	}

	Response getDepartmentBudget(String requestData) {
		int budget = company.getDepartmentBudget(requestData);
		
		return new Response(ResponseCode.OK, "" + budget);
	}

	Response removeEmployee(String requestData) {
		long id = Long.parseLong(requestData);
		Employee empl = company.removeEmployee(id);
		Map<String, Object> map = new HashMap<>();
		empl.fillMap(map);
		
		return new Response(ResponseCode.OK, new JSONObject(map).toString());
	}

	Response getEmployee(String requestData) {
		long id = Long.parseLong(requestData);
		Employee empl = company.getEmployee(id);
		Map<String, Object> map = new HashMap<>();
		empl.fillMap(map);
		return new Response(ResponseCode.OK, new JSONObject(map).toString());
	}

	Response addEmployee(String requestData) {
		JSONObject jsonObj = new JSONObject(requestData);
		try {
			Employee empl = (Employee) Class.forName(jsonObj.getString("className"))
				.getConstructor(JSONObject.class).newInstance(jsonObj);
				company.addEmployee(empl);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		return new Response(ResponseCode.OK, "");
	}

	Response wrongTypeResponse(String type) {
		
		return new Response(ResponseCode.WRONG_REQUEST_TYPE, type);

	}

}
