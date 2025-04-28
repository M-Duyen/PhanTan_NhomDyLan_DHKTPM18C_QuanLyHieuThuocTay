package dao;

import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import model.*;
import jakarta.persistence.EntityManager;
import service.ProductService;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductDAO extends GenericDAO<Product, String> implements ProductService {
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
                Vendor vendor = product.getVendor();
                if (vendor != null && em.find(Vendor.class, vendor.getVendorID()) == null) {
                    em.persist(vendor);
                }
                Category category = product.getCategory();
                if (category != null && em.find(Category.class, category.getCategoryID()) == null) {
                    em.persist(category);
                }
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
        try {
            if (em.isOpen()) {  // Kiểm tra xem EntityManager có mở hay không
                List<Object[]> resultList = em.createQuery(
                                "SELECT p.productID, p.productName, u.inStock " +
                                        "FROM Product p JOIN p.unitDetails u " +
                                        "WHERE KEY(u) = 'BOX' AND u.inStock < :threshold", Object[].class
                        )
                        .setParameter("threshold", threshold) // Thêm tham số threshold vào câu truy vấn
                        .getResultList();

                // Xử lý kết quả
                List<Product> finalResult = new ArrayList<>();
                for (Object[] row : resultList) {
                    String productId = (String) row[0];
                    int inStock = ((Number) row[2]).intValue(); // Dùng Number để an toàn giữa Long và Integer

                    Product product = findById(productId);
                    finalResult.add(product);
                }

                return finalResult;
            } else {
                throw new IllegalStateException("EntityManager is closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }



    /**
     * Tính tổng số lượng từ unitNote lấy BOX*BIN
     *
     * @param unitNote
     * @return
     */
    private int calculateTotalFromUnitNoteFunctionalFood(String unitNote) {
        if (unitNote == null || unitNote.isBlank()) {
            return 1; // không có BIN hoặc BOX thì nhân 1
        }

        return Arrays.stream(unitNote.split(","))
                .map(String::trim)
                .filter(s -> s.startsWith("BIN") || s.startsWith("BOX")) // chỉ lấy BIN hoặc BOX
                .map(s -> {
                    int start = s.indexOf('(');
                    int end = s.indexOf(')');
                    if (start != -1 && end != -1 && start < end) {
                        return Integer.parseInt(s.substring(start + 1, end));
                    }
                    return 1; // nếu không đúng định dạng thì mặc định là 1
                })
                .reduce(1, (a, b) -> a * b);
    }

    /** Lấy số lượng BOX trong unitNote
     *
     * @param unitNote
     * @return
     */

    public int getBoxQuantityMedicine(String unitNote) {
        if (unitNote == null || unitNote.isBlank()) {
            return 0; // không có gì thì trả 0
        }

        return Arrays.stream(unitNote.split(","))
                .map(String::trim)
                .filter(s -> s.startsWith("BOX")) // chỉ lấy dòng bắt đầu bằng BOX
                .findFirst()
                .map(s -> {
                    int start = s.indexOf('(');
                    int end = s.indexOf(')');
                    if (start != -1 && end != -1 && start < end) {
                        return Integer.parseInt(s.substring(start + 1, end));
                    }
                    return 0; // nếu format lỗi
                })
                .orElse(0); // nếu không tìm thấy BOX
    }

    /** Lấy số lượng BIN trong unitNote BIN * PACK
     *
     * @param unitNote
     * @return
     */
    private int calculateBinTimesPackMedicalSupply(String unitNote) {
        if (unitNote == null || unitNote.isBlank()) {
            return 0; // Nếu không có dữ liệu thì trả 0
        }

        int bin = 1;
        int pack = 1;

        for (String part : unitNote.split(",")) {
            part = part.trim();
            if (part.startsWith("BIN")) {
                int start = part.indexOf('(');
                int end = part.indexOf(')');
                if (start != -1 && end != -1 && start < end) {
                    bin = Integer.parseInt(part.substring(start + 1, end));
                }
            } else if (part.startsWith("PACK")) {
                int start = part.indexOf('(');
                int end = part.indexOf(')');
                if (start != -1 && end != -1 && start < end) {
                    pack = Integer.parseInt(part.substring(start + 1, end));
                }
            }
        }

        return bin * pack;
    }


    /**
     * Lọc danh sách sản phẩm và phân loại
     *
     * @return
     */
    @Override
    @Transactional
    public List<Product> fetchProducts() {
        List<Product> productList = new ArrayList<>();

        // Lấy product và productID
        String jpql = "SELECT p.productID, p FROM Product p";
        List<Object[]> results = em.createQuery(jpql, Object[].class).getResultList();

        for (Object[] row : results) {
            Product p = (Product) row[1];
            Category category = p.getCategory();
            String categoryID = category.getCategoryID();

            switch (categoryID) {
                case "CA001": case "CA002": case "CA003": case "CA004":
                case "CA005": case "CA006": case "CA007": case "CA008":
                case "CA009": case "CA010": case "CA011": case "CA012":
                case "CA013": case "CA014": case "CA015": case "CA016":
                case "CA017": case "CA018":
                    if (p instanceof Medicine) {
                        Medicine medicine = (Medicine) p;
                        productList.add(medicine);
                    }
                    break;
                case "CA019":
                    if (p instanceof MedicalSupply) {
                        MedicalSupply supply = (MedicalSupply) p;
                        productList.add(supply);
                    }
                    break;
                case "CA020":
                    if (p instanceof FunctionalFood) {
                        FunctionalFood food = (FunctionalFood) p;
                        productList.add(food);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected category ID: " + categoryID);
            }
        }

        return productList;
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

        String jpql = "SELECT SUBSTRING(p.productID, 9, 6) FROM Product p WHERE SUBSTRING(p.productID, 3, 6) = :datePart";

        try {
            TypedQuery<String> query = em.createQuery(jpql, String.class);
            query.setParameter("datePart", datePart);
            List<String> results = query.getResultList();

            if (results != null && !results.isEmpty()) {
                currentMax = results.stream()
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::parseInt)
                        .max()
                        .orElse(0);
            }

            int nextMaSP = currentMax + 1 + (index == 0 ? 0 : index);
            newMaSP = numType + datePart + String.format("%06d", nextMaSP);

        } catch (Exception e) {
            e.printStackTrace();
        }

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
        String productID = convertBarcode_ToProductID(barcode);
        return this.findById(productID);
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
     * @return
     */
    @Override
    public boolean updateProductInStock(String productID, int qtyChange, PackagingUnit unitEnum, boolean inc) {

        return false;
    }

    //10 BOX, 100 PACK, 600 PILL
    //BOX: 9, PACK: 90 (100 - 1 * 10), ...
    public Map<PackagingUnit, Integer> getUnitNoteChangeSelling(String productID, int qtyChange, PackagingUnit sellUnit) {
        Product product = findById(productID);
        Map<PackagingUnit, Integer> result = new LinkedHashMap<>();

        Map<PackagingUnit, Integer> unitNoteMap = product.parseUnitNote(); //BOX: 293, BLISTER_PACK: 10, PILL: 6
        List<PackagingUnit> unitLevels = new ArrayList<>(unitNoteMap.keySet()); //BOX, BLISTER_PACK, PILL

        int smallestQty = 0;
        boolean flag = false;
        for(int i = 0;  i < unitLevels.size();  i++) {
            System.out.println(unitLevels.get(i));
            if(unitLevels.get(i).equals(sellUnit)) {
                flag = true;
            }
            if(flag) {
                if((i + 1 ) >= unitLevels.size()) {
                    break;
                }
                int uniteNotePart = unitNoteMap.get(unitLevels.get(i + 1));

                smallestQty = qtyChange * uniteNotePart;
            }
        }

        System.out.println(smallestQty);

        boolean first = true;
        for(int i = unitLevels.size() - 1;  i >= 0;  i--) {
            if(!first) {
                result.put(unitLevels.get(i), (int) Math.ceil(result.get(unitLevels.get(i + 1)) / unitNoteMap.get(unitLevels.get(i + 1))));
            } else {
                PackagingUnit unit = unitLevels.get(i);
                int stock = product.getInstockQuantity(unit);
                result.put(unitLevels.get(i), stock - smallestQty);
                first = false;

            }
        }
        return result;
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
        ProductDAO dao = new ProductDAO(Product.class);
        System.out.println(dao.getIDProduct("PM", 0));
//        System.out.println(dao.findById("OC2804250903002").ge);
    }


}
