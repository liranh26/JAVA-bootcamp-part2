import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import models.Employee;
import services.EmployeeDBservice;
import utils.DBconnectionManager;

public class Runner {

	private static final String PROPERTIES_FILE = "demo.properties";

	public static void main(String[] args) {

		Properties prop = new Properties();
		String serverAddress="", port="", databaseName="", serverName="", userName="", password="";
		
		try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE);) {
			prop.load(fileInputStream);
			userName = prop.getProperty("user");
			password = prop.getProperty("password");
			serverAddress = prop.getProperty("server_address");
			port = prop.getProperty("port");
			databaseName = prop.getProperty("db_name");
			serverName = prop.getProperty("server_name");

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (Connection connection = new DBconnectionManager(serverAddress, port, databaseName, serverName, userName,
				password).connect();) {

			System.out.println("Connected");
			EmployeeDBservice dbService = new EmployeeDBservice();

			/**** CRUD operations ****/

			/* CREATE - adding 3 employees */
//				add3Emp(connection);

			/* READ - from db by employee id */
			Employee emp = dbService.getEmployee("1013", connection);
			System.out.println(emp);

			/* UPDATE - employee in db */
			emp.setFirstName("liran nisim");
			Employee emp1 = dbService.updateEmployee(emp, connection);
			emp1 = dbService.getEmployee("1013", connection);
			System.out.println(emp1);
			
			/* DELETE - employee from db */
			emp = dbService.delete(emp1, connection);
			String isDeleted = dbService.getEmployee("1013", connection) != null ? "Employee has no Data to delete" : "Employee deleted"  ;
			System.out.println(isDeleted);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void add3Emp(Connection connection) {
		Employee emp1 = new Employee("liran", "hadad", "liranh6@gmail.com", "Engineering", 50000);
		Employee emp2 = new Employee("sapir", "hadad", "sapo@gmail.com", "Teach", 100000);
		Employee emp3 = new Employee("snir", "hadad", "sniro@gmail.com", "Engineering", 50000);

		EmployeeDBservice dbService = new EmployeeDBservice();
		dbService.addEmployee(emp1, connection);
		dbService.addEmployee(emp2, connection);
		dbService.addEmployee(emp3, connection);
	}

}
