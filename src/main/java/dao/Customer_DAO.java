package dao;

import entity.Customer;
import net.datafaker.Faker;

public class Customer_DAO {
    public Customer_DAO() {
    }

    public static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }
}
