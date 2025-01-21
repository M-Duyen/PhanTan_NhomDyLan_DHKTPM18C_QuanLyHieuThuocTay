package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Unit_DAO {
    private final EntityManager em;

    public Unit_DAO() {
        em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();
    }

    public List getAll() {
        return em.createQuery("select u from Unit u").getResultList();
    }

    public boolean insertUnit(Unit unit) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.persist(unit);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static Unit createSapmleUnit() {
        Faker faker = new Faker();
        Unit unit = new Unit();
        unit.setUnitID("U" + faker.number().digits(3));
        unit.setUnitName(faker.company().name());
        unit.setDescription(faker.company().name());
        return unit;
    }

    public boolean createSanple() {
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
            tr.commit();
        }
        return true;
    }


    public boolean updateUnit(Unit unit) {
        EntityTransaction tr = em.getTransaction();
        try {
            tr.begin();
            em.merge(unit);
            tr.commit();
            return true;
        } catch (Exception e) {
            tr.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
        Unit_DAO dao = new Unit_DAO();
        Unit unit = dao.createSapmleUnit();
        System.out.println(dao.insertUnit(unit));

        dao.getAll().forEach(System.out::println);
        //System.out.println(dao.updateUnit(unit));

    }
}