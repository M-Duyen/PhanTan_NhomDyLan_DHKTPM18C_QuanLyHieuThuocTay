package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import model.ModelDataRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends GenericDAO<Order, String> {
    private EntityManager em;

    public OrderDAO(Class<Order> clazz) {
        super(clazz);
    }

    public OrderDAO(EntityManager em, Class<Order> clazz) {
        super(em, clazz);
    }

    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        String query = "SELECT od FROM OrderDetail od WHERE od.order.orderID = :orderID";

        return em.createQuery(query, OrderDetail.class)
                .setParameter("orderID", orderId)
                .getResultList();
    }

    public boolean insertOrderDetail(List<OrderDetail> list) {
        return false;
    }

    public Order getOrderByOrderId(String orderId) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderId)
                .getSingleResult();
    }

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

    public ArrayList<Order> getOrderByCriterious(String criterious) {
        return null;
    }

    public double getRevenueByCriteria(String criteria) {
        return 0;
    }

    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return 0;
    }

    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    public ArrayList<Order> filterOrderByEmpID(String empID) {
        return null;
    }

    public double calculateTotalAllOrder(String empID) {
        return 0;
    }

    public double getTotalDue(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult()
                .getTotalDue();
    }

    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) {
        return null;
    }

    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) {
        return null;
    }

    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(String start, String end) {
        return null;
    }

    public ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    public double getTotalProductsSold() {
        return 0;
    }

    public double getRevenueSoldPercentage() {
        return 0;
    }

    public double getProfit() {
        return 0;
    }

    public boolean orderIsExists(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        Order order = em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult();
        return order != null;
    }
}
