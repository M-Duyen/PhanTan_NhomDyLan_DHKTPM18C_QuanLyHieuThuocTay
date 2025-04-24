package service;

import model.OrderDetail;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;

import java.util.ArrayList;
import java.util.Map;

public interface OrderDetailService extends GenericService<OrderDetail, String> {
    boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID);
    ArrayList<ModelDataPS> getProductStatistical(String start, String end);
    ArrayList<ModelDataPS_Circle> getProductStaticsByType(String startDate, String endDate);
    ArrayList<ModelDataPS_Circle> getProductStaticsByCategory(String startDate, String endDate);
    Map<String, Double> getUnitPricesByOrderID(String orderID);

}
