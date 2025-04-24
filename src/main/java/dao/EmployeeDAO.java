package dao;

import model.Employee;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

public class EmployeeDAO extends GenericDAO<Employee, String> {
    public EmployeeDAO(Class<Employee> clazz) {
        super(clazz);
    }

    public EmployeeDAO(EntityManager em, Class<Employee> clazz) {
        super(em, clazz);
    }

    public static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }
}
