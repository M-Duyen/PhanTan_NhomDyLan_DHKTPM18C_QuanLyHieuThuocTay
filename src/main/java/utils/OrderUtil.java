package utils;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class OrderUtil {
    public static void main(String[] args) {
        EntityManager em = Persistence.createEntityManagerFactory("mariadb")
                .createEntityManager();

        EntityTransaction tr = em.getTransaction();
        List<Product> productList = new ArrayList<>();
        List<Order> orderList = new ArrayList<Order>();
        Random rand = new Random();

        //Data Faker
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();

            order.setOrderID("OR" + faker.number().digits(5));
            order.setOrderDate(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
            order.setShipToAddress(faker.address().fullAddress());
            order.setPaymentMethod(faker.options().option(PaymentMethod.class));
            order.setDiscount(faker.number().randomDouble(2, 0, 20) / 100);

            order.setEmployee(createSampleEmployee(faker));
            order.setCustomer(createSampleCustomer(faker));
            order.setPrescription(createSamplePrescription(faker));

            int numOfDetails = faker.number().numberBetween(1, 5);
            for (int j = 0; j < numOfDetails; j++) {
                Product product = productList.get(rand.nextInt(productList.size()));
                PackagingUnit unit = faker.options().option(PackagingUnit.class);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setUnit(unit);
                orderDetail.setOrderQuantity(faker.number().numberBetween(1, 10));
                orderDetailList.add(orderDetail);
            }

            order.setListOrderDetail(orderDetailList);
            orderList.add(order);
        }
    }

    private static Prescription createSamplePrescription(Faker faker) {
        Prescription prescription = new Prescription();
        prescription.setPrescriptionId("PC" + faker.number().digits(3));
        return prescription;
    }

    private static Customer createSampleCustomer(Faker faker) {
        Customer customer = new Customer();
        customer.setCustomerID("CM" + faker.number().digits(3));
        return customer;
    }

    private static Employee createSampleEmployee(Faker faker) {
        Employee employee = new Employee();
        employee.setEmployeeID("EP" + faker.number().digits(3));
        employee.setEmployeeName(faker.name().fullName());
        return employee;
    }

    private static List<Product> createSampleProduct(Faker faker) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setProductID("P" + faker.number().digits(4));
            product.setProductName(faker.commerce().productName());
            product.setPurchasePrice(faker.number().randomDouble(2, 5, 100));
            product.setTaxPercentage(faker.number().randomDouble(2, 0, 15));
            product.setEndDate(LocalDateTime.now().plusDays(1).toLocalDate());

            HashMap<PackagingUnit, Double> unitPrice = new HashMap<>();
            HashMap<PackagingUnit, Integer> unitStock = new HashMap<>();
            for (PackagingUnit unit : PackagingUnit.values()) {
                unitPrice.put(unit, faker.number().randomDouble(2, 1000, 200000));
                unitStock.put(unit, faker.number().numberBetween(50, 500));
            }
            product.setUnitPrice(unitPrice);
            product.setUnitStock(unitStock);
            productList.add(product);
        }
        return productList;
    }

}
