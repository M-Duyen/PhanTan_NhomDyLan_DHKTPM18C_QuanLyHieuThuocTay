package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;
import ui.model.ModelDataRS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Order_DAO {
    private final EntityManager em;

    public Order_DAO() {
        em = Persistence.createEntityManagerFactory("SHH-mariaDB").createEntityManager();
    }

    public List<Order> getAll() {
        return em.createQuery("from Order order", Order.class).getResultList();
    }



    /**
     * Thống kê theo năm (trục tung là doanh thu, trục hoành là tháng)
     *
     * @param year
     * @return
     */
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
     *
     * @param start
     * @param end
     * @return
     */

    //TODO: Kiểm tra điều kiện bên giao diện cho chỉ pheps 30 ngày
    //TODO: Bổ sung hàm định dạng lại chuỗi LocalDate
    public ArrayList<ModelDataRS> getModelDataRSByYearByTime(LocalDate start, LocalDate end) {
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

    public static void main(String[] args) {

    }


}