package dao;

import entity.Customer;
import entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.List;

public class Customer_DAO extends AbstractCRUD<Customer, String> {
    public Customer_DAO(EntityManager em) {
        super(em, Customer.class);
    }
    public static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setPhoneNumber(faker.phoneNumber().cellPhone().substring(0, 10));
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }

    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();

        Customer_DAO dao = new Customer_DAO(em);
        Customer customer = new Customer();
        customer.setCustomerID("CUSER_test");
        customer.setCustomerName("Test Customer");
        customer.setAddr("Test Address");
        customer.setPhoneNumber("1234567890");
        customer.setBrithDate(LocalDate.now());
        customer.setGender(true);

//        dao.create(customer);
//        System.out.println(dao.read("1234567890"));
//        customer.setCustomerName("Test Customer Updated");
//        dao.update(customer);
        dao.delete("1234567890");
        dao.getAll().forEach(cus -> System.out.println(cus.getCustomerID() + ": " + cus.getCustomerName()));


    }
}
