package dao;

import model.OrderDetail;
import jakarta.persistence.EntityManager;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;
import service.OrderDetailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDetailDAO extends GenericDAO<OrderDetail, String> implements OrderDetailService {
   private EntityManager em;

    public OrderDetailDAO(Class<OrderDetail> clazz) {
        super(clazz);
    }

    public OrderDetailDAO(EntityManager em, Class<OrderDetail> clazz) {
        super(em, clazz);
    }

    /**
     * Thêm đơn hoàn trả
     *
     * @param orderID
     * @param productID
     * @param orderQuantity
     * @param gia
     * @param unitID
     * @return
     */
    @Override
    public boolean addOrderReturnDetails(String orderID, String productID, int orderQuantity, double gia, String unitID) {
        return false;
    }

    /**
     * Thống kê sản phẩm bán chạy biểu đồ cột
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public ArrayList<ModelDataPS> getProductStatistical(String start, String end) {
        return null;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo loại sản phẩm
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ArrayList<ModelDataPS_Circle> getProductStaticsByType(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");

        String query = "SELECT SUBSTRING(p.productID, 1, 2), SUM(od.orderQuantity) " +
                "FROM OrderDetail od " +
                "   INNER JOIN Order o ON od.order.orderID = o.orderID" +
                "   INNER JOIN Product p ON p.productID = od.product.productID " +
                "WHERE o.orderDate >= :startDate AND o.orderDate <= :endDate " +
                "GROUP BY SUBSTRING(p.productID, 1, 2)";

        List<Object[]> result = em.createQuery(query, Object[].class)
                .setParameter("startDate", startDateTime)
                .setParameter("endDate", endDateTime)
                .getResultList();
        ArrayList<ModelDataPS_Circle> modelList = new ArrayList<>();
        for (Object[] row : result) {
            String type = (String) row[0];
            int qty = (int) row[1];

            modelList.add(new ModelDataPS_Circle(type, qty));
        }
        return modelList;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo danh mục (nhóm thuốc)
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ArrayList<ModelDataPS_Circle> getProductStaticsByCategory(String startDate, String endDate) {
        LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T00:00:00");
        LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T23:59:59");

        String query = "SELECT c.categoryName, COUNT(od) " +
                "FROM OrderDetail od " +
                "   INNER JOIN Order o ON od.order.orderID = o.orderID" +
                "   INNER JOIN Product p ON p.productID = od.product.productID " +
                "   INNER JOIN Category c ON c.categoryID = p.category.categoryID " +
                "WHERE o.orderDate >= :startDate AND o.orderDate <= :endDate " +
                "GROUP BY c.categoryName";

        List<Object[]> result = em.createQuery(query, Object[].class)
                .setParameter("startDate", startDateTime)
                .setParameter("endDate", endDateTime)
                .getResultList();

        ArrayList<ModelDataPS_Circle> modelList = new ArrayList<>();
        for(Object[] row : result) {
            String categoryName = (String) row[0];
            int count = (Integer) row[1];

            modelList.add(new ModelDataPS_Circle(categoryName, count));
        }

        return modelList;
    }

    /**
     * Lọc giá sản phẩm theo hóa đơn
     *
     * @param orderID
     * @return Danh sách giá sản phẩm theo hóa đơn
     */
    @Override
    public Map<String, Double> getUnitPricesByOrderID(String orderID) {
        return Map.of();
    }
}
