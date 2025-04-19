package dao;

import dao.implementation.GenericDAOImpl;
import entity.Employee;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

public class Employee_DAO extends GenericDAOImpl<Employee, String> {
    public Employee_DAO(Class<Employee> clazz) {
        super(clazz);
    }

    public Employee_DAO(EntityManager em, Class<Employee> clazz) {
        super(em, clazz);
    }

    public static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }
}
