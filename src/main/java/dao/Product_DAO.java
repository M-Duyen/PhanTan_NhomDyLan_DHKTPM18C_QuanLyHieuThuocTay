package dao;

import database.ConnectDB;
import entity.*;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Product_DAO {
    private ArrayList<Product> cachedProductList = new ArrayList<>();
    public Product_DAO() {
    }
    public static Product_DAO getInstance(){
        return new Product_DAO();
    }
    /**
     * Lọc tất cả danh sách tất cả sản phẩm
     *
     * @return
     */
    public ArrayList<Product> getProductList() {
        Vendor_DAO vendor_dao = new Vendor_DAO();
        Promotion_DAO promotion_dao = new Promotion_DAO();
        Category_DAO category_dao = new Category_DAO();
        ArrayList<Product> productList = new ArrayList<>();
        PreparedStatement stmt = null;
        Connection con = ConnectDB.getConnection();

        try {
            stmt = con.prepareStatement("SELECT * FROM Product");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String proID = rs.getString(1);
                String proName = rs.getString(2);
                int quanInStock = rs.getInt(3);
                double tax = rs.getDouble(4);
                double purchasePrice = rs.getDouble(5);
                String resNumber = rs.getString(6);
                LocalDate endDate = rs.getDate(7).toLocalDate();
                String maKM = rs.getString(8);
                String maNCC = rs.getString(9);
                String maLoai = rs.getString(10);

                Promotion promotion = new Promotion();

                if (maKM == null) {
                    promotion = new Promotion();
                } else {
                    ArrayList<Promotion> promotionList = promotion_dao.getPromotionListByCriterous(maKM);
                    promotion = promotionList.get(0);
                }

                ArrayList<Vendor> vendorList = vendor_dao.getVendorListByCriterias(maNCC);
                Vendor vendor = vendorList.get(0);

                ArrayList<Category> categoryList = category_dao.getCategoryByCriterous(maLoai);
                Category category = categoryList.get(0);
                //TODO: Xử lý tồn kho
                Product product = new Product(proID, proName, resNumber, purchasePrice, tax, endDate, promotion, vendor, category);
                productList.add(product);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return productList;
    }

    /**
     * Lọc sản phẩm theo mã
     *
     * @param productID
     * @return
     */
    public Product getProduct_ByID(String productID) {
        Product product = null;
        PreparedStatement stmt = null;
        Connection con = ConnectDB.getConnection();
        try {
            stmt = con.prepareStatement("SELECT * FROM Product WHERE productID = ?");
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String proName = rs.getString("productName");
                double tax = rs.getDouble("taxPercentage");
                double purchasePrice = rs.getDouble("purchasePrice");
                String resNumber = rs.getString("registrationNumber");
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String promotionID = rs.getString("promotionID");
                String vendorID = rs.getString("vendorID");
                String categoryID = rs.getString("categoryID");
                String unitNote = rs.getString("unitNote");
                Promotion promotion = new Promotion();
                if(promotionID ==  null){
                    promotion = new Promotion();
                }else{
                    ArrayList<Promotion> promotionList = new Promotion_DAO().getPromotionListByCriterous(promotionID);
                    promotion = promotionList.getFirst();
                }

                Vendor vendor = new Vendor_DAO().getVendor_ByID(vendorID);

                ArrayList<Category> categoryList = new Category_DAO().getCategoryByCriterous(categoryID);
                Category category = categoryList.getFirst();

                product = new Product(productID, proName, resNumber, purchasePrice, tax, endDate, promotion, vendor, category, unitNote);
            }

            stmt = con.prepareStatement("SELECT * FROM ProductUnit WHERE productID = ? ");
            stmt.setString(1, productID);
            ResultSet rs2 = stmt.executeQuery();

            while (rs2.next()){
                String unitID = rs2.getString("unitID");
                int inStock = rs2.getInt("inStock");
                double sellPrice = rs2.getDouble("sellPrice");

                PackagingUnit unit = PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unitID).getUnitName());
                if (product != null) {
                    product.addUnit(unit, sellPrice, inStock);
                } else {
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Lọc sản phẩm theo tiêu chí bất kỳ
     *
     * @param criterous
     * @return
     */
    public ArrayList<Product> getProductListByCriterious(String criterous) {
        ArrayList<Product> proListByCri = new ArrayList<>();
        ArrayList<Product> proList = fetchProducts();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = null;

        if (criterous != null && !criterous.trim().isEmpty()) {
            try {
                endDate = LocalDate.parse(criterous.trim(), dateFormatter);
            } catch (DateTimeParseException e) {

            }
        }

        for (Product pro : proList) {
            if (pro.getProductID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getProductName().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getRegistrationNumber().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    (pro.getEndDate().equals(endDate)) ||
                    pro.getVendor().getVendorID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getVendor().getVendorName().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    (pro instanceof Medicine && ((Medicine) pro).getAdministrationRoute().getAdministrationID().toLowerCase().trim().contains(criterous.toLowerCase().trim())) ||
                    pro.getVendor().getCountry().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getCategory().getCategoryID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getCategory().getCategoryName().toLowerCase().trim().contains(criterous.toLowerCase().trim())) {
                proListByCri.add(pro);
            }
        }
        return proListByCri;
    }

    public ArrayList<Product> getProductListByCriterious(String criterous, ArrayList<Product> temp) {
        ArrayList<Product> proListByCri = new ArrayList<>();
        ArrayList<Product> proList = temp;

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = null;

        if (criterous != null && !criterous.trim().isEmpty()) {
            try {
                endDate = LocalDate.parse(criterous.trim(), dateFormatter);
            } catch (DateTimeParseException e) {

            }
        }

        for (Product pro : proList) {
            if (pro.getProductID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getProductName().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getRegistrationNumber().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    (pro.getEndDate().equals(endDate)) ||
                    pro.getVendor().getVendorID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getVendor().getVendorName().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    (pro instanceof Medicine && ((Medicine) pro).getAdministrationRoute().getAdministrationID().toLowerCase().trim().contains(criterous.toLowerCase().trim())) ||
                    pro.getVendor().getCountry().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getCategory().getCategoryID().toLowerCase().trim().contains(criterous.toLowerCase().trim()) ||
                    pro.getCategory().getCategoryName().toLowerCase().trim().contains(criterous.toLowerCase().trim())) {
                proListByCri.add(pro);
            }
        }
        return proListByCri;
    }

    /**
     * Thêm sản phẩm
     * @param product
     * @return
     */
    public boolean addProduct(Product product) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;

        String sql = "INSERT INTO Product (productID, productName, quantityInStock, taxPercentage, purchasePrice," +
                " registrationNumber, promotionID, vendorID, categoryID) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int n = 0;

        try {
            stmt = con.prepareStatement(sql);
            stmt.setString(1, product.getProductID());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, 4);//product.getQuantityInStock()
            stmt.setDouble(4, product.getTaxPercentage());
            stmt.setDouble(5, product.getPurchasePrice());
            stmt.setString(6, product.getRegistrationNumber());
            stmt.setString(7, product.getPromotion().getPromotionID());
            stmt.setString(8, product.getVendor().getVendorID());
            stmt.setString(9, product.getCategory().getCategoryID());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return n > 0;
    }

    /**
     * Thêm nhiều sản phẩm
     *
     * @param listProduct
     * @return
     */
    public boolean addManyProduct(ArrayList<Product> listProduct) {
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        int n = 0;
        String productID = "";
        try {
            con.setAutoCommit(false);
            for (Product product : listProduct) {
                String sql = "INSERT INTO Product (productID, productName, taxPercentage, purchasePrice," +
                        " registrationNumber, promotionID, vendorID, categoryID, endDate, unitNote) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, product.getProductID());
                stmt.setString(2, product.getProductName());
                stmt.setDouble(3, product.getTaxPercentage());
                stmt.setDouble(4, product.getPurchasePrice());
                stmt.setString(5, product.getRegistrationNumber());
                stmt.setString(10, product.getUnitNote());
                if (product.getPromotion() == null) {
                    stmt.setNull(6, 0);
                } else {
                    product.getPromotion().getPromotionID();
                }
                stmt.setString(7, product.getVendor() == null ? "" : product.getVendor().getVendorID());
                stmt.setString(8, product.getCategory() == null ? "" : product.getCategory().getCategoryID());
                stmt.setDate(9, java.sql.Date.valueOf(product.getEndDate()));
                stmt.executeUpdate();
                productID = product.getProductID();

                if (product.isMedicalSupplies()) {
                    String medicalSql = "INSERT INTO MedicalSupplies (medicalSupplyID, medicalSupplyName) VALUES (?, ?)";
                    stmt = con.prepareStatement(medicalSql);
                    stmt.setString(1, productID);
                    stmt.setString(2, ((MedicalSupplies) product).getMedicalSupplyType());
                    stmt.executeUpdate();
                } else if (product.isMedicine()) {
                    String medicalSql = "INSERT INTO Medicine (medicineID, activeIngredient, conversionUnit, administrationID) VALUES (?, ?, ?, ?)";
                    stmt = con.prepareStatement(medicalSql);
                    stmt.setString(1, productID);
                    stmt.setString(2, ((Medicine) product).getActiveIngredient());
                    stmt.setString(3, ((Medicine) product).getConversionUnit());
                    stmt.setString(4, ((Medicine) product).getAdministrationRoute().getAdministrationID());
                    stmt.executeUpdate();
                } else if (product.isFunctionalFood()) {
                    String medicalSql = "INSERT INTO FunctionalFood (functionalFoodID, mainNutrients, supplementaryIngredients) VALUES (?, ?, ?)";
                    stmt = con.prepareStatement(medicalSql);
                    stmt.setString(1, productID);
                    stmt.setString(2, ((FunctionalFood) product).getMainNutrients());
                    stmt.setString(3, ((FunctionalFood) product).getSupplementaryIngredients());
                    stmt.executeUpdate();
                }


                //Xử lý đơn vị, tồn kho và giá cho mỗi đơn vị
                for (Map.Entry<PackagingUnit, Double> entry : product.getUnitPrice().entrySet()) {
                    String unitProduct = "INSERT INTO ProductUnit(productID, unitID, inStock, sellPrice) VALUES (?, ?, ?, ?)";
                    stmt = con.prepareStatement(unitProduct);
                    String unitID = Unit_DAO.getInstance().getUnitByName(entry.getKey().name()).getFirst().getUnitID();
                    // Set các giá trị cho PreparedStatement
                    stmt.setString(1, productID);
                    stmt.setString(2, unitID);
                    stmt.setInt(3, product.getInStockByUnit(entry.getKey()));
                    stmt.setDouble(4, entry.getValue()); //entry.getValue()
                    stmt.executeUpdate();
                }

                n = 10;
                con.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                JOptionPane.showMessageDialog(null, "Lỗi: Trùng đường đăng ký, vui lòng kiểm tra lại");
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return n > 0;
    }


    /**
     * Lọc danh sách sản phẩm gần hết hạn
     *
     * @return
     */
    public ArrayList<Product> getProductListNearExpire() {
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<Product> proListNearExpire = new ArrayList<>();
        ArrayList<Product> proList = fetchProducts();

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
    public ArrayList<Product> getLowStockProducts(int threshold) {
        Connection con = ConnectDB.getInstance().getConnection();
        ArrayList<Product> lowStockProductList = new ArrayList<>();
        ArrayList<Product> proList = fetchProducts();
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
    public ArrayList<Product> fetchProducts() {
        Vendor_DAO vendor_dao = new Vendor_DAO();
        Promotion_DAO promotion_dao = new Promotion_DAO();
        Category_DAO category_dao = new Category_DAO();
        AdministrationRoute_DAO administrationRoute_dao = new AdministrationRoute_DAO();
        Connection con = ConnectDB.getConnection();
        ArrayList<Product> productList = new ArrayList<>();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM\n" +
                    "Product p \n" +
                    "LEFT JOIN Medicine m ON p.productID = m.medicineID\n" +
                    "LEFT JOIN MedicalSupplies s ON p.productID = s.medicalSupplyID\n" +
                    "LEFT JOIN FunctionalFood f ON p.productID = f.functionalFoodID");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String proID = rs.getString("productID");
                String proName = rs.getString("productName");
                double tax = rs.getDouble("taxPercentage");
                double purchasePrice = rs.getDouble("purchasePrice");
                String resNumber = rs.getString("registrationNumber");
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String conversionUnit = rs.getString("conversionUnit");
                Promotion promotion;

                if (rs.getString("promotionID") == null) {
                    promotion = new Promotion();
                } else {
                    ArrayList<Promotion> promotionList = promotion_dao.getPromotionListByCriterous(rs.getString("promotionID"));
                    promotion = promotionList.get(0);
                }

                ArrayList<Vendor> vendorList = vendor_dao.getVendorListByCriterias(rs.getString("vendorID"));
                Vendor vendor = vendorList.get(0);

                ArrayList<Category> categoryList = category_dao.getCategoryByCriterous(rs.getString("categoryID"));
                Category category = categoryList.get(0);

                String activeIngredient = rs.getString("activeIngredient");
                AdministrationRoute administrationRoute;

                if (rs.getString("administrationID") == null) {
                    administrationRoute = new AdministrationRoute();
                } else {
                    ArrayList<AdministrationRoute> administrationRoutesList = administrationRoute_dao.getAdminListByCriterous(rs.getString("administrationID"));
                    administrationRoute = administrationRoutesList.get(0);
                }

                String medicalSuppliesName = rs.getString("medicalSupplyName");
                String mainNutrients = rs.getString("mainNutrients");

                String supplementaryIngredients = rs.getString("supplementaryIngredients");

                //Tìm tất cả đơn vị của một sản phẩm
                String sql = "SELECT * " +
                        "FROM ProductUnit " +
                        "WHERE productID = ?";
                switch (category.getCategoryID()) {
                    case "CA001":
                    case "CA002":
                    case "CA003":
                    case "CA004":
                    case "CA005":
                    case "CA006":
                    case "CA007":
                    case "CA008":
                    case "CA009":
                    case "CA010":
                    case "CA011":
                    case "CA012":
                    case "CA013":
                    case "CA014":
                    case "CA015":
                    case "CA016":
                    case "CA017":
                    case "CA018":
                        Medicine medicine = new Medicine(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, activeIngredient, conversionUnit, administrationRoute);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, medicine.getProductID());
                        ResultSet rsUnit = stmt.executeQuery();
                        while (rsUnit.next()) {

                            String unit = rsUnit.getString("unitID");
                            int inStock = rsUnit.getInt("inStock");
                            double sellPrice = rsUnit.getDouble("sellPrice");
                            medicine.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(medicine);
                        break;
                    case "CA019":
                        MedicalSupplies medical = new MedicalSupplies(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, medicalSuppliesName);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, medical.getProductID());
                        ResultSet rsUnitMedical = stmt.executeQuery();
                        while (rsUnitMedical.next()) {
                            String unit = rsUnitMedical.getString("unitID");
                            int inStock = rsUnitMedical.getInt("inStock");
                            double sellPrice = rsUnitMedical.getDouble("sellPrice");
                            medical.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(medical);
                        break;
                    case "CA020":
                        FunctionalFood tempFunctionFood = new FunctionalFood(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, mainNutrients, supplementaryIngredients);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, tempFunctionFood.getProductID());
                        ResultSet rsUnitFuntionFood = stmt.executeQuery();
                        while (rsUnitFuntionFood.next()) {
                            String unit = rsUnitFuntionFood.getString("unitID");
                            int inStock = rsUnitFuntionFood.getInt("inStock");
                            double sellPrice = rsUnitFuntionFood.getDouble("sellPrice");
                            tempFunctionFood.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(tempFunctionFood);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + category.getCategoryID());
                }
            }
            cachedProductList = new ArrayList<>(productList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public String getIDProduct(String numType, int index) {
        Connection con = ConnectDB.getInstance().getConnection();
        String newMaSP = null;
        int currentMax = 0;
        String datePart = new SimpleDateFormat("ddMMyy").format(new Date());
        String query = "SELECT MAX(CAST(SUBSTRING(productID, 9, 6) AS INT)) AS maxMaSP " +
                "FROM Product " +
                "WHERE SUBSTRING(productID, 3, 6) = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            preparedStatement.setString(1, datePart);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getObject("maxMaSP") != null) {
                currentMax = resultSet.getInt("maxMaSP");
            }
            int nextMaSP = currentMax + 1 + (index == 0 ? 0 : index);
            newMaSP = numType + datePart + String.format("%06d", nextMaSP);

        } catch (SQLException e) {
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
    public String getProductCategory(String productID){
        Connection con = ConnectDB.getInstance().getConnection();
        String categoryStr = null;
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT CAST(SUBSTRING(productID, 1, 2) AS char) AS categoryType FROM Product WHERE productID = ?");
            sm.setString(1, productID);
            ResultSet rs = sm.executeQuery();

            while (rs.next()) {
                categoryStr = rs.getString("categoryType");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryStr;
    }

    /**
     * Lấy mã sản phẩm không bao gồm mã danh mục
     *
     * @param productID
     * @return
     */
    public String getProductID_NotCategory(String productID){
        Connection con = ConnectDB.getInstance().getConnection();
        String categoryStr = null;
        PreparedStatement sm = null;

        try {
            sm = con.prepareStatement("SELECT CAST(SUBSTRING(productID, 1, 2) AS char) AS categoryType FROM Product WHERE productID = ?");
            sm.setString(1, productID);
            ResultSet rs = sm.executeQuery();

            while (rs.next()){
                categoryStr = rs.getString("categoryType");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categoryStr;
    }

    /**
     * Chuyển mã vạch sang mã sản phẩm
     *
     * @param productID
     * @return
     */
    public String convertProductID_ToBarcode(String productID){
        String barcode = new Product_DAO().getProductCategory(productID);
        switch (barcode){
            case "PF":
                return "7" + new Product_DAO().getProductID_NotCategory(productID);
            case "PM":
                return "8" + new Product_DAO().getProductID_NotCategory(productID);
            case "PS":
                return "9" + new Product_DAO().getProductID_NotCategory(productID);
        }
        return barcode;
    }

    /**
     * Chuyển mã sản phẩm sang mã vạch
     *
     * @param barcode
     * @return
     */
    public String convertBarcode_ToProductID(String barcode){
        String productID = barcode.substring(1);
        String temp = String.valueOf(barcode.charAt(0));
        switch (temp){
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
    public Product getProduct_ByBarcode(String barcode){
        Product product = null;
        PreparedStatement stmt = null;
        String productID = new Product_DAO().convertBarcode_ToProductID(barcode);
        Connection con = ConnectDB.getConnection();
        try {
            stmt = con.prepareStatement("SELECT * FROM Product WHERE productID = ? ");
            stmt.setString(1, productID);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                String proName = rs.getString("productName");
                double tax = rs.getDouble("taxPercentage");
                double purchasePrice = rs.getDouble("purchasePrice");
                String resNumber = rs.getString("registrationNumber");
                LocalDate endDate = rs.getDate("endDate").toLocalDate();
                String promotionID = rs.getString("promotionID");
                String vendorID = rs.getString("vendorID");
                String categoryID = rs.getString("categoryID");

                Promotion promotion = new Promotion();
                if(promotionID ==  null){
                    promotion = new Promotion();
                }else{
                    ArrayList<Promotion> promotionList = new Promotion_DAO().getPromotionListByCriterous(promotionID);
                    promotion = promotionList.getFirst();
                }

                Vendor vendor = new Vendor_DAO().getVendor_ByID(vendorID);

                ArrayList<Category> categoryList = new Category_DAO().getCategoryByCriterous(categoryID);
                Category category = categoryList.getFirst();

                product = new Product(productID, proName, resNumber, purchasePrice, tax, endDate, promotion, vendor, category);
            }

            stmt = con.prepareStatement("SELECT * FROM ProductUnit WHERE productID = ? ");
            stmt.setString(1, productID);
            ResultSet rs2 = stmt.executeQuery();

            while (rs2.next()){
                String unitID = rs2.getString("unitID");
                int inStock = rs2.getInt("inStock");
                double sellPrice = rs2.getDouble("sellPrice");

                PackagingUnit unit = PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unitID).getUnitName());
                if (product != null) {
                    product.addUnit(unit, sellPrice, inStock);
                } else {
                    System.out.println(product);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    /**
     * Hàm thay đổi số lượng tồn kho khi hóa đơn được tạo, inc = true thì +, ngược lại thì -
     *
     * @param productID
     * @param qtyChange
     * @param inc
     * @return
     */
    public boolean updateProductInStock(String productID, int qtyChange, boolean inc) {
        int quantity = 0;
        Connection con = ConnectDB.getConnectDB_H();
        PreparedStatement sm = null;
        try {
            //TODO: xử lý tồn kho cho từng đơn vị
//            quantity = new Product_DAO().getProduct_ByID(productID).getQuantityInStock();

            sm = con.prepareStatement("UPDATE Product " +
                    "SET quantityInStock = ? " +
                    "WHERE productID = ?");

            int newQuantity = inc ? (quantity + qtyChange) : (quantity - qtyChange);
            sm.setInt(1, newQuantity);
            sm.setString(2, productID);

            return sm.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
    public boolean updateProductInStock_WithTransaction(String productID, int qtyChange, PackagingUnit unitEnum, boolean inc, Connection con) {
        int quantity = 0;
        PreparedStatement sm1 = null;
        PreparedStatement sm2 = null;
        int result = -1;

        try {
            Product product = new Product_DAO().getProduct_ByID(productID);
            String unitID = Unit_DAO.getInstance().getUnit_ByEnumUnit(unitEnum).getUnitID();
            quantity = product.getInStockByUnit(unitEnum);

            int newQuantity = inc ? (quantity + qtyChange) : (quantity - qtyChange);

            if(!inc) {
                sm1 = con.prepareStatement("SELECT unitNote " +
                        "FROM ProductUnit PU JOIN Product P ON P.productID = PU.productID " +
                        "WHERE P.productID = ? AND unitID = ?");
                sm1.setString(1, productID);
                sm1.setString(2, unitID);
                ResultSet rs = sm1.executeQuery();

                String unitNote = "";
                if (rs.next()) {
                    unitNote = rs.getString("unitNote");
                }
                String[] parts = unitNote.split(",\\s*");
                boolean matches = false;

                if(unitEnum.name().equals(extractUnitName(parts[0]))) {
                    for(String part : parts) {
                        Pattern pattern = Pattern.compile("([A-Z_ ]+)(?:\\((\\d+)\\))?");
                        Matcher matcher = pattern.matcher(part);

                        if(matcher.matches()) {
                            String enumUnit = matcher.group(1).trim();
                            int converUnit = Integer.parseInt(matcher.group(2).trim());

                            sm2 = con.prepareStatement("UPDATE ProductUnit " +
                                    "SET inStock = ? " +
                                    "WHERE productID = ? AND unitID IN (SELECT unitID FROM Unit WHERE name = ?)");
                            sm2.setInt(1, newQuantity);
                            sm2.setString(2, productID);
                            sm2.setString(3, enumUnit);
                            result = sm2.executeUpdate();

                            if(!matches) {
                                matches = true;
                            } else {
                                newQuantity = (int) Math.ceil((double) newQuantity * converUnit);
                            }
                        }
                    }
                } else if(unitEnum.name().equals(extractUnitName(parts[parts.length - 1]))) {
                    for(int i = parts.length - 1; i >= 0; i--) {
                        String part = parts[i];
                        Pattern pattern = Pattern.compile("([A-Z_ ]+)(?:\\((\\d+)\\))?");
                        Matcher matcher = pattern.matcher(part);

                        if(matcher.matches()) {
                            String enumUnit = matcher.group(1).trim();
                            int converUnit = Integer.parseInt(matcher.group(2).trim());

                            if(enumUnit.equals(unitEnum.name())) {
                                matches = true;
                            }
                            if(matches) {
                                sm2 = con.prepareStatement("UPDATE ProductUnit " +
                                        "SET inStock = ? " +
                                        "WHERE productID = ? AND unitID IN (SELECT unitID FROM Unit WHERE name = ?)");
                                sm2.setInt(1, newQuantity);
                                sm2.setString(2, productID);
                                sm2.setString(3, enumUnit);
                                result = sm2.executeUpdate();

                                newQuantity = (int) Math.ceil((double) newQuantity / converUnit);
                            }
                        }
                    }
                } else {
                    int unitIndex = getIndexPart_UnitNote(parts, unitEnum.name());

                    for(String part : parts) {
                        Pattern pattern = Pattern.compile("([A-Z_ ]+)(?:\\((\\d+)\\))?");
                        Matcher matcher = pattern.matcher(part);
                        int updateQty = 0;
                        if(matcher.matches()) {
                            String enumUnit = matcher.group(1).trim();
                            int converUnit = Integer.parseInt(matcher.group(2).trim());

                            int partIndex = getIndexPart_UnitNote(parts, extractUnitName(part));
                            if(partIndex < unitIndex) {
                                int newConver = getNextConver(parts, partIndex);

                                updateQty = (int) Math.ceil((double) newQuantity / newConver);
                            } else if(partIndex > unitIndex) {
                                updateQty = (int) Math.ceil((double) newQuantity * converUnit);
                            } else {
                                updateQty = newQuantity;
                            }

                            sm2 = con.prepareStatement("UPDATE ProductUnit " +
                                    "SET inStock = ? " +
                                    "WHERE productID = ? AND unitID IN (SELECT unitID FROM Unit WHERE name = ?)");
                            sm2.setInt(1, updateQty);
                            sm2.setString(2, productID);
                            sm2.setString(3, enumUnit);
                            result = sm2.executeUpdate();
                        }
                    }
                }
            }

            return result >= 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ArrayList<Product> searchProductWithCriteria(String productName, JComboBox<String> cbbCategory, JComboBox<String> cbbVendor,
                                                        JComboBox<String> cbbAdministrationRoute) {

        Connection con = ConnectDB.getConnection();
        Promotion_DAO promotion_dao = new Promotion_DAO();
        Vendor_DAO vendor_dao = new Vendor_DAO();
        Category_DAO category_dao = new Category_DAO();
        AdministrationRoute_DAO administrationRoute_dao = new AdministrationRoute_DAO();
        PreparedStatement stmt = null;
        ArrayList<Product> productList = new ArrayList<>();
        try {
            // Tạo câu lệnh gọi thủ tục lưu trữ
            StringBuilder query = new StringBuilder("EXEC sp_SearchProduct @ProductName = ?, @CategoryName = ?, @VendorName = ?, @AdministrationRouteID = ?");

            stmt = con.prepareStatement(query.toString());

            // Lấy giá trị từ JTextField và JComboBox
            String categoryName = cbbCategory.getSelectedItem() != null ? cbbCategory.getSelectedItem().toString() : null;
            String vendorName = cbbVendor.getSelectedItem() != null ? cbbVendor.getSelectedItem().toString() : null;
            String administrationRouteName = cbbAdministrationRoute.getSelectedItem() != null ? cbbAdministrationRoute.getSelectedItem().toString() : null;

            // Gán giá trị vào các tham số trong câu truy vấn
            stmt.setString(1, productName.isEmpty() ? null : productName); // Nếu trống, gán null
            stmt.setString(2, categoryName);
            stmt.setString(3, vendorName);
            stmt.setString(4, administrationRouteName);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String proID = rs.getString("productID");
                String proName = rs.getString("productName");
                double tax = rs.getDouble("taxPercentage");
                double purchasePrice = rs.getDouble("purchasePrice");
                String resNumber = rs.getString("registrationNumber");
                LocalDate endDate = rs.getDate("endDate").toLocalDate();

                Promotion promotion;

                if (rs.getString("promotionID") == null) {
                    promotion = new Promotion();
                } else {
                    ArrayList<Promotion> promotionList = promotion_dao.getPromotionListByCriterous(rs.getString("promotionID"));
                    promotion = promotionList.get(0);
                }

                ArrayList<Vendor> vendorList = vendor_dao.getVendorListByCriterias(rs.getString("vendorID"));
                Vendor vendor = vendorList.get(0);

                ArrayList<Category> categoryList = category_dao.getCategoryByCriterous(rs.getString("categoryID"));
                Category category = categoryList.get(0);


                String activeIngredient = rs.getString("activeIngredient");
                String conversionUnit = rs.getString("conversionUnit");
                AdministrationRoute administrationRoute;

                if (rs.getString("administrationID") == null) {
                    administrationRoute = new AdministrationRoute();
                } else {
                    ArrayList<AdministrationRoute> administrationRoutesList = administrationRoute_dao.getAdminListByCriterous(rs.getString("administrationID"));
                    administrationRoute = administrationRoutesList.get(0);
                }

                String medicalSuppliesName = rs.getString("medicalSupplyName");
                String mainNutrients = rs.getString("mainNutrients");

                String supplementaryIngredients = rs.getString("supplementaryIngredients");

                String sql = "SELECT * " +
                        "FROM ProductUnit " +
                        "WHERE productID = ?";
                switch (category.getCategoryID()) {
                    case "CA001":
                    case "CA002":
                    case "CA003":
                    case "CA004":
                    case "CA005":
                    case "CA006":
                    case "CA007":
                    case "CA008":
                    case "CA009":
                    case "CA010":
                    case "CA011":
                    case "CA012":
                    case "CA013":
                    case "CA014":
                    case "CA015":
                    case "CA016":
                    case "CA017":
                    case "CA018":
                        Medicine medicine = new Medicine(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, activeIngredient, conversionUnit, administrationRoute);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, medicine.getProductID());
                        ResultSet rsUnit = stmt.executeQuery();
                        while (rsUnit.next()) {

                            String unit = rsUnit.getString("unitID");
                            int inStock = rsUnit.getInt("inStock");
                            double sellPrice = rsUnit.getDouble("sellPrice");
                            medicine.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(medicine);
                        break;
                    case "CA019":
                        MedicalSupplies medical = new MedicalSupplies(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, medicalSuppliesName);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, medical.getProductID());
                        ResultSet rsUnitMedical = stmt.executeQuery();
                        while (rsUnitMedical.next()) {
                            String unit = rsUnitMedical.getString("unitID");
                            int inStock = rsUnitMedical.getInt("inStock");
                            double sellPrice = rsUnitMedical.getDouble("sellPrice");
                            medical.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(medical);
                        break;
                    case "CA020":
                        FunctionalFood tempFunctionFood = new FunctionalFood(proID, proName, resNumber, purchasePrice, tax, vendor, category, promotion, endDate, mainNutrients, supplementaryIngredients);
                        stmt = con.prepareStatement(sql);
                        stmt.setString(1, tempFunctionFood.getProductID());
                        ResultSet rsUnitFuntionFood = stmt.executeQuery();
                        while (rsUnitFuntionFood.next()) {
                            String unit = rsUnitFuntionFood.getString("unitID");
                            int inStock = rsUnitFuntionFood.getInt("inStock");
                            double sellPrice = rsUnitFuntionFood.getDouble("sellPrice");
                            tempFunctionFood.addUnit(PackagingUnit.fromString(Unit_DAO.getInstance().getUnit_ByID(unit).getUnitName()), sellPrice, inStock);
                        }
                        productList.add(tempFunctionFood);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + category.getCategoryID());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * Xử lý cắt tên từ 0 đến ( cho unitNote
     *
     * @param input
     * @return
     */
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
        ConnectDB.getInstance().connect();
        Connection con = ConnectDB.getConnection();
        Product_DAO product_dao = new Product_DAO();
        PackagingUnit unitEnum = PackagingUnit.fromString("PILL");
        PackagingUnit unitEnum2 = PackagingUnit.fromString("BLISTER_PACK");
        PackagingUnit unitEnum3 = PackagingUnit.fromString("BOX");

        //product_dao.updateProductInStock_WithTransaction("PM121224000003", 10, unitEnum2, false, con);
    }
}

