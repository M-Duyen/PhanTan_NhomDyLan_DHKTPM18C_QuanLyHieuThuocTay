package service;

import model.Product;

import java.util.List;

public interface ProductService extends GenericService<Product, String>{

    List<Product> getAll();

    boolean create(Product product);

    boolean createMultiple(List<Product> products);

    Product findById(String id);

    boolean update(Product product);

    boolean delete(String id);
}
