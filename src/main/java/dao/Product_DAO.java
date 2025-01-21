package dao;

import entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Product_DAO {
    private EntityManager em;

    public Product_DAO() {
        em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
    }


    public List<Product> getAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    public boolean create(Product product) {
        try {
            em.getTransaction().begin();
            em.persist(product);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
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

    public Product read(String id) {
        return em.find(Product.class, id);
    }

    public boolean update(Product product) {
        try {
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(String id) {
        try {
            Product product = em.find(Product.class, id);
            if (product != null) {
                em.getTransaction().begin();
                em.remove(product);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {
//        Product_DAO productDAO = new Product_DAO();
//
//        Product product = new Product();
//        product.setProductID("PM001");
//        product.setProductName("Paracetamol");
//        product.setRegistrationNumber("REG12345");
//        product.setPurchasePrice(500.0);
//        product.setTaxPercentage(10.0);
//        product.setEndDate(LocalDate.of(2025, 12, 31));
//
//         productDAO.create(product);
//
//         System.out.println(productDAO.read("PM001"));
//
//         Product existingProduct = productDAO.read("PM001");
//         existingProduct.setPurchasePrice(550.0);
//         productDAO.update(existingProduct);
//
//         productDAO.delete("PM001");
//
//         productDAO.getAll().forEach(System.out::println);
    }
}
