package dao;

import dao.GenericDAO;
import entity.Product;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductDAO extends GenericDAO<Product, String> {
    private static ProductDAO dao;

    public ProductDAO(Class<Product> clazz) {
        super(clazz);
    }

    public ProductDAO(EntityManager em, Class<Product> clazz) {
        super(em, clazz);
    }
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
        dao = new ProductDAO(Product.class);
        Product product = new Product();
//        dao.searchByMultipleCriteria()
       List<Product> products = (List<Product>) dao.searchByMultipleCriteria("Product", "kd");
       if (products.size() > 0) {
           products.forEach(System.out::println);
       }else {
           System.out.println("Not found");
       }

    }



}
