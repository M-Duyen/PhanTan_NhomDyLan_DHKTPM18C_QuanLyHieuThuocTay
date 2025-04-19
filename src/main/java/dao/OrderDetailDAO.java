package dao;

import entity.OrderDetail;
import jakarta.persistence.EntityManager;
import model.ModelDataPS;
import model.ModelDataPS_Circle;
import service.OrderDetailService;

import java.util.ArrayList;
import java.util.Map;

public class OrderDetailDAO extends GenericDAO<OrderDetail, String> implements OrderDetailService {
   private EntityManager em;

    public OrderDetailDAO(Class<OrderDetail> clazz) {
        super(clazz);
    }

    public OrderDetailDAO(EntityManager em, Class<OrderDetail> clazz) {
        super(em, clazz);
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetailList(String orderID) {
        return null;
    }

    @Override
    public ArrayList<OrderDetail> getOrderDetailListByScri(String orderID) {
        return null;
    }

    @Override
    public boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID) {
        return false;
    }

    @Override
    public ArrayList<ModelDataPS> getProductStatistical(String start, String end) {
        return null;
    }

    @Override
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByType(String startDate, String endDate) {
        return null;
    }

    @Override
    public ArrayList<ModelDataPS_Circle> getProductStatics_ByCategory(String startDate, String endDate) {
        return null;
    }

    @Override
    public Map<String, Double> getUnitPricesByOrderID(String orderID) {
        return Map.of();
    }
}
