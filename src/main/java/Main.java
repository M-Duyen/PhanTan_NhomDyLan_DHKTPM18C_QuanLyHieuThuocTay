import dao.AccountDAO;
import dao.ProductDAO;
import model.Account;
import model.PackagingUnit;
import model.Product;
import model.ProductUnit;
import service.AccountService;
import service.ProductService;
import service.impl.AccountServiceImpl;
import service.impl.ProductServiceImpl;

import java.rmi.RemoteException;

public class Main {
    public static void main(String[] args) throws RemoteException {
//        ProductDAO productDAO = new ProductDAO(Product.class);
//        Product product = productDAO.findById("PF250425000001");
//        System.out.println(product.getSellPrice(PackagingUnit.BOX));

        AccountService accountService = new AccountServiceImpl(new AccountDAO(Account.class));
        System.out.println(accountService.containUserName("EP0302"));

    }
}
