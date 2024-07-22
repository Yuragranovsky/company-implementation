package telran.employees;

import java.util.Map;

import org.json.JSONObject;

public class Manager extends Employee {
    float factor;

	public Manager(long id, int basicSalary, String department, float factor) {
		super(id, basicSalary, department);
		this.factor = factor;
	}
	public Manager(JSONObject jsonObj) {
		super(jsonObj);
		this.factor = jsonObj.getFloat("factor");
	}
	
    @Override
	public String toString() {
		return "Manager [factor=" + factor + ", toString()=" + super.toString() + "]";
	}
	@Override
    public int computeSalary() {
    	return Math.round(super.computeSalary() * factor);
    }
    @Override
    public void fillMap(Map<String, Object> map) {
		if (!map.containsKey("className")) {
			map.put("className", getClass().getName());
		}
    	super.fillMap(map);
    	map.put("factor",factor);
		
    }
	
	

}
