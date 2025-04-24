package service.impl;

import dao.OrderDetailDAO;
import entity.OrderDetail;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;
import service.OrderDetailService;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public class OrderDetailServiceImpl extends GenericServiceImpl<OrderDetail, String> implements OrderDetailService {
    private OrderDetailDAO orderDetailDAO;

    public OrderDetailServiceImpl(OrderDetailDAO orderDetailDAO) throws RemoteException {
        super(orderDetailDAO);
        this.orderDetailDAO = orderDetailDAO;
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetailList(String orderID) {
        return orderDetailDAO.getOrderDetailList(orderID);
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetailListByScri(String orderID) {
        return orderDetailDAO.getOrderDetailListByScri(orderID);
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
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByType(String startDate, String endDate) {
        return orderDetailDAO.getProductStatics_ByType(startDate, endDate);
    }

    @Override
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByCategory(String startDate, String endDate) {
        return orderDetailDAO.getProductStatics_ByCategory(startDate, endDate);
    }

    @Override
    public Map<String, Double> getUnitPricesByOrderID(String orderID) {
        return orderDetailDAO.getUnitPricesByOrderID(orderID);
    }
}
