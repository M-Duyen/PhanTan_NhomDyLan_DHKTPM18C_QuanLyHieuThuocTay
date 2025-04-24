package dao;

import model.*;
import jakarta.persistence.EntityManager;
import service.ProductService;

import java.sql.Connection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductDAO extends GenericDAO<Product, String> implements ProductService {
    private EntityManager em;

    public ProductDAO(Class<Product> clazz) {
        super(clazz);
    }

    public ProductDAO(EntityManager em, Class<Product> clazz) {
        super(em, clazz);
    }

    @Override
    public boolean createMultiple(List<Product> products) {
        try {
            em.getTransaction().begin();
            for (Product product : products) {
                em.persist(product);
            }
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Lọc danh sách sản phẩm gần hết hạn
     *
     * @return
     */
    @Override
    public List<Product> getProductListNearExpire(){
        return null;
    }
    /**
     * Lọc danh sách sản phẩm có số lượng tồn kho thấp (<=25)
     *
     * @param threshold
     * @return
     */
    @Override
    public List<Product> getLowStockProducts(int threshold) {
        return null;
    }

    /**
     * Lọc danh sách sản phẩm và phân loại
     *
     * @return
     */
    @Override
    public List<Product> fetchProducts() {
        return null;
    }
    /**
     * Tạo mã tự động cho sản phẩm
     *
     * @param numType
     * @param index
     * @return
     */
    @Override
    public String getIDProduct(String numType, int index) {
        return null;
    }

    /**
     * Lấy danh mục của sản phẩm
     *
     * @param productID
     * @return
     */
    @Override
    public String getProductCategory(String productID){
        return null;
    }

    /**
     * Lấy mã sản phẩm không bao gồm mã danh mục
     *
     * @param productID
     * @return
     */
    @Override
    public String getProductID_NotCategory(String productID){
        return null;
    }

    /**
     * Chuyển mã vạch sang mã sản phẩm
     *
     * @param productID
     * @return
     */
    @Override
    public String convertProductID_ToBarcode(String productID){
        return null;
    }

    /**
     * Chuyển mã sản phẩm sang mã vạch
     *
     * @param barcode
     * @return
     */
    @Override
    public String convertBarcode_ToProductID(String barcode){
        return null;
    }

    /**
     * Lọc sản phẩm theo mã vạch
     *
     * @param barcode
     * @return
     */
    @Override
    public Product getProduct_ByBarcode(String barcode){
        return null;
    }
    /**
     * Hàm thay đổi số lượng tồn kho khi hóa đơn được tạo, inc = true thì +, ngược lại thì -
     *
     * @param productID
     * @param qtyChange
     * @param inc
     * @return
     */
    @Override
    public boolean updateProductInStock(String productID, int qtyChange, boolean inc) {
        return false;
    }
    /**
     * Hàm thay đổi số lượng tồn kho khi hóa đơn được tạo, inc = true thì +, ngược lại thì - (Có transaction)
     *
     * @param productID
     * @param qtyChange
     * @param inc
     * @param con
     * @return
     */
    @Override
    public boolean updateProductInStock_WithTransaction(String productID, int qtyChange, PackagingUnit unitEnum, boolean inc, Connection con) {
        return false;
    }
    /**
     * Xử lý cắt tên từ 0 đến ( cho unitNote
     *
     * @param input
     * @return
     */
    @Override
    public String extractUnitName(String input) {
        if (input == null || !input.contains("(")) {
            return null;
        }

        return input.substring(0, input.indexOf("(")).trim();
    }
    /**
     * Lấy index của phần tử trong array
     *
     * @param parts
     * @param element
     * @return
     */
    @Override
    public int getIndexPart_UnitNote(String[] parts, String element) {
        for(int i = 0; i < parts.length; i++) {
            if(element.equals(extractUnitName(parts[i]))) {
                return i;
            }
        }
        return  -1;
    }

    /**
     * Lấy phần tủ tiếp theo trong array theo matcher
     *
     * @param parts
     * @param currentIndex
     * @return
     */
    @Override
    public int getNextConver(String[] parts, int currentIndex) {
        if (currentIndex < 0 || currentIndex >= parts.length - 1) {
            return -1;
        }
        String nextPart = parts[currentIndex + 1];
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(nextPart);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO(Product.class);
        dao.getAll().forEach(System.out::println);
    }




}
