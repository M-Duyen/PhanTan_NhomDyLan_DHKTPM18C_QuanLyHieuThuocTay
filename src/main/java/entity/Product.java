package entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "products")
public class Product {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id", nullable = false, columnDefinition = "char(14)")
    private String productID;

    @Column(name = "product_name", columnDefinition = "nvarchar(50)")
    private String productName;

    @Column(name = "registration_number", columnDefinition = "varchar(16)")
    private String registrationNumber;

    @Column(name = "purchase_price", columnDefinition = "money")
    private double purchasePrice;

    @Column(name = "tax_percentage", columnDefinition = "float")
    private double taxPercentage;

    @Column(name = "end_date", columnDefinition = "date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "varchar(60)")
    private String unitNote;


    @ElementCollection
    @CollectionTable(name = "product_units", joinColumns = @JoinColumn(name = "productID"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "unit_name")
    private Map<PackagingUnit, ProductUnit> unitDetails = new HashMap<>();


    public double getSellPrice(PackagingUnit unit) {
        return unitDetails.get(unit).getSellPrice();
    }

    public boolean isMedicine() {
        return productID.substring(0, 2).equals("PM");
    }

    public boolean isMedicalSupplies() {
        return productID.substring(0, 2).equals("PS");
    }

    public boolean isFunctionalFood() {
        return productID.substring(0, 2).equals("PF");
    }

}


//
//    public Map<Enum_PackagingUnit, Integer> getInStock_BySmallestUnit() {
//        Enum_PackagingUnit unitTemp = null;
//        int inStockTemp = 0;
//
//        for (Map.Entry<Enum_PackagingUnit, Integer> entry : unitStock.entrySet()) {
//            if (entry.getValue() > inStockTemp || (entry.getValue() == inStockTemp &&
//                    (unitTemp == null || entry.getKey().ordinal() < unitTemp.ordinal()))) {
//                inStockTemp = entry.getValue();
//                unitTemp = entry.getKey();
//            }
//        }
//
//        Map<Enum_PackagingUnit, Integer> result = new HashMap<>();
//        if (unitTemp != null) {
//            result.put(unitTemp, inStockTemp);
//        }
//
//        return result;
//    }
//
//    public double setSellPrice(Enum_PackagingUnit unit){
//        double price = unitPrice.get(unit);
//        double sellPrice = 0;
//
//        if (price >= 5000 && price < 100000) {
//            sellPrice = price + 0.1 * price + taxPercentage * price;
//        } else if (price >= 100000 && price < 1000000) {
//            sellPrice = price + 0.07 * price + taxPercentage * price;
//        } else {
//            sellPrice = price + 0.05 * price + taxPercentage * price;
//        }
//        return sellPrice; //Math.floor(sellPrice / 1000) * 1000; //Làm tròn xuống
//        //Math.ceil(sellPrice); //Làm tròn lên
//    }
//
//
//    public void addUnit(Enum_PackagingUnit unit, double price, int inStock){
//        unitPrice.put(unit, price);
//        unitStock.put(unit, inStock);
//    }
//    public List<Enum_PackagingUnit> getPackagingUnitsList() {
//        return new ArrayList<>(unitPrice.keySet());
//    }
//
//    public double getPriceByUnit(Enum_PackagingUnit unit){
//        return unitPrice.getOrDefault(unit, 0.0);
//    }
//
//    public int getInStockByUnit(Enum_PackagingUnit unit){
//        return unitStock.getOrDefault(unit, 0);
//    }