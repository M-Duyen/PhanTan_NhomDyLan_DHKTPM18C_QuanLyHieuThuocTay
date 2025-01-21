package dao;

import entity.Employee;
import net.datafaker.Faker;

public class Employee_DAO {

    public Employee_DAO() {
    }

    public static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }
}
