package dao;

import entity.Employee;
import jakarta.persistence.EntityManager;

public class Employee_DAO extends AbstractCRUD<Employee, String> {
    public Employee_DAO(EntityManager em) {
        super(em, Employee.class);
    }
}
