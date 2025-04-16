package dao.implementation;

import dao.*;
import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order_DAOImpl extends GenericDAOImpl<Order, String> {
    public Order_DAOImpl(Class<Order> clazz) {
        super(clazz);
    }

    public Order_DAOImpl(EntityManager em, Class<Order> clazz) {
        super(em, clazz);
    }

    public boolean insertOrder() {
        EntityTransaction tr = em.getTransaction();
        Faker faker = new Faker();
        List<Product> productList = Product_DAO.createSampleProduct(faker);
        List<Order> orderList = new ArrayList<Order>();
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            Order order = new Order();
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();

            order.setOrderID("OR" + faker.number().digits(5));
            System.out.println(order.getOrderID());
            order.setOrderDate(LocalDateTime.now().minusDays(faker.number().numberBetween(1, 30)));
            order.setShipToAddress(faker.address().fullAddress());
            order.setPaymentMethod(faker.options().option(PaymentMethod.class));
            order.setDiscount(faker.number().randomDouble(2, 0, 20) / 100);

            Employee employee = Employee_DAO.createSampleEmployee(faker);
            order.setEmployee(employee);

            Customer customer = Customer_DAO.createSampleCustomer(faker);
            order.setCustomer(customer);

            Prescription prescription = Prescription_DAO.createSamplePrescription(faker);
            order.setPrescription(prescription);

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

            tr.begin();
            em.persist(employee);
            em.persist(customer);
            em.persist(prescription);
            em.persist(order);
            new OrderDetail_DAOImpl().insertOrderDetail(orderDetailList);
            tr.commit();
        }
        return true;
    }

    public static void main(String[] args) {
        Order_DAOImpl dao = new Order_DAOImpl(Order.class);

        dao.insertOrder();
        System.out.println("Result: " + dao.getAll());
    }


}