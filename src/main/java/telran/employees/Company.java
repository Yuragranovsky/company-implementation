package telran.employees;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

//we do consider optimization
public class Company implements CompanyInterface{
	private static final String FILE_NAME = "employees-data.txt";
	private TreeSet<Employee> employees = new TreeSet<>();
	public void addEmployee(Employee empl) {
		//if an employee with id equaled to id of empl exists, then to throw IllegalStateException
		long id = empl.getId();
		if(getEmployee(id) != null) {
			throw new IllegalStateException(String.format("employee with id %d already exists",
					id));
		}
		employees.add(empl);
		
	}
	public Employee getEmployee(long id) {
		//if the company doesn't have such employee, then return null
		Employee res = null;
		Employee pattern = new Employee(id, 0, null);
		if(employees.contains(pattern)) {
			res = employees.ceiling(pattern);
		}
		return res;
	}
	public Employee removeEmployee(long id) {
		//removes from the company an employee with a given id
		//if such employee doesn't exist, throw NoSuchElementException
		//returns reference to being removed employee
		Employee result = getEmployee(id);
		if(result == null) {
			throw new NoSuchElementException
			(String.format("Employee with id %d doesn't exist", id));
		}
		employees.remove(result);
		return result;
	}
	public int getDepartmentBudget(String department) {
		
		//returns sum of salary values for all employees of a given department
		//if employees of a given department don't exist, returns 0
		int result = 0;
		for(Employee empl: employees) {
			if(empl.getDepartment().equals(department)) {
				result += empl.computeSalary();
			}
		}
		return result;
	}
	public Company(Employee[] employees) {
		for(Employee empl: employees) {
			this.employees.add(empl);
		}
	}
	@Override
	public Iterator<Employee> iterator() {
		
		return employees.iterator();
	}
	public String[] getDepartments() {
		TreeSet<String> departments = new TreeSet<>();
		for(Employee empl: employees) {
			String department = empl.getDepartment();
			departments.add( department);
		}
		return departments.toArray(String[]::new);
	}
	
	public Manager[] getManagersWithMostFactor() {
		TreeSet<Manager> result = new TreeSet<>();
		float[] maxFactor = {0};
		for (Employee empl: employees) {
			if(empl instanceof Manager) {
				result = updateResult(result, (Manager)empl, maxFactor);
			}
		}
		return result.stream().toArray(Manager[]::new);
	}
	private TreeSet<Manager> updateResult(TreeSet<Manager> managers,
			Manager manager,
			float[] maxFactor) {
		TreeSet<Manager> result = managers;
		if(manager.factor == maxFactor[0]) {
			managers.add( manager);
		} else if(manager.factor > maxFactor[0]) {
			result = new TreeSet<>(List.of(manager));
			maxFactor[0] = manager.factor;
		}
		return result;
	}
	public void save() throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(FILE_NAME);
		JSONArray employeesAr = new JSONArray();
		for(Employee empl: employees) {
			Map<String, Object> map = new HashMap<>();
			empl.fillMap(map);
			
			employeesAr.put(map);
		}
		
			
		writer.println(employeesAr.toString());	
		writer.close();
	}
	public void restore() throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
		String line = reader.readLine();
		JSONArray array = new JSONArray(line);

		for(int i = 0; i < array.length(); i++) {
			JSONObject jsonObj = array.getJSONObject(i);
			Employee empl = (Employee) Class.forName(jsonObj.getString("className"))
			.getConstructor(JSONObject.class).newInstance(jsonObj);
			addEmployee(empl);
		}
		reader.close();
	}
	
	
}
