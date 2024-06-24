package telran.employees;

import java.util.Map;

import org.json.JSONObject;

public class SalesPerson extends WageEmployee {
	
	float percent;
	long sales;
	public SalesPerson(long id, int basicSalary, String department, int hours, int wage, float percent, long sales) {
		super(id, basicSalary, department, hours, wage);
		this.percent = percent;
		this.sales = sales;
	}
	public SalesPerson(JSONObject jsonObj) {
		super(jsonObj);
		percent = jsonObj.getFloat("percent");
		sales = jsonObj.getLong("sales");
	}
	@Override
	public int computeSalary() {
		return Math.round(super.computeSalary() + sales * percent / 100);
	}
	@Override
	public void fillMap(Map<String, Object> map) {
		super.fillMap(map);
		map.put("percent", percent);
		map.put("sales", sales);
	}

}
