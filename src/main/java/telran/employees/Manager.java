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
    public int computeSalary() {
    	return Math.round(super.computeSalary() * factor);
    }
    @Override
    public void fillMap(Map<String, Object> map) {
    	super.fillMap(map);
    	map.put("factor",factor);
    }
	
	

}
