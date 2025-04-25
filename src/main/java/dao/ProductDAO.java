package dao;

import jakarta.persistence.TypedQuery;
import model.*;
import jakarta.persistence.EntityManager;
import service.ProductService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductDAO extends GenericDAO<Product, String> implements ProductService {
    private EntityManager em;

    public ProductDAO(Class<Product> clazz) {
        super(clazz);
    }

    public ProductDAO(EntityManager em, Class<Product> clazz) {
        super(em, clazz);
    }

    @Override
    public boolean createMultiple(List<Product> products) {
        try {
            em.getTransaction().begin();
            for (Product product : products) {
                em.persist(product);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tạo mã tự động cho sản phẩm
     *
     * @param numType
     * @param index
     * @return
     */
    @Override
    public String getIDProduct(String numType, int index) {
        String newMaSP = null;
        int currentMax = 0;
        String datePart = new SimpleDateFormat("ddMMyy").format(new Date());

        String jpql = "SELECT MAX(CAST(FUNCTION('SUBSTRING', p.productID, 9, 6) AS int)) " +
                "FROM Product p " +
                "WHERE FUNCTION('SUBSTRING', p.productID, 3, 6) = :datePart";

        try {
            TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
            query.setParameter("datePart", datePart);
            Integer max = query.getSingleResult();
            if (max != null) {
                currentMax = max;
            }
            int nextMaSP = currentMax + 1 + (index == 0 ? 0 : index);
            newMaSP = numType + datePart + String.format("%06d", nextMaSP);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return newMaSP;
    }

}
