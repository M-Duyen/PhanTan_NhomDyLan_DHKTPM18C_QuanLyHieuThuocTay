package service.impl;

import dao.ProductDAO;
import model.PackagingUnit;
import model.Product;
import service.ProductService;
import utils.JPAUtil;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

public class ProductServiceImpl extends GenericServiceImpl<Product, String> implements ProductService {
    private ProductDAO productDAO;


    public ProductServiceImpl(ProductDAO productDAO) throws RemoteException {
        super(productDAO);
        this.productDAO = productDAO;
    }

    @Override
    public boolean createMultiple(List<Product> products)  throws RemoteException {
        return productDAO.createMultiple(products);
    }

    @Override
    public List<Product> getProductListNearExpire() throws RemoteException {
        return productDAO.getProductListNearExpire();
    }

    @Override
    public List<Product> getLowStockProducts(int threshold) throws RemoteException {
        return productDAO.getLowStockProducts(threshold);
    }

    @Override
    public List<Product> fetchProducts() throws RemoteException {
        return productDAO.fetchProducts();
    }

    @Override
    public String getIDProduct(String numType, int index) throws RemoteException {
        return productDAO.getIDProduct(numType, index);
    }

    @Override
    public String getProductCategory(String productID) throws RemoteException {
        return productDAO.getProductCategory(productID);
    }

    @Override
    public String getProductID_NotCategory(String productID) throws RemoteException {
        return productDAO.getProductID_NotCategory(productID);
    }

    @Override
    public String convertProductID_ToBarcode(String productID) throws RemoteException {
        return productDAO.convertProductID_ToBarcode(productID);
    }

    @Override
    public String convertBarcode_ToProductID(String barcode) throws RemoteException {
        return productDAO.convertBarcode_ToProductID(barcode);
    }

    @Override
    public Product getProduct_ByBarcode(String barcode) throws RemoteException {
        return productDAO.getProduct_ByBarcode(barcode);
    }

    @Override
    public boolean updateProductInStock(String productID, int qtyChange, boolean inc) throws RemoteException {
        return productDAO.updateProductInStock(productID, qtyChange, inc);
    }

    @Override
    public boolean updateProductInStock(String productID, int qtyChange, PackagingUnit unitEnum, boolean inc) throws RemoteException {
        return productDAO.updateProductInStock(productID, qtyChange, unitEnum, inc);
    }

    @Override
    public String extractUnitName(String input) throws RemoteException {
        return productDAO.extractUnitName(input);
    }

    @Override
    public int getIndexPart_UnitNote(String[] parts, String element) throws RemoteException {
        return productDAO.getIndexPart_UnitNote(parts, element);
    }

    @Override
    public int getNextConver(String[] parts, int currentIndex) throws RemoteException {
        return productDAO.getNextConver(parts, currentIndex);
    }

    @Override
    public Product getProductAfterUpdateUnits(Product product, PackagingUnit unit, boolean inc, int qtyChange) {
        return productDAO.getProductAfterUpdateUnits(product, unit, inc, qtyChange);
    }

}
