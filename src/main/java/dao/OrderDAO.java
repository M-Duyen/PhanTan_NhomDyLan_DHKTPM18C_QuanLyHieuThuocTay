package dao;

import model.*;
import jakarta.persistence.EntityManager;
import ui.model.ModelDataRS;
import service.OrderService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderDAO extends GenericDAO<Order, String> implements OrderService {
    private EntityManager em;

    public OrderDAO(Class<Order> clazz) {
        super(clazz);
    }

    public OrderDAO(EntityManager em, Class<Order> clazz) {
        super(em, clazz);
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        String query = "SELECT od FROM OrderDetail od WHERE od.order.orderID = :orderID";

        return em.createQuery(query, OrderDetail.class)
                .setParameter("orderID", orderId)
                .getResultList();
    }

    @Override
    public boolean insertOrderDetail(List<OrderDetail> list) {
        return false;
    }

    /**
     * Lọc hóa đơn theo mã hóa đơn
     *
     * @param orderID
     * @return
     */
    @Override
    public Order getOrderByOrderId(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult();
    }

    /**
     * Tạo mã tự động cho hóa đơn
     *
     * @return
     */
    @Override
    public String createOrderID(String emplId) {
        String datePart = DateTimeFormatter.ofPattern("ddMMyy").format(LocalDate.now());
        String empl = emplId.substring(2);
        String prefix = "OR" + datePart + empl;

        String query = "SELECT MAX(SUBSTRING(o.orderID, LENGTH(:prefix) + 1, 3)) " +
                "FROM Order o WHERE SUBSTRING(o.orderID, 3, 6) =:datePart";
        String maxId = em.createQuery(query, String.class)
                .setParameter("prefix", prefix)
                .setParameter("datePart", datePart)
                .getSingleResult();
        int currentId = 0;
        if(maxId != null) {
            currentId = Integer.parseInt(maxId);
        }
        int newId = currentId + 1;
        return prefix + String.format("%03d", newId);
    }

    /**
     * Tìm kiếm hóa đơn theo tiêu chí bất kì
     * định dạng ngày khi nhập (yyyy-MM-dd)
     *
     * @param criterious
     * @return
     */
    @Override
    public ArrayList<Order> getOrderByCriterious(String criterious) {
        return null;
    }

    /**
     * Tính doanh thu theo 1 tiêu chí bất kì
     *
     * @param criteria
     * @return
     */
    @Override
    public double getRevenueByCriteria(String criteria) {
        return 0;
    }

    /**
     * Tính doanh thu trong 1 khoảng thời gian bất kì
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public double getRevenueByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return 0;
    }

    /**
     * Lọc hóa đơn được tạo trong 1 khoảng thời gian bất kì
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ArrayList<Order> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return null;
    }

    /**
     * Lọc danh sách hóa đơn theo ngày hiện tại của nhân viên
     *
     * @param empID
     * @return
     */
    @Override
    public ArrayList<Order> filterOrderByEmpID(String empID) {
        return null;
    }

    /**
     * Tính doanh thu theo ngày hiện tại của nhân viên
     *
     * @param empID
     * @return
     */
    @Override
    public double calculateTotalAllOrder(String empID) {
        return 0;
    }

    /**
     * Lấy totalDue của hóa đơn
     *
     * @param orderID
     * @return
     */
    @Override
    public double getTotalDue(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        return em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult()
                .getTotalDue();
    }

    /**
     * Thống kê theo năm (trục tung là doanh thu, trục hoành là tháng)
     *
     * @param year
     * @return
     */
    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYear(int year) {
        List<Object[]> results = em.createQuery(
                "SELECT DISTINCT MONTH(o.orderDate), o FROM Order o JOIN FETCH o.listOrderDetail od " +
                        "WHERE YEAR(o.orderDate) = :year", Object[].class
        ).setParameter("year", year).getResultList();
        Map<Integer, List<Order>> orderMapByMouth = new HashMap<>();

        results.forEach(row -> {
            int month = (Integer) row[0];
            Order order = (Order)row[1];
            orderMapByMouth.computeIfAbsent(month, k -> new ArrayList<>()).add(order);
        });
        ArrayList<ModelDataRS> modelDataRS = new ArrayList<>();
        orderMapByMouth.forEach((month, order) -> {
            double[] pricesTemp = new double[3];

            order.forEach( o -> {
                o.getListOrderDetail().forEach(detail -> {
                    char type = detail.getProduct().getProductID().charAt(1);
                    switch (type) {
                        case 'M' -> pricesTemp[0] += detail.getLineTotal();
                        case 'S' -> pricesTemp[1] += detail.getLineTotal();
                        case 'F' -> pricesTemp[2] += detail.getLineTotal();
                    }
                });
            });
            double total = pricesTemp[0] + pricesTemp[1] + pricesTemp[2];
            modelDataRS.add(new ModelDataRS(
                    "Tháng" + month,
                    total,
                    pricesTemp[0],
                    pricesTemp[1],
                    pricesTemp[2]
            ));
        });
        return modelDataRS;
    }

    /**
     * Thống kê theo tháng, trục tung là doanh thu, trục hoành là các ngày trong tháng đó
     *
     * @param month
     * @param year
     * @return
     */
    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByMonth(int month, int year) {
        List<Object[]> results = em.createQuery(
                        "SELECT DISTINCT DAY(o.orderDate), o FROM Order o JOIN FETCH o.listOrderDetail od " +
                                "WHERE YEAR(o.orderDate) = :year AND MONTH(o.orderDate) = :month", Object[].class
                ).setParameter("year", year)
                .setParameter("month", month)
                .getResultList();

        Map<Integer, List<Order>> orderMapByDay = new HashMap<>();

        results.forEach(row -> {
            int day = (Integer) row[0];
            Order order = (Order) row[1];
            orderMapByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(order);
        });

        ArrayList<ModelDataRS> modelDataRS = new ArrayList<>();
        orderMapByDay.forEach((day, orders) -> {
            double[] pricesTemp = new double[3];

            orders.forEach(order -> {
                order.getListOrderDetail().forEach(detail -> {
                    char type = detail.getProduct().getProductID().charAt(1);
                    switch (type) {
                        case 'M' -> pricesTemp[0] += detail.getLineTotal();
                        case 'S' -> pricesTemp[1] += detail.getLineTotal();
                        case 'F' -> pricesTemp[2] += detail.getLineTotal();
                    }
                });
            });

            double total = pricesTemp[0] + pricesTemp[1] + pricesTemp[2];
            modelDataRS.add(new ModelDataRS(
                    "Ngày " + day,
                    total,
                    pricesTemp[0],
                    pricesTemp[1],
                    pricesTemp[2]
            ));
        });
        return modelDataRS;
    }

    /**
     * Thống kê theo tháng, trục tung là doanh thu, trục hoành là các ngày trong tháng đó
     * TODO: Kiểm tra điều kiện bên giao diện cho chỉ pheps 30 ngày
     * TODO: Bổ sung hàm định dạng lại chuỗi LocalDate
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(String start, String end) {
        List<Object[]> results = em.createQuery(
                        "SELECT DISTINCT DAY(o.orderDate), o FROM Order o JOIN FETCH o.listOrderDetail od " +
                                "WHERE o.orderDate >= :start AND o.orderDate <= :end", Object[].class
                ).setParameter("start", start)
                .setParameter("end", end)
                .getResultList();

        Map<LocalDate, List<Order>> orderMapByDay = new HashMap<>();

        results.forEach(row -> {
            LocalDate day = (LocalDate) row[0];
            Order order = (Order) row[1];
            orderMapByDay.computeIfAbsent(day, k -> new ArrayList<>()).add(order);
        });

        ArrayList<ModelDataRS> modelDataRS = new ArrayList<>();
        orderMapByDay.forEach((day, orders) -> {
            double[] pricesTemp = new double[3];

            orders.forEach(order -> {
                order.getListOrderDetail().forEach(detail -> {
                    char type = detail.getProduct().getProductID().charAt(1);
                    switch (type) {
                        case 'M' -> pricesTemp[0] += detail.getLineTotal();
                        case 'S' -> pricesTemp[1] += detail.getLineTotal();
                        case 'F' -> pricesTemp[2] += detail.getLineTotal();
                    }
                });
            });

            double total = pricesTemp[0] + pricesTemp[1] + pricesTemp[2];
            modelDataRS.add(new ModelDataRS(
                    "Ngày " + day.toString(), //TODO: Xử lý format ngày
                    total,
                    pricesTemp[0],
                    pricesTemp[1],
                    pricesTemp[2]
            ));
        });
        return modelDataRS;
    }

    /**
     * Lấy thông tin tổng quan
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public ArrayList<Double> getOverviewStatistical(LocalDate startDate, LocalDate endDate) {
        return null;
    }

    /**
     * Lấy tỷ lệ sản phẩm đã bán, tính bằng cách lấy tổng sản phẩm đã bán chia cho tổng của tồn kho với sản phẩm đã bán
     *
     * @return
     */
    @Override
    public double getTotalProductsSold() {
        String query1 = "SELECT p.unitNote FROM Product p WHERE p.unitNote IS NOT NULL";
        List<String> unitNotes = em.createQuery(query1, String.class).getResultList();

        int totalInStock = 0;
        for (String unitNote : unitNotes) {
            Pattern pattern = Pattern.compile("\\((\\d+)\\)");
            Matcher matcher = pattern.matcher(unitNote);
            while (matcher.find()) {
                totalInStock += Integer.parseInt(matcher.group(1));
            }
        }

        String query2 = "SELECT p.unitNote, od.unit, SUM(od.orderQuantity) " +
                "FROM OrderDetail od INNER JOIN Product p ON p.productID = od.product.productID " +
                "WHERE p.unitNote IS NOT NULL " +
                "GROUP BY p.productID, od.unit, p.unitNote";

        List<Object[]> results = em.createQuery(query2, Object[].class).getResultList();

        double totalSold = 0.0;
        for (Object[] row : results) {
            String unitNote = (String) row[0];
            PackagingUnit soldUnit = (PackagingUnit) row[1];
            int quantitySold = ((Number) row[2]).intValue();

            String[] parts = unitNote.split(",\\s*");
            boolean isFirstUnit = true;
            for (String part : parts) {
                Pattern pattern = Pattern.compile("([A-Z_]+)\\((\\d+)\\)");
                Matcher matcher = pattern.matcher(part);

                if (matcher.find()) {
                    String enumName = matcher.group(1).trim();
                    int multiplier = Integer.parseInt(matcher.group(2));
                    if (isFirstUnit) {
                        isFirstUnit = false;
                        continue;
                    }

                    if (enumName.equals(soldUnit.name())) {
                        totalSold += (double) quantitySold / multiplier;
                        break;
                    } else {
                        totalSold += (double) quantitySold / multiplier;
                    }
                }
            }
        }
        return totalInStock == 0 ? 0 : (totalSold / totalInStock) * 100;
    }

    /**
     * Lấy tổng doanh thu đã bán
     *
     * @return
     */
    @Override
    public double getRevenueSoldPercentage() {
        return 0;
    }

    /**
     * Tỷ lợi lợi nhuận, xem công thức ở tài liệu số 6
     *
     * @return
     */
    @Override
    public double getProfit() {
        return 0;
    }

    /**
     * Kiểm tra hóa đơn có tồn tại hay không
     * @param orderID
     * @return
     */
    @Override
    public boolean orderIsExists(String orderID) {
        String query = "SELECT o FROM Order o WHERE o.orderID = :orderID";
        Order order = em.createQuery(query, Order.class)
                .setParameter("orderID", orderID)
                .getSingleResult();
        return order != null;
    }
}
