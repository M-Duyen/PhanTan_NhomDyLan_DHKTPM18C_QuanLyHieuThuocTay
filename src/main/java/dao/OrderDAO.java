package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import model.ModelDataRS;
import net.datafaker.Faker;
import service.OrderService;
import utils.JPAUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderDAO extends GenericDAO<Order, String> implements OrderService {

    public OrderDAO(Class<Order> clazz) {
        super(clazz);
    }

    public OrderDAO(EntityManager em, Class<Order> clazz) {
        super(em, clazz);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        String query = "SELECT od FROM OrderDetail od WHERE od.order.orderID = :orderID";

        return em.createQuery(query, OrderDetail.class)
                .setParameter("orderID", orderId)
                .getResultList();
    }

    @Override
    public boolean insertOrderDetail(List<OrderDetail> list) {
        return false;
    }

    @Override
    public Order getOrderByOrderId(String orderId) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderId)
                .getSingleResult();
    }

    @Override
    public String createOrderID(String emplId) {
        String datePart = DateTimeFormatter.ofPattern("ddMMyy").format(LocalDate.now());
        String empl = emplId.substring(2);
        String prefix = "OR" + datePart + empl;

        String query = "SELECT MAX(SUBSTRING(o.orderID, LENGTH(:prefix) + 1, 3)) " +
                "FROM Order o WHERE SUBSTRING(o.orderID, 3, 6) =:datePart";
        String maxId = em.createQuery(query, String.class)
                .setParameter("prefix", prefix)
                .setParameter("datePart", datePart)
                .getSingleResult();
        int currentId = 0;
        if(maxId != null) {
            currentId = Integer.parseInt(maxId);
        }
        int newId = currentId + 1;
        return prefix + String.format("%03d", newId);
    }

    @Override
    public ArrayList<Order> getOrderByCriterious(String criterious) {
        return null;
    }

    @Override
    public double getRevenueByCriteria(String criteria) {
        return 0;
    }

    @Override
    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return 0;
    }

    @Override
    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<Order> filterOrderByEmpID(String empID) {
       String jpql = "select o " +
               "from Order o " +
               "where o.employee.id = :empID ";

       return em.createQuery(jpql, Order.class)
               .getResultList();
    }
    public List<LocalDate> getAllDateHaveEmpID(String empID) {
        String jpql = "SELECT o.orderDate FROM Order o WHERE o.employee.id = :empID GROUP BY o.orderDate";

        List<LocalDateTime> dateTimes = em.createQuery(jpql, LocalDateTime.class)
                .setParameter("empID", empID)
                .getResultList();

        // Convert to LocalDate
        return dateTimes.stream()
                .map(LocalDateTime::toLocalDate)
                .distinct()
                .toList();
    }



    @Override
    public double calculateTotalAllOrder(String empID) {
        return 0;
    }

    @Override
    public double getTotalDue(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult()
                .getTotalDue();
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) {
        return null;
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) {
        return null;
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(String start, String end) {
        return null;
    }

    @Override
    public ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public double getTotalProductsSold() {
        return 0;
    }

    @Override
    public double getRevenueSoldPercentage() {
        return 0;
    }

    @Override
    public double getProfit() {
        return 0;
    }

    @Override
    public boolean orderIsExists(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        Order order = em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult();
        return order != null;
    }

    public boolean insertOrder() {
        EntityTransaction tr = em.getTransaction();
        Faker faker = new Faker();
        //List<Product> productList = new Product_DAO(Product.class).createSampleProduct(faker);
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
                Product product = null; //productList.get(rand.nextInt(productList.size()));
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
            //new OrderDetailDAO(OrderDetail.class).insertOrderDetail(orderDetailList);
            tr.commit();
        }
        return true;
    }

    public static void main(String[] args) {
        OrderDAO orderDAO = new OrderDAO(JPAUtil.getEntityManager(), Order.class);
        orderDAO.getAllDateHaveEmpID("EP1501").forEach(System.out::println);
    }




}
