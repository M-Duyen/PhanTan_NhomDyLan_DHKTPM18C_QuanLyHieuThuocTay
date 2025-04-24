package service;

import entity.OrderDetail;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;

import java.util.ArrayList;
import java.util.Map;

public interface OrderDetailService extends GenericService<OrderDetail, String> {
    ArrayList<OrderDetail> getOrderDetailList(String orderID);
    ArrayList<OrderDetail> getOrderDetailListByScri(String orderID);
    boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID);
    ArrayList<ModelDataPS> getProductStatistical(String start, String end);
    ArrayList<ModelDataPS_Circle> getProductStatics_ByType(String startDate, String endDate);
    ArrayList<ModelDataPS_Circle> getProductStatics_ByCategory(String startDate, String endDate);
    Map<String, Double> getUnitPricesByOrderID(String orderID);

}
