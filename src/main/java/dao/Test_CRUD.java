package dao;

import entity.Account;
import entity.Employee;
import entity.Manager;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;

public class Test_CRUD {
    private static EntityManager em;


    public static void main(String[] args) {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();

        Account_DAO account_dao = new Account_DAO(em);
        Employee_DAO employee_dao = new Employee_DAO(em);
        Manager_DAO manager_dao = new Manager_DAO(em);

        Account account = new Account();
        account.setAccountID("1234567890");
        account.setPassword("password");

        Employee employee = new Employee();
        employee.setEmployeeID("EMP_Test_1");
        employee.setEmployeeName("Nguyen Van A");
        employee.setPhoneNumber("0123456789");
        employee.setBirthDate(LocalDate.now());
        employee.setGender(false);
        employee.setAddress("Quan 12");
        employee.setEmail("nguyenvana@gmail.com");
        employee.setStatus(true);
        employee.setDegree("IUH");

        Manager manager = new Manager();
        manager.setManagerID("MN_Test");
        manager.setManagerName("Nguyen Van B");
        manager.setBirthDate(LocalDate.now());
        manager.setPhoneNumber("0987654321");

        account.setEmployee(employee);
        account.setManager(manager);


//        employee_dao.create(employee);
//        manager_dao.create(manager);
//
//        account.setManager(manager);
//        account.setEmployee(employee);
//        account_dao.create(account);

//        account.setPassword("new_password");
//        employee.setEmployeeName("Nguyen Van C");
//        manager.setManagerName("Nguyen Van D");
//        account_dao.update(account);
//        employee_dao.update(employee);
//        manager_dao.update(manager);
//
//        System.out.println(account_dao.read("1234567890"));
//        System.out.println(employee_dao.read("EMP_Test_1"));
//        System.out.println(manager_dao.read("MN_Test"));

//        account_dao.delete("1234567890");
        employee_dao.delete("EMP_Test_1");
        manager_dao.delete("MN_Test");




    }
}
