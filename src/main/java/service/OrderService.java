package service;

import model.Order;
import model.OrderDetail;
import ui.model.ModelDataRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface OrderService extends GenericService<Order, String>{
    List<OrderDetail> getOrderDetailsByOrderId(String orderId);
    boolean insertOrderDetail(List<OrderDetail> list);
    Order getOrderByOrderId(String orderId);
    String createOrderID(String emplId);
    ArrayList<Order> getOrderByCriterious(String criterious);
    double getRevenueByCriteria(String criteria);
    double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> filterOrderByEmpID(String empID);
    double calculateTotalAllOrder(String empID);
    double getTotalDue(String orderID);
    ArrayList<ModelDataRS> getModelDataRSByYear(int year);
    ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year);
    ArrayList<ModelDataRS> getModelDataRSByYearByTime(LocalDate start, LocalDate end);
    ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate);
    double getTotalProductsSold();
    double getRevenueSoldPercentage();
    double getProfit();
    boolean orderIsExists(String orderID);
}
