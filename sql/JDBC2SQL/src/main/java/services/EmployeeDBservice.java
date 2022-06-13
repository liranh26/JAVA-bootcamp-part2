package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.Employee;

public class EmployeeDBservice {

	public void addEmployee(Employee employee, Connection connection) {

		try (Statement statment = connection.createStatement()) {

			String query = "Insert Into Employees\n" + "(lastName, firstName, email, department, salary)" + "values('"
					+ createValuesStr(employee) + "')";
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String createValuesStr(Employee employee) {
		return employee.getLastName() + "','" + employee.getFirstName() + "','" + employee.getEmail() + "','"
				+ employee.getDepartment() + "','" + employee.getSalary();
	}

	public Employee getEmployee(String id, Connection connection) {
		Employee employee = null;
		String query = "select * from employees where id = " + id;

		try (Statement statment = connection.createStatement(); ResultSet resultSet = statment.executeQuery(query);) {

			if (resultSet.next()) {
				employee = new Employee();
				employee.setId(resultSet.getInt(1));
				employee.setLastName(resultSet.getString(2));
				employee.setFirstName(resultSet.getString(3));
				employee.setEmail(resultSet.getString(4));
				employee.setDepartment(resultSet.getString(5));
				employee.setSalary(resultSet.getDouble(6));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employee;
	}

	public Employee updateEmployee(Employee employee, Connection connection) {

		Employee emp = getEmployee(Integer.toString(employee.getId()), connection);
		if (emp == null)
			return emp;

		if (!employee.equals(emp)) {
			try (Statement statment = connection.createStatement()) {
				String query = "Update Employees \n"
						+ "set lastName='%s', firstName='%s', email='%s', department='%s', salary=%d".formatted(
								employee.getLastName(), employee.getFirstName(), employee.getEmail(),
								employee.getDepartment())
						+ " " + employee.getSalary() + "\n where id=" + employee.getId();

				int rowsAffected = statment.executeUpdate(query);
				System.out.println("Success ! " + rowsAffected + " rows affected");
				emp = employee;

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("No data to update!");
		}

		return emp;
	}

	public Employee delete(Employee employee, Connection connection) {

		Employee emp = getEmployee(Integer.toString(employee.getId()), connection);

		if (emp == null) {
			System.out.println("Employee doesnt exist in DB.");
			return null;
		}
		try (Statement statment = connection.createStatement()) {

			String query = "Delete from Employees where id = %d".formatted(employee.getId());
			int rowsAffected = statment.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return emp;
	}
}
