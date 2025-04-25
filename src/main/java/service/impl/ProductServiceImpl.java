package service.impl;

import dao.ProductDAO;
import model.Product;
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
    public boolean createMultiple(List<Product> products) {
        return productDAO.createMultiple(products);
    }

    @Override
    public String getIDProduct(String numType, int index) {
        return productDAO.getIDProduct(numType, index);
    }
}
