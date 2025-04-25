package service;

import model.OrderDetail;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface OrderDetailService extends GenericService<OrderDetail, String> {
    ArrayList<OrderDetail> getOrderDetailList(String orderID) throws RemoteException;
    ArrayList<OrderDetail> getOrderDetailListByScri(String orderID) throws RemoteException;
    boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID) throws RemoteException;
    ArrayList<ModelDataPS> getProductStatistical(String start, String end) throws RemoteException;
    ArrayList<ModelDataPS_Circle> getProductStatics_ByType(String startDate, String endDate) throws RemoteException;
    ArrayList<ModelDataPS_Circle> getProductStatics_ByCategory(String startDate, String endDate) throws RemoteException;
    Map<String, Double> getUnitPricesByOrderID(String orderID) throws RemoteException;

}
