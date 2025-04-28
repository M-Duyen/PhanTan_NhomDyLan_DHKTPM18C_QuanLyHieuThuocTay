package dao;

import model.OrderDetail;
import jakarta.persistence.EntityManager;
import model.PackagingUnit;
import model.Product;
import model.ProductUnit;
import ui.model.ModelDataPS;
import ui.model.ModelDataPS_Circle;
import service.OrderDetailService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailDAO extends GenericDAO<OrderDetail, String> implements OrderDetailService {
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
        LocalDateTime startDateTime = LocalDate.parse(start).atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(end).atTime(23, 59, 59);

        String query = "SELECT od.product.productID, od.unit, od.product.productName, SUM(od.orderQuantity) AS sumQty " +
                "FROM OrderDetail od " +
                "WHERE od.order.orderDate >= :startDateTime AND od.order.orderDate <= :endDateTime " +
                "GROUP BY od.product.productID, od.unit, od.product.productName " +
                "ORDER BY sumQty DESC ";

        List<Object[]> resultSold = em.createQuery(query, Object[].class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .setMaxResults(14)
                .getResultList();

        ArrayList<ModelDataPS> modelDataPSList = new ArrayList<>();
        for (Object[] row : resultSold) {
            String productID = (String) row[0];
            PackagingUnit unit = (PackagingUnit) row[1];
            String productName = (String) row[2];
            int totalSold = ((Number) row[3]).intValue();

            ModelDataPS data = new ModelDataPS(productID, productName, totalSold, unit);

            Product product = em.find(Product.class, productID);
            if (product != null && product.getUnitDetails().containsKey(unit)) {
                ProductUnit productUnit = product.getUnitDetails().get(unit);
                data.setInStock(productUnit.getInStock());
                data.setTotalPriceSold(totalSold * productUnit.getSellPrice());
            }

            modelDataPSList.add(data);
        }
        return modelDataPSList;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo loại sản phẩm
     *
     * TODO: Fix
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
            Number qty = (Number) row[1];

            int quantity = qty.intValue();
            modelList.add(new ModelDataPS_Circle(type, quantity));
        }
        return modelList;
    }

    /**
     * Thống kê sản phẩm bán chạy theo ngày, theo danh mục (nhóm thuốc)
     *  TODO: Fix
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
                .setMaxResults(15)
                .getResultList();

        ArrayList<ModelDataPS_Circle> modelList = new ArrayList<>();
        for(Object[] row : result) {
            String categoryName = (String) row[0];
            Number count = (Number) row[1];

            int qty = count.intValue();
            modelList.add(new ModelDataPS_Circle(categoryName, qty));
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
        String query = "SELECT od.product.productID, od.unit FROM OrderDetail od WHERE od.order.orderID=:orderID";
        List<Object[]> result = em.createQuery(query, Object[].class)
                .setParameter("orderID", orderID)
                .getResultList();
        Map<String, Double> unitPrices = new HashMap<>();
        for (Object[] row : result) {
            String productID = (String) row[0];
            PackagingUnit unit = (PackagingUnit) row[1];

            Product product = em.find(Product.class, productID);
            if (product != null && product.getUnitDetails().containsKey(unit)) {
                ProductUnit productUnit = product.getUnitDetails().get(unit);
                unitPrices.put(unit.convertUnit(unit), productUnit.getSellPrice());
            }
        }
        return unitPrices;
    }

    public static void main(String[] args) {
        OrderDetailDAO dao = new OrderDetailDAO(OrderDetail.class);
        System.out.println(dao.getProductStatistical("2025-01-15", "2025-01-16"));
    }
}
