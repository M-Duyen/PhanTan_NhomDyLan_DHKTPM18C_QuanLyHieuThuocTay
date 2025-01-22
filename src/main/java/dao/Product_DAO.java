package dao;

import entity.PackagingUnit;
import entity.Product;
import entity.ProductUnit;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product_DAO {
    private static EntityManager em;

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

    public static List<Product> createSampleProduct(Faker faker) {
        List<Product> productList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();

            product.setProductID("P" + faker.number().digits(4));
            product.setProductName(faker.commerce().productName());
            product.setPurchasePrice(faker.number().randomDouble(2, 5, 100));
            product.setTaxPercentage(faker.number().randomDouble(2, 0, 15));
            product.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));

            Map<PackagingUnit, ProductUnit> unitDetails = new HashMap<>();
            int unitCount = faker.number().numberBetween(1, 5);
            for (int j = 0; j < unitCount; j++) {
                PackagingUnit unit = PackagingUnit.values()[faker.number().numberBetween(0, PackagingUnit.values().length)];
                System.out.println("Unit: " + unit);
                ProductUnit productUnit = new ProductUnit();
                productUnit.setSellPrice(faker.number().randomDouble(2, 10, 500));
                productUnit.setInStock(faker.number().numberBetween(10, 1000));

                unitDetails.put(unit, productUnit);
            }
            product.setUnitDetails(unitDetails);
            productList.add(product);
        }
        return productList;
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
