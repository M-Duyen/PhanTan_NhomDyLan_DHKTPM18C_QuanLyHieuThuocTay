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
    public String createOrderID(String emplId) {
        return orderDAO.createOrderID(emplId);
    }

    @Override
    public double getRevenueByCriteria(String criteria) {
        return orderDAO.getRevenueByCriteria(criteria);
    }

    @Override
    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDAO.getRevenueByDateRange(startDate, endDate);
    }

    @Override
    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderDAO.getOrdersByDateRange(startDate, endDate);
    }

    @Override
    public List<Order> filterOrderByEmpID(String empID, String date) {
        return orderDAO.filterOrderByEmpID(empID, date);
    }

    @Override
    public List<LocalDate> getAllDateHaveEmpID(String empID) throws RemoteException {
        return orderDAO.getAllDateHaveEmpID(empID);
    }

    @Override
    public double calculateTotalAllOrder(String empID, String date) {
        return orderDAO.calculateTotalAllOrder(empID, date);
    }

    @Override
    public double getTotalDue(String orderID) {
        return orderDAO.getTotalDue(orderID);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) {
        return orderDAO.getModelDataRSByYear(year);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) {
        return orderDAO.getModelDataRSByYearByMonth(month, year);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(LocalDate start, LocalDate end) {
        return orderDAO.getModelDataRSByYearByTime(start, end);
    }

    @Override
    public ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) {
        return orderDAO.getOverviewStatistical(startDate, endDate);
    }

    @Override
    public double getTotalProductsSold() {
        return orderDAO.getTotalProductsSold();
    }

    @Override
    public double getRevenueSoldPercentage() {
        return orderDAO.getRevenueSoldPercentage();
    }

    @Override
    public double getProfit() {
        return orderDAO.getProfit();
    }

    @Override
    public boolean orderIsExists(String orderID) {
        return orderDAO.orderIsExists(orderID);
    }
}