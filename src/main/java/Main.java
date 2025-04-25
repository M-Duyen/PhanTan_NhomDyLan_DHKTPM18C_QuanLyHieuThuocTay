import dao.ProductDAO;
import model.PackagingUnit;
import model.Product;
import model.ProductUnit;

public class Main {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO(Product.class);
        Product product = productDAO.findById("PF250425000001");
        System.out.println(product.getSellPrice(PackagingUnit.BOX));
    }
}
