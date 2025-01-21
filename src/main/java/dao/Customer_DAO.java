package dao;

import entity.Customer;
import net.datafaker.Faker;

public class Customer_DAO {
    public Customer_DAO() {
    }

    public static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setPhoneNumber(faker.phoneNumber().cellPhone().substring(0, 10));
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }
}
