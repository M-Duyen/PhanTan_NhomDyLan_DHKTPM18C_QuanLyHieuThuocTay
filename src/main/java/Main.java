import dao.*;
import model.PackagingUnit;
import model.Product;
import model.ProductUnit;
import service.OrderService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {
        ProductDAO productDAO = new ProductDAO(Product.class);
//        Product product = productDAO.findById("PF250425000001");
//        System.out.println(product.getSellPrice(PackagingUnit.BOX));
//        productDAO.searchByMultipleCriteria("product", "").forEach(System.out::println);
//        productDAO.getAll()
//                .stream()
//                .filter(p -> p.getVendor().getVendorName().contains("Agimexpharm"))
//                .forEach(System.out::println);

//        productDAO.getProductListNearExpire().forEach(System.out::println);
        productDAO.getLowStockProducts(210)
                .forEach(product -> System.out.println("Kết quả: " + product));
//        productDAO.searchByMultipleCriteria("product","2025-01-17").forEach(System.out::println);
//        productDAO.getAll().forEach(System.out::println);




        OrderDAO orderDAO = new OrderDAO(model.Order.class);
//        orderDAO.filterOrderByEmpID("EP1501", "2025-04-25").forEach(System.out::println);
//        orderDAO.searchByMultipleCriteria("order", "Vười").forEach(System.out::println);
//        orderDAO.getAll().forEach(System.out::println);

//        PromotionTypeDAO promotionTypeDAO = new PromotionTypeDAO(model.PromotionType.class);
//        promotionTypeDAO.searchByMultipleCriteria("promotionType", "Khuyến mãi").forEach(System.out::println);

        PromotionDAO promotionDAO = new PromotionDAO(model.Promotion.class);
//        promotionDAO.getPromotionListByStatus(true).forEach(System.out::println);
//        promotionDAO.updatePromotionStatus();

        AccountDAO accountDAO = new AccountDAO(model.Account.class);
//        System.out.println(accountDAO.updatePasswordByAccountID("EP1501", "EP1501"));
    }
}
