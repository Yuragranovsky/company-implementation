package telran.employees;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

//we do consider optimization
public interface CompanyInterface extends Iterable<Employee>{
	 static final String FILE_NAME = "employees-data.txt";
	
	public void addEmployee(Employee empl) ;
	public Employee getEmployee(long id);
	public Employee removeEmployee(long id) ;
	public int getDepartmentBudget(String department) ;
	
	public String[] getDepartments() ;
	
	public Manager[] getManagersWithMostFactor() ;
	
	public void save() throws FileNotFoundException ;
	public void restore() throws Exception ;
	
	
}
