package dao;

import entity.Product;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ProductDAOImpl extends GenericDAO<Product, String> {
    private static ProductDAOImpl dao;

    public ProductDAOImpl(Class<Product> clazz) {
        super(clazz);
    }

    public ProductDAOImpl(EntityManager em, Class<Product> clazz) {
        super(em, clazz);
    }

    public static void main(String[] args) {
        dao = new ProductDAOImpl(Product.class);
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
