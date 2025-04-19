package dao.implementation;

import entity.Order;
import entity.OrderDetail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderDetail_DAOImpl extends GenericDAOImpl<OrderDetail, String>{

    public OrderDetail_DAOImpl(Class<OrderDetail> clazz) {
        super(clazz);
    }

    public OrderDetail_DAOImpl(EntityManager em, Class<OrderDetail> clazz) {
        super(em, clazz);
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
        OrderDetail_DAOImpl dao = new OrderDetail_DAOImpl(OrderDetail.class);
        dao.getAll().forEach(System.out::println);
    }
}