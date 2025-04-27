package service;

import model.PackagingUnit;
import model.Product;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.List;

public interface ProductService extends GenericService<Product, String>{
    boolean createMultiple(List<Product> products) throws RemoteException;
    List<Product> getProductListNearExpire()  throws RemoteException;

    List<Product> getLowStockProducts(int threshold) throws RemoteException;

    List<Product> fetchProducts() throws RemoteException;

    String getIDProduct(String numType, int index) throws RemoteException;

    String getProductCategory(String productID) throws RemoteException;

    String getProductID_NotCategory(String productID) throws RemoteException;

    String convertProductID_ToBarcode(String productID) throws RemoteException;

    String convertBarcode_ToProductID(String barcode) throws RemoteException;

    Product getProduct_ByBarcode(String barcode) throws RemoteException;

    boolean updateProductInStock(String productID, int qtyChange, boolean inc) throws RemoteException;

    boolean updateProductInStock(String productID, int qtyChange, PackagingUnit unitEnum, boolean inc) throws RemoteException;

    String extractUnitName(String input) throws RemoteException;

    int getIndexPart_UnitNote(String[] parts, String element) throws RemoteException;

    int getNextConver(String[] parts, int currentIndex) throws RemoteException;

    Product getProductAfterUpdateUnits(Product product, PackagingUnit unit, boolean inc, int qtyChange) throws RemoteException;

}
