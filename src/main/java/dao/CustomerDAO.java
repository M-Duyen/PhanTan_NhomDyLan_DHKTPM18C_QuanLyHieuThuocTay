package dao;

import model.Customer;
import jakarta.persistence.EntityManager;
import net.datafaker.Faker;

import java.time.LocalDate;

public class CustomerDAO extends GenericDAO<Customer, String> {
    public CustomerDAO(Class<Customer> clazz) {
        super(clazz);
    }

    public CustomerDAO(EntityManager em, Class<Customer> clazz) {
        super(em, clazz);
    }

    public static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setPhoneNumber(faker.phoneNumber().cellPhone().substring(0, 10));
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }

    public static void main(String[] args) {
        CustomerDAO dao = new CustomerDAO(Customer.class);
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
