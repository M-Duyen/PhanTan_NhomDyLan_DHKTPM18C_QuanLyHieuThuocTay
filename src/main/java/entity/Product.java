package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "products")
public class Product {
    @Id
    @Column(unique = true, nullable = false, columnDefinition = "char(14)")
    private String productID;

    @Column(columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(columnDefinition = "varchar(16)")
    private String registrationNumber;

    @Column(columnDefinition = "money")
    private double purchasePrice;

    @Column(columnDefinition = "float")
    private double taxPercentage;

    @Column(columnDefinition = "date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "promotionID")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "vendorID")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "categoryID")
    private Category category;

    @Column(columnDefinition = "varchar(60)")
    private String unitNote;

    //TODO: để tạm hai cái này đây
    @ElementCollection
    private HashMap<Enum_PackagingUnit, Double> unitPrice;
    @ElementCollection
    private HashMap<Enum_PackagingUnit, Integer> unitStock;

    @ElementCollection
    @CollectionTable(name = "ProductUnit", joinColumns = @JoinColumn(name = "productID"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "unitName")
    private Map<Enum_PackagingUnit, ProductUnit> unitDetails = new HashMap<>();

    public Product() {
    }

    public Product(String productID) {
        setProductID(productID);
    }

    public Product(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, LocalDate endDate, Promotion promotion, Vendor vendor, Category category) {
        this.productID = productID;
        this.productName = productName;
        this.registrationNumber = registrationNumber;
        this.purchasePrice = purchasePrice;
        this.taxPercentage = taxPercentage;
        this.endDate = endDate;
        this.promotion = promotion;
        this.vendor = vendor;
        this.category = category;
        this.unitPrice = new HashMap<>();
        this.unitStock = new HashMap<>();
    }

    public Product(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, LocalDate endDate, Promotion promotion, Vendor vendor, Category category, String unitNote) {
        this.productID = productID;
        this.productName = productName;
        this.registrationNumber = registrationNumber;
        this.purchasePrice = purchasePrice;
        this.taxPercentage = taxPercentage;
        this.endDate = endDate;
        this.promotion = promotion;
        this.vendor = vendor;
        this.category = category;
        this.unitNote = unitNote;
        this.unitPrice = new HashMap<>();
        this.unitStock = new HashMap<>();
    }

    public double getSellPrice(Enum_PackagingUnit unit) {
        return unitPrice.get(unit);
    }

    public Map<Enum_PackagingUnit, Integer> getInStock_BySmallestUnit() {
        Enum_PackagingUnit unitTemp = null;
        int inStockTemp = 0;

        for (Map.Entry<Enum_PackagingUnit, Integer> entry : unitStock.entrySet()) {
            if (entry.getValue() > inStockTemp || (entry.getValue() == inStockTemp &&
                    (unitTemp == null || entry.getKey().ordinal() < unitTemp.ordinal()))) {
                inStockTemp = entry.getValue();
                unitTemp = entry.getKey();
            }
        }

        Map<Enum_PackagingUnit, Integer> result = new HashMap<>();
        if (unitTemp != null) {
            result.put(unitTemp, inStockTemp);
        }

        return result;
    }

    public double setSellPrice(Enum_PackagingUnit unit){
        double price = unitPrice.get(unit);
        double sellPrice = 0;

        if (price >= 5000 && price < 100000) {
            sellPrice = price + 0.1 * price + taxPercentage * price;
        } else if (price >= 100000 && price < 1000000) {
            sellPrice = price + 0.07 * price + taxPercentage * price;
        } else {
            sellPrice = price + 0.05 * price + taxPercentage * price;
        }
        return sellPrice; //Math.floor(sellPrice / 1000) * 1000; //Làm tròn xuống
        //Math.ceil(sellPrice); //Làm tròn lên
    }

    public HashMap<Enum_PackagingUnit, Double> getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(HashMap<Enum_PackagingUnit, Double> unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void addUnit(Enum_PackagingUnit unit, double price, int inStock){
        unitPrice.put(unit, price);
        unitStock.put(unit, inStock);
    }
    // Getter cho danh sách đơn vị đóng gói
    public List<Enum_PackagingUnit> getPackagingUnitsList() {
        return new ArrayList<>(unitPrice.keySet());
    }

    public double getPriceByUnit(Enum_PackagingUnit unit){
        return unitPrice.getOrDefault(unit, 0.0);
    }

    public int getInStockByUnit(Enum_PackagingUnit unit){
        return unitStock.getOrDefault(unit, 0);
    }
    //get giá bán
//    public double getActualSellPrice() {
//        if (promotion != null && promotion.getEndDate().isBefore(LocalDate.now())) {
//                    	return getSellPrice() * promotion.getDiscount();
//        }
//        return getSellPrice();
//    }

    public boolean isMedicine() {
        return getProductID().substring(0, 2).equals("PM");
    }

    public boolean isMedicalSupplies() {
        return getProductID().substring(0, 2).equals("PS");
    }

    public boolean isFunctionalFood() {
        return getProductID().substring(0, 2).equals("PF");
    }

    public String getProductID() {
        return productID;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate dateEnd) {
        this.endDate = dateEnd;
    }

    public String getUnitNote() {
        return unitNote;
    }

    public void setUnitNote(String unitNote) {
        this.unitNote = unitNote;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void setProductID(String productID) {
        if (productID == null || productID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sản phẩm không được rỗng");
        }
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sản phẩm không được rỗng");
        }
        this.productName = productName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã đăng ký không được rỗng");
        }
        this.registrationNumber = registrationNumber;
    }

//    public int getQuantityInStock() {
//        return quantityInStock;
//    }
//
//    public void setQuantityInStock(int quantityInStock) {
//        if (quantityInStock < 0) {
//            throw new IllegalArgumentException("Số lượng tồn kho phải lớn hơn 0");
//        }
//        this.quantityInStock = quantityInStock;
//    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Giá nhập của sản phẩm phải lớn hơn 0");
        }
        this.purchasePrice = purchasePrice;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getTaxPercentage() {
        return taxPercentage;
    }

    public void setTaxPercentage(double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        return Objects.equals(this.productID, other.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productID);
    }

    @Override
    public String toString() {
        String unitPriceString = " ";
        for (Map.Entry<Enum_PackagingUnit, Double> entry : unitPrice.entrySet()) {
//                String packagingUnitStr = entry.getKey().name();
            unitPriceString += productID + "//" + entry.getKey().name() + "//" + entry.getValue();
        }
        return "Product{" +
                "productID='" + productID + '\'' +
                ", productName='" + productName + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", purchasePrice=" + purchasePrice +
                ", taxPercentage=" + taxPercentage +
                ", endDate=" + endDate +
                ", promotion=" + promotion +
                ", vendor=" + vendor +
                ", category=" + category +
                ", unitPrice=" + unitPriceString +
                ", unitStock=" + unitStock +
                '}';
    }
}
