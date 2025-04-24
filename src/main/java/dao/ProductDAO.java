package dao;

import model.*;
import jakarta.persistence.EntityManager;
import service.ProductService;

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

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO(Product.class);
        dao.getAll().forEach(System.out::println);
    }
}
