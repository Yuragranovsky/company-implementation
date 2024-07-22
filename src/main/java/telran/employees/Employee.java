package telran.employees;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

public class Employee implements Comparable<Employee>{
	private long id;
	private int basicSalary;
	private String department;
	
	public Employee(long id, int basicSalary, String department) {
		this.id = id;
		this.basicSalary = basicSalary;
		this.department = department;
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", basicSalary=" + basicSalary + ", department=" + department + "]";
	}

	public Employee(JSONObject jsonObj) {
		id = jsonObj.getLong("id");
		basicSalary = jsonObj.getInt("basicSalary");
		department =  jsonObj.getString("department");
		
	}
	public void fillMap(Map<String, Object> map) {
		if (!map.containsKey("className")) {
			map.put("className", getClass().getName());
		}
		map.put("id", id);
		map.put("basicSalary", basicSalary);
		map.put("department", department);
		
		
	}
	@Override
	public int compareTo(Employee o) {
		return Long.compare(id, o.id);
	}
	public long getId() {
		return id;
	}
	public int getBasicSalary() {
		return basicSalary;
	}
	public String getDepartment() {
		return department;
	}
	public int computeSalary() {
		return basicSalary;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return id == other.id;
	}
	
}
