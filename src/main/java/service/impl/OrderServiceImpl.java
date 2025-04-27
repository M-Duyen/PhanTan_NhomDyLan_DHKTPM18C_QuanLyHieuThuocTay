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
    private final OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) throws RemoteException {
        super(orderDAO);
        this.orderDAO = orderDAO;
    }


    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws RemoteException {
        return orderDAO.getOrderDetailsByOrderId(orderId);
    }

    @Override
    public boolean insertOrderDetail(List<OrderDetail> list) throws RemoteException {
        return false;
    }

    @Override
    public String createOrderID(String emplId) throws RemoteException {
        return orderDAO.createOrderID(emplId);
    }

    @Override
    public double getRevenueByCriteria(String criteria) throws RemoteException {
        return orderDAO.getRevenueByCriteria(criteria);
    }

    @Override
    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException {
        return orderDAO.getRevenueByDateRange(startDate, endDate);
    }

    @Override
    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException {
        return orderDAO.getOrdersByDateRange(startDate, endDate);
    }

    @Override
    public List<Order> filterOrderByEmpID(String empID, String date) throws RemoteException {
        return orderDAO.filterOrderByEmpID(empID, date);
    }

    @Override
    public List<LocalDate> getAllDateHaveEmpID(String empID) throws RemoteException {
        return orderDAO.getAllDateHaveEmpID(empID);
    }

    @Override
    public double calculateTotalAllOrder(String empID, String date) throws RemoteException {
        return orderDAO.calculateTotalAllOrder(empID, date);
    }

    @Override
    public double getTotalDue(String orderID) throws RemoteException {
        return orderDAO.getTotalDue(orderID);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) throws RemoteException {
        return orderDAO.getModelDataRSByYear(year);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) throws RemoteException {
        return orderDAO.getModelDataRSByYearByMonth(month, year);
    }

    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(LocalDateTime start, LocalDateTime end) throws RemoteException {
        return orderDAO.getModelDataRSByYearByTime(start, end);
    }

    @Override
    public ArrayList<Double> getOverviewStatistical(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException {
        return orderDAO.getOverviewStatistical(startDate, endDate);
    }

    @Override
    public double getTotalProductsSold() throws RemoteException {
        return orderDAO.getTotalProductsSold();
    }

    @Override
    public double getRevenueSoldPercentage() throws RemoteException {
        return orderDAO.getRevenueSoldPercentage();
    }

    @Override
    public double getProfit() throws RemoteException {
        return orderDAO.getProfit();
    }

    @Override
    public boolean orderIsExists(String orderID) throws RemoteException {
        return orderDAO.orderIsExists(orderID);
    }
}