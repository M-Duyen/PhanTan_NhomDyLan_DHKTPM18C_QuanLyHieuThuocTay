package utils;

import dao.ProductDAO;
import model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoadProductFromExcel {
    /**
     * Load data product from excel file
     * @param path
     * @return
     */
        public ArrayList<Product> loadDataProduct(String path) {
        ArrayList<Product> listProduct = new ArrayList();
        int xM = 0, xFF = 0, xMS = 0;
        try (FileInputStream fis = new FileInputStream(new File(path));
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String productName = row.getCell(1).getStringCellValue();
                String registrationNumber = row.getCell(2).getStringCellValue();
                int quantityInStock = (int) row.getCell(3).getNumericCellValue();
                double purchasePrice = (double) row.getCell(4).getNumericCellValue();
                double taxPercentage = (double) row.getCell(5).getNumericCellValue();


                LocalDate endDate = row.getCell(6).getDateCellValue()
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();

                String vendorID = row.getCell(7).getStringCellValue();
                String vendorName = row.getCell(8).getStringCellValue();
                String vendorCountry = row.getCell(9).getStringCellValue();
                String categoryID = row.getCell(10).getStringCellValue();
                String categoryName = row.getCell(12).getStringCellValue();
                String conversionUnit = row.getCell(14).getStringCellValue();
                String noteUnit = row.getCell(20).getStringCellValue();
                switch (row.getCell(11).getStringCellValue()) {
                    case "M": {
                        String activeIngredient = row.getCell(13).getStringCellValue();
                        String administrationID = row.getCell(15).getStringCellValue();
                        String administrationName = row.getCell(16).getStringCellValue();

                        Map<PackagingUnit, ProductUnit> unitMap = new HashMap<>();
                        Medicine medicine = new Medicine(new ProductDAO(Product.class).getIDProduct("PM", xM), productName, registrationNumber, purchasePrice, taxPercentage, new Vendor(vendorID, vendorName, vendorCountry), new Category(categoryID, categoryName, null), endDate, activeIngredient, conversionUnit, new AdministrationRoute(administrationID, administrationName), noteUnit);
                        medicine.addUnit(PackagingUnit.fromString(conversionUnit), new ProductUnit(calSellPrice(purchasePrice , medicine), quantityInStock));
                        //Các đơn vị còn lại
                        addUnitByString(row.getCell(20).getStringCellValue(), purchasePrice, medicine);
                        xM++;
                        listProduct.add(medicine);
                        break;
                    }
                    case "FF": {
                        String mainNutrients = row.getCell(17).getStringCellValue();
                        String supplementaryIngredients = row.getCell(18).getStringCellValue();
                        FunctionalFood ff = new FunctionalFood(new ProductDAO(Product.class).getIDProduct("PF", xFF), productName, registrationNumber, purchasePrice, taxPercentage, new Vendor(vendorID, vendorName, vendorCountry), new Category(categoryID, categoryName, null), endDate, mainNutrients, supplementaryIngredients, noteUnit);
                        xFF++;

                        ff.addUnit(PackagingUnit.fromString(conversionUnit), new ProductUnit(calSellPrice(purchasePrice , ff), quantityInStock));
                        //Các đơn vị còn lại
                        addUnitByString(row.getCell(20).getStringCellValue(), purchasePrice, ff);
                        listProduct.add(ff);
                        break;
                    }
                    case "MS": {
                        String medicalSupplyType = row.getCell(19).getStringCellValue();
                        MedicalSupply ms = new MedicalSupply(new ProductDAO(Product.class).getIDProduct("PS", xMS), productName, registrationNumber, purchasePrice, taxPercentage, new Vendor(vendorID, vendorName, vendorCountry), new Category(categoryID, categoryName, null), endDate, medicalSupplyType, noteUnit);
                        xMS++;
                        ms.addUnit(PackagingUnit.fromString(conversionUnit), new ProductUnit(calSellPrice(purchasePrice , ms), quantityInStock));
                        //Các đơn vị còn lại
                        addUnitByString(row.getCell(20).getStringCellValue(), purchasePrice,  ms);
                        listProduct.add(ms);
                        break;
                    }
                    default: {
                        //Xử lý ngoại lệ
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listProduct;
    }

    /**
     * Tinh toán giá bán
     */
    public static double calSellPrice(double purchasePrice, Product p){
        double sellPrice = 0.0;
        if (purchasePrice >= 5000 && purchasePrice < 100000) {
            sellPrice = purchasePrice + 0.1 * purchasePrice + p.getTaxPercentage() * purchasePrice;
        } else if (purchasePrice >= 100000 && purchasePrice < 1000000) {
            sellPrice = purchasePrice + 0.07 * purchasePrice + p.getTaxPercentage() * purchasePrice;
        } else {
            sellPrice = purchasePrice + 0.05 * purchasePrice + p.getTaxPercentage() * purchasePrice;
        }
        return sellPrice;
    }

    /**
     * Thêm các đơn vị còn lại
     */

    public static void addUnitByString(String unitNote, double purchasePrice, Product product){
        String[] parts = unitNote.split(",\\s*");
        int unitTL = 1;
        boolean isFirstUnit = true;
        double priceTL = 1;
        for (String part : parts) {
            Pattern pattern = Pattern.compile("([A-Z_ ]+)(?:\\((\\d+)\\))?");
            Matcher matcher = pattern.matcher(part);

            if (matcher.matches()) {
                String enumName = matcher.group(1).trim();
                int multiplier = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : 1;
                if (isFirstUnit) {
                    isFirstUnit = false;
                    unitTL *= multiplier;
                    continue;
                }
                priceTL *= multiplier;
                PackagingUnit unit = PackagingUnit.fromString(enumName);
                double unitPrice = purchasePrice / priceTL;
                int quantity =unitTL * multiplier;
                product.addUnit(unit, new ProductUnit(calSellPrice(unitPrice, product), quantity));
                unitTL *= multiplier;
            }
        }

    }

    //TODO: Load product temp
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO(Product.class);
        LoadProductFromExcel loadProductFromExcel = new LoadProductFromExcel();
        ArrayList<Product> listProduct = loadProductFromExcel.loadDataProduct("data/ListProductTest.xlsx");
        productDAO.createMultiple(listProduct);

    }
}
