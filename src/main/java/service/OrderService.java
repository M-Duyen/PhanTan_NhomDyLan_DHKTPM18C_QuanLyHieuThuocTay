package service;

import model.Order;
import model.OrderDetail;
import ui.model.ModelDataRS;

import java.rmi.RemoteException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface OrderService extends GenericService<Order, String> {
    List<OrderDetail> getOrderDetailsByOrderId(String orderId) throws RemoteException;
    boolean insertOrderDetail(List<OrderDetail> list) throws RemoteException;
    String createOrderID(String emplId) throws RemoteException;
    double getRevenueByCriteria(String criteria) throws RemoteException;
    double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException;
    ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) throws RemoteException;
    ArrayList<Order> filterOrderByEmpID(String empID) throws RemoteException;
    double calculateTotalAllOrder(String empID) throws RemoteException;
    double getTotalDue(String orderID) throws RemoteException;
    ArrayList<ModelDataRS> getModelDataRSByYear(int year) throws RemoteException;
    ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) throws RemoteException;
    ArrayList<ModelDataRS> getModelDataRSByYearByTime(LocalDate start, LocalDate end) throws RemoteException;
    ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) throws RemoteException;
    double getTotalProductsSold() throws RemoteException;
    double getRevenueSoldPercentage() throws RemoteException;
    double getProfit() throws RemoteException;
    boolean orderIsExists(String orderID) throws RemoteException;
}
