package service.impl;

import dao.ProductDAO;
import entity.Product;
import service.ProductService;

import java.rmi.RemoteException;
import java.util.List;

public class ProductServiceImpl extends GenericServiceImpl<Product, String> implements ProductService {
    private ProductDAO productDAO;

    public ProductServiceImpl(ProductDAO productDAO) throws RemoteException {
        super(productDAO);
        this.productDAO = productDAO;
    }
    @Override
    public List<Product> getAll() {
        return productDAO.getAll();
    }

    @Override
    public boolean create(Product product) {
        return productDAO.create(product);
    }

    @Override
    public boolean createMultiple(List<Product> products) {
        return productDAO.createMultiple(products);
    }

    @Override
    public Product findById(String id) {
        return productDAO.findById(id);
    }

    @Override
    public boolean update(Product product) {
        return productDAO.update(product);
    }

    @Override
    public boolean delete(String id) {
        return productDAO.delete(id);
    }

    @Override
    public List<?> searchByMultipleCriteria(String entityName, String keyword) {
        return List.of();
    }
}
