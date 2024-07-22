package telran.employees;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;

import org.json.JSONObject;

import telran.net.TcpClient;
public class CompanyClientApplication {
public static void main(String[] args) throws UnknownHostException, IOException {
    TcpClient tcpClient = new TcpClient("localhost", 5000, 10, 1000);
    CompanyInterface company = new CompanyProxy(tcpClient);
  System.out.println(company.removeEmployee(123));
  System.out.println(company.removeEmployee(124));
    company.addEmployee(new Manager(123, 1000, "QA", 2.0f));
    company.addEmployee(new WageEmployee(124, 1000, "Audit", 100, 100));
   System.out.println(company.getEmployee(123));
    System.out.println(company.getEmployee(124));
    System.out.println(company.getDepartmentBudget("QA"));
    System.out.println(Arrays.toString(company.getDepartments()));
    tcpClient.close();
}
}
