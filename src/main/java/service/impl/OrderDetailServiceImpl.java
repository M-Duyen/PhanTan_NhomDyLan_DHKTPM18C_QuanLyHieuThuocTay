package service.impl;

import dao.OrderDetailDAO;
import model.OrderDetail;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;
import service.OrderDetailService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailServiceImpl extends GenericServiceImpl<OrderDetail, String> implements OrderDetailService {
    private OrderDetailDAO orderDetailDAO;

    public OrderDetailServiceImpl(OrderDetailDAO orderDetailDAO) throws RemoteException {
        super(orderDetailDAO);
        this.orderDetailDAO = orderDetailDAO;
    }

    @Override
    public boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID) {
        return orderDetailDAO.addOrderReturnDetails(orderID, productID, orderQuantity, gia, unitID);
    }

    @Override
    public ArrayList<ModelDataPS> getProductStatistical(String start, String end) {
        return orderDetailDAO.getProductStatistical(start, end);
    }

    @Override
    public ArrayList<ModelDataPS_Circle> getProductStaticsByType(String startDate, String endDate) {
        return orderDetailDAO.getProductStaticsByType(startDate, endDate);
    }

    @Override
    public ArrayList<ModelDataPS_Circle> getProductStaticsByCategory(String startDate, String endDate) {
        return orderDetailDAO.getProductStaticsByCategory(startDate, endDate);
    }

    @Override
    public Map<String, Double> getUnitPricesByOrderID(String orderID) {
        return orderDetailDAO.getUnitPricesByOrderID(orderID);
    }

    @Override
    public List<?> searchByMultipleCriteria(String entityName, String keyword) {
        return List.of();
    }
}
