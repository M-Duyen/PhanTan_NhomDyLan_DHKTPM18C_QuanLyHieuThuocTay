package dao;

import jakarta.persistence.TypedQuery;
import model.*;
import jakarta.persistence.EntityManager;
import service.ProductService;
import utils.JPAUtil;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductDAO extends GenericDAO<Product, String> implements ProductService {

    public ProductDAO(Class<Product> clazz) {
        super(clazz);
        this.em = JPAUtil.getEntityManager();
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
    public List<Product> getProductListNearExpire() {
        List<Product> proListNearExpire = new ArrayList<>();
        List<Product> proList = fetchProducts();

        for (Product pro : proList) {
            if (pro.getEndDate().isBefore(LocalDate.now().plusDays(180))) {
                proListNearExpire.add(pro);
            }
        }
        return proListNearExpire;
    }

    /**
     * Lọc danh sách sản phẩm có số lượng tồn kho thấp (<=25)
     *
     * @param threshold
     * @return
     */
    @Override
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockProductList = new ArrayList<>();
        List<Product> proList = fetchProducts();
//TODO: Xử lý chổ thêm chổ này
//        for (Product product : proList) {
//            if (product.getQuantityInStock() <= threshold) {
//                lowStockProductList.add(product);
//            }
//        }
        return lowStockProductList;
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
        String newMaSP = null;
        int currentMax = 0;
        String datePart = new SimpleDateFormat("ddMMyy").format(new Date());

        String jpql = "SELECT MAX(CAST(FUNCTION('SUBSTRING', p.productID, 9, 6) AS int)) " +
                "FROM Product p " +
                "WHERE FUNCTION('SUBSTRING', p.productID, 1, 2) = :numType " +
                "AND FUNCTION('SUBSTRING', p.productID, 3, 6) = :datePart";

        try {
            TypedQuery<Integer> query = em.createQuery(jpql, Integer.class);
            query.setParameter("numType", numType);
            query.setParameter("datePart", datePart);
            Integer max = query.getSingleResult();
            if (max != null) {
                currentMax = max;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int nextMaSP = currentMax + 1 + index;
        newMaSP = numType + datePart + String.format("%06d", nextMaSP);

        return newMaSP;
    }


    /**
     * Lấy danh mục của sản phẩm
     *
     * @param productID
     * @return
     */
    @Override
    public String getProductCategory(String productID) {
        String jpql = "SELECT SUBSTRING(p.productID, 1, 2) FROM Product p WHERE p.productID = :productID";
        return em.createQuery(jpql, String.class)
                .setParameter("productID", productID)
                .getSingleResult();

    }

    /**
     * Lấy mã sản phẩm không bao gồm mã danh mục
     *
     * @param productID
     * @return
     */
    @Override
    public String getProductID_NotCategory(String productID) {
        String jpql = "SELECT SUBSTRING(p.productID, 1, 2) FROM Product p WHERE p.productID = :productID";
        return em.createQuery(jpql, String.class)
                .setParameter("productID", productID)
                .getSingleResult();
    }

    /**
     * Chuyển mã vạch sang mã sản phẩm
     *
     * @param productID
     * @return
     */
    @Override
    public String convertProductID_ToBarcode(String productID) {
        String barcode = new ProductDAO(Product.class).getProductCategory(productID);
        switch (barcode) {
            case "PF":
                return "7" + new ProductDAO(Product.class).getProductID_NotCategory(productID);
            case "PM":
                return "8" + new ProductDAO(Product.class).getProductID_NotCategory(productID);
            case "PS":
                return "9" + new ProductDAO(Product.class).getProductID_NotCategory(productID);
        }
        return barcode;
    }

    /**
     * Chuyển mã sản phẩm sang mã vạch
     *
     * @param barcode
     * @return
     */
    @Override
    public String convertBarcode_ToProductID(String barcode) {
        String productID = barcode.substring(1);
        String temp = String.valueOf(barcode.charAt(0));
        switch (temp) {
            case "7":
                return "PF" + productID;
            case "8":
                return "PM" + productID;
            case "9":
                return "PS" + productID;
        }
        return null;
    }

    /**
     * Lọc sản phẩm theo mã vạch
     *
     * @param barcode
     * @return
     */
    @Override
    public Product getProduct_ByBarcode(String barcode) {
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
        for (int i = 0; i < parts.length; i++) {
            if (element.equals(extractUnitName(parts[i]))) {
                return i;
            }
        }
        return -1;
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

    /**
     * Cập nhật số lượng tồn kho của sản phẩm theo đơn vị
     * inc = true thì +, ngược lại thì -
     *
     * @param product
     * @param unit
     * @param inc
     * @param qtyChange
     * @return
     */
    @Override
    public Product getProductAfterUpdateUnits(Product product, PackagingUnit unit, boolean inc, int qtyChange) {
        if (product == null || qtyChange <= 0) {
            return product;
        }

        Map<PackagingUnit, ProductUnit> productUnits = product.getUnitDetails();
        if (!productUnits.containsKey(unit)) {
            return product;
        }

        Map<PackagingUnit, Integer> unitConversionMap = parseUnitNoteToMap(product.getUnitNote());
        List<PackagingUnit> unitsOrder = new ArrayList<>(unitConversionMap.keySet());

        // Đồng bộ từ đơn vị nhỏ nhất lên lớn nhất
        int toSmallest = 1;
        int startIndex = unitsOrder.indexOf(unit);
        for (int i = startIndex; i < unitsOrder.size() - 1; i++) {
            PackagingUnit nextUnit = unitsOrder.get(i + 1);
            toSmallest *= unitConversionMap.get(nextUnit);
        }

        int qtyChangeInSmallest = qtyChange * toSmallest;

        PackagingUnit smallestUnit = unitsOrder.get(unitsOrder.size() - 1);
        ProductUnit smallestProductUnit = productUnits.get(smallestUnit);
        if (smallestProductUnit == null) {
            smallestProductUnit = new ProductUnit(0.0, 0);
            productUnits.put(smallestUnit, smallestProductUnit);
        }
        int newSmallestStock = inc
                ? smallestProductUnit.getInStock() + qtyChangeInSmallest
                : smallestProductUnit.getInStock() - qtyChangeInSmallest;
        smallestProductUnit.setInStock(newSmallestStock);

        int remain = smallestProductUnit.getInStock();

        int flag = 1;
        for (int i = unitsOrder.size() - 2; i >= 0; i--) {
            PackagingUnit currentUnit = unitsOrder.get(i);
            ProductUnit currentProductUnit = productUnits.get(currentUnit);
            if (currentProductUnit == null) {
                currentProductUnit = new ProductUnit(0.0, 0);
                productUnits.put(currentUnit, currentProductUnit);
            }

            int conversionQty = unitConversionMap.get(unitsOrder.get(i + 1));
            flag *= conversionQty;
            int unitQty = remain / flag;
            currentProductUnit.setInStock(unitQty);
        }

        return product;
    }

    /**
     * Phân tích UnitNote thành đơn vị quy đổi theo THỨ TỰ
     *
     * @param unitNote
     * @return
     */
    private Map<PackagingUnit, Integer> parseUnitNoteToMap(String unitNote) {
        Map<PackagingUnit, Integer> result = new LinkedHashMap<>();
        if (unitNote == null || unitNote.isEmpty()) {
            return result;
        }

        String[] parts = unitNote.split(",");
        for (String part : parts) {
            part = part.trim();
            int idx1 = part.indexOf('(');
            int idx2 = part.indexOf(')');
            if (idx1 >= 0 && idx2 > idx1) {
                String unitName = part.substring(0, idx1).trim();
                int conversionQty = Integer.parseInt(part.substring(idx1 + 1, idx2));
                result.put(PackagingUnit.valueOf(unitName), conversionQty);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO(Product.class);
        //BIN(100), PACK(20)
        //2000, 100
        Product product = productDAO.getProductAfterUpdateUnits(productDAO.findById("PS270425000004"), PackagingUnit.BIN, true, 9);
//         Product  product = productDAO.findById("PF270425000002");
        product.getUnitDetails().forEach((unit, productUnit) -> {
            System.out.println("Unit: " + unit);
            System.out.println("In Stock: " + productUnit.getInStock());
        });
        //233, 1398, 5592
    }
}
