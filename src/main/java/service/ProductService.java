package service;

import model.Product;

import java.util.List;

public interface ProductService extends GenericService<Product, String>{
    boolean createMultiple(List<Product> products);

}
