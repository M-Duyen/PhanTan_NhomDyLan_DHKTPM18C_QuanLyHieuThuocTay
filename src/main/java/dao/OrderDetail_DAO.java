package dao;

import entity.OrderDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderDetail_DAO {
    private final EntityManager em;

    public OrderDetail_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }

    public List getAll() {
        return em.createQuery("SELECT od FROM OrderDetail od").getResultList();
    }

    public List<OrderDetail> getOrderDetailsByOrderId(String orderId) {
        try {
            TypedQuery<OrderDetail> query = em.createQuery(
                    "SELECT od FROM OrderDetail od WHERE od.order.orderID = :orderId",
                    OrderDetail.class
            );
            query.setParameter("orderId", orderId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertOrderDetail(List<OrderDetail> list) {
        try {
            em.getTransaction().begin();
            for (OrderDetail od : list) {
                em.persist(od);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            return false;
        }
    }

    public static void main(String[] args) {
        OrderDetail_DAO dao = new OrderDetail_DAO();
        dao.getAll().forEach(System.out::println);
    }
}