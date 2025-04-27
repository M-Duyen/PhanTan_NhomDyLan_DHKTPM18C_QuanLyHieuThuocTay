package dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import model.*;
import service.ProductService;
import utils.JPAUtil;

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
        // Lấy toàn bộ danh sách sản phẩm một lần
        List<Product> allProducts = fetchProducts();

        // Lọc và xử lý sản phẩm từ Medicine
        List<Product> productFromMedicine = allProducts.stream()
                .filter(product -> product instanceof Medicine)
                .map(product -> (Medicine) product)
                .filter(medicine -> {
                    int stock = getBoxQuantityMedicine(medicine.getUnitNote());
                    return stock <= threshold;
                })
                .collect(Collectors.toList());

        // Lọc và xử lý sản phẩm từ FunctionalFood
        List<Product> productFromFF = allProducts.stream()
                .filter(product -> product instanceof FunctionalFood)
                .map(product -> (FunctionalFood) product)
                .filter(ff -> {
                    int stock = calculateTotalFromUnitNoteFunctionalFood(ff.getUnitNote());
                    return stock <= threshold;
                })
                .collect(Collectors.toList());

        // Lọc và xử lý sản phẩm từ MedicalSupply
        List<Product> productFromMS = allProducts.stream()
                .filter(product -> product instanceof MedicalSupply)
                .map(product -> (MedicalSupply) product)
                .filter(ms -> {
                    int stock = calculateTotalFromUnitNoteFunctionalFood(ms.getUnitNote());
                    return stock <= threshold;
                })
                .collect(Collectors.toList());

        // Kết hợp tất cả sản phẩm có tồn kho thấp vào một danh sách
        List<Product> proListLowStock = new ArrayList<>();
        proListLowStock.addAll(productFromMedicine);
        proListLowStock.addAll(productFromFF);
        proListLowStock.addAll(productFromMS);

        return proListLowStock;
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
    public List<Product> fetchProducts() {
        List<Product> productList = new ArrayList<>();

        try {
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
//                            loadUnitsForProduct(medicine, em);
                            productList.add(medicine);
                        }
                        break;
                    case "CA019":
                        if (p instanceof MedicalSupply) {
                            MedicalSupply supply = (MedicalSupply) p;
//                            loadUnitsForProduct(supply, em);
                            productList.add(supply);
                        }
                        break;
                    case "CA020":
                        if (p instanceof FunctionalFood) {
                            FunctionalFood food = (FunctionalFood) p;
//                            loadUnitsForProduct(food, em);
                            productList.add(food);
                        }
                        break;
                    default:
                        throw new IllegalStateException("Unexpected category ID: " + categoryID);
                }
            }
        } finally {
            em.close();
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
                currentMax = results.stream() // Bắt đầu "stream" (luồng) từ results.
                        .filter(Objects::nonNull) //Bỏ qua những phần tử bị null
                        .mapToInt(Integer::parseInt)// Chuyển từng String thành int.
                        .max()// Tìm số lớn nhất trong danh sách đó.
                        .orElse(0); // Nếu không tìm được số nào (list rỗng), trả về 0 thay vì null để tránh lỗi.
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



    public static void main(String[] args) {
        ProductDAO dao = new ProductDAO(Product.class);
        System.out.println(dao.getProductID_NotCategory("PF021024000004"));
    }


}
