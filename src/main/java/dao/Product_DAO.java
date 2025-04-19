package dao;

import entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class Product_DAO extends GenericDAO<Product, String> implements service.ProductService{
    private EntityManager em;

    public Product_DAO(Class<Product> clazz) {
        super(clazz);
    }

    public Product_DAO(EntityManager em, Class<Product> clazz) {
        super(em, clazz);
    }

    @Override
    public List<Product> getAll() {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
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

    @Override
    public Product findById(String id) {
        return em.find(Product.class, id);
    }

    @Override
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

    @Override
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
//Dữ liệu mẫu product
        Faker faker = new Faker();
        EntityManager em = Persistence.createEntityManagerFactory("mariadb").createEntityManager();
        EntityTransaction tr = em.getTransaction();
        tr.begin();
        try {
            for (int i = 0; i < 10; i++) {
                Category category = new Category();
                category.setCategoryID("C" + faker.number().digits(5));
                category.setCategoryName(faker.commerce().department());
                em.persist(category);

                Vendor vendor = new Vendor();
                vendor.setVendorID("V" + faker.number().digits(5));
                vendor.setVendorName(faker.company().name());
                vendor.setCountry(faker.address().country());
                em.persist(vendor);

                Product product = new Product();
                product.setProductID("P" + faker.number().digits(8));
                product.setProductName(faker.commerce().productName());
                product.setRegistrationNumber("REG" + faker.number().digits(6));
                product.setPurchasePrice(faker.number().randomDouble(2, 100, 1000));
                product.setTaxPercentage(faker.number().randomDouble(2, 5, 15));
                product.setEndDate(LocalDate.now().plusDays(faker.number().numberBetween(30, 365)));
                product.setCategory(category); // Set the category
                product.setVendor(vendor);
                String unitNote = faker.lorem().sentence();
                if (unitNote.length() > 60) {
                    unitNote = unitNote.substring(0, 60);
                }
                product.setUnitNote(unitNote);

                HashMap<PackagingUnit, ProductUnit> unitDetails = new HashMap<>();
                PackagingUnit unit = PackagingUnit.BOX;
                ProductUnit productUnit = new ProductUnit();
                productUnit.setSellPrice(faker.number().randomDouble(2, 150, 500));
                productUnit.setInStock(faker.number().numberBetween(10, 100));
                unitDetails.put(unit, productUnit);
                product.setUnitDetails(unitDetails);

                em.persist(product);
            }
            tr.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }
    }
}
