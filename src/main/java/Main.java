import dao.OrderDAO;
import dao.ProductDAO;
import model.PackagingUnit;
import model.Product;
import model.ProductUnit;
import service.OrderService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
//        ProductDAO productDAO = new ProductDAO(Product.class);
//        Product product = productDAO.findById("PF250425000001");
//        System.out.println(product.getSellPrice(PackagingUnit.BOX));

        OrderDAO orderDAO = new OrderDAO(model.Order.class);
        orderDAO.filterOrderByEmpID("EP1501", "2025-04-25").forEach(System.out::println);

    }
}
