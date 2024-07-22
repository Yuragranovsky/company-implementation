package telran.employees;

import java.io.*;
import java.util.*;

import org.json.JSONObject;

import telran.net.TcpClient;

import java.net.*;

public class CompanyProxy implements CompanyInterface {
	TcpClient tcpClient;
	
	public CompanyProxy(TcpClient tcpClient) {
		this.tcpClient = tcpClient;
	}

	@Override
	public Iterator<Employee> iterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void addEmployee(Employee empl) {
		HashMap<String, Object> map = new HashMap<>();
		empl.fillMap(map);
		tcpClient.sendAndGet("addEmployee", new JSONObject(map).toString());
	}

	@Override
	public Employee getEmployee(long id) {
		String employeeJSON = tcpClient.sendAndGet("getEmployee", "" + id);
		JSONObject jsonObj = new JSONObject(employeeJSON);
		try {
			Employee empl = (Employee) Class.forName(jsonObj.getString("className"))
				.getConstructor(JSONObject.class).newInstance(jsonObj);
			return empl;	
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public Employee removeEmployee(long id) {
		String employeeJSON = tcpClient.sendAndGet("removeEmployee", "" + id);
		JSONObject jsonObj = new JSONObject(employeeJSON);
		try {
			Employee empl = (Employee) Class.forName(jsonObj.getString("className"))
				.getConstructor(JSONObject.class).newInstance(jsonObj);
			return empl;	
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getDepartmentBudget(String department) {
		
		return Integer.parseInt(tcpClient.sendAndGet("getDepartmentBudget", department));
	}

	@Override
	public String[] getDepartments() {
		
		return tcpClient.sendAndGet("getDepartments", "").split(";");
	}

	@Override
	public Manager[] getManagersWithMostFactor() {
		
		return null;
	}

	@Override
	public void save() throws FileNotFoundException {

	}

	@Override
	public void restore() throws Exception {

	}

}
