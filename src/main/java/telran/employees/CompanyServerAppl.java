package telran.employees;

import telran.employees.net.CompanyProtocol;
import telran.net.*;
public class CompanyServerAppl {
public static void main(String[] args) {
    Company company = new Company(new Employee[0]);
    Protocol companyProtocol = new CompanyProtocol(company);
    TcpServer tcpServer = new TcpServer(5000, companyProtocol);
    tcpServer.run();
}
}
