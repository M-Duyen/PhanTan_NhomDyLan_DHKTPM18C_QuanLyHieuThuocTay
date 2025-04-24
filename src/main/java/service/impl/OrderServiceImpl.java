package service.impl;

import dao.*;
import model.*;
import ui.model.ModelDataRS;
import service.OrderService;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl extends GenericServiceImpl<Order, String> implements OrderService {
    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) throws RemoteException {
        super(orderDAO);
        this.orderDAO = orderDAO;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        return orderDAO.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public boolean insertOrderDetail(List<OrderDetail> list) {
        return false;
    }

    @Override
    public Order getOrderByOrderId(String orderId) {
        return orderDAO.getOrderByOrderId(orderId);
    }

    //OR 301024 0302 002
    @Override
    public String createOrderID(String emplId) {
        return orderDAO.createOrderID(emplId);
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
        return null;
    }

    @Override
    public double calculateTotalAllOrder(String empID) {
        return 0;
    }

    @Override
    public double getTotalDue(String orderID) {
        return orderDAO.getTotalDue(orderID);
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
        return orderDAO.orderIsExists(orderID);
    }

    @Override
    public Order findById(String s) {
        return null;
    }

    @Override
    public List<?> searchByMultipleCriteria(String entityName, String keyword) {
        return List.of();
    }
}