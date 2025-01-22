package dao;

import entity.Employee;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

public class Employee_DAO extends AbstractCRUD<Employee, String> {
    public Employee_DAO(EntityManager em) {
        super(em, Employee.class);
    }
    public static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }
}
