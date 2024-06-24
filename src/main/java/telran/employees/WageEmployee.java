package telran.employees;

import java.util.Map;

import org.json.JSONObject;

public class WageEmployee extends Employee {
	private int hours;
	private int wage; //one hour salary cost
	public WageEmployee(long id, int basicSalary, String department, int hours, int wage) {
		super(id, basicSalary, department);
		this.hours = hours;
		this.wage = wage;
	}
	public WageEmployee(JSONObject jsonObj) {
		super(jsonObj);
		hours = jsonObj.getInt("hours");
		wage = jsonObj.getInt("wage");
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getWage() {
		return wage;
	}
	public void setWage(int wage) {
		this.wage = wage;
	}
	@Override
	public int computeSalary() {
		return super.computeSalary() + wage * hours;
	}
	@Override
	public void fillMap(Map<String, Object> map) {
		super.fillMap(map);
		map.put("wage", wage);
		map.put("hours", hours);
	}
	
}
