package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashMap;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "products")
public class Product {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id", nullable = false)
    private String productID;

    private String productName;

    private String registrationNumber;

    private double purchasePrice;

    private double taxPercentage;

    private LocalDate endDate;

    private Promotion promotion;

    private Vendor vendor;

    private Category category;

    private String unitNote;

    @ElementCollection
    @CollectionTable(name = "product_unit_price", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "unit")
    @Column(name = "price")
    private HashMap<PackagingUnit, Double> unitPrice;

    @ElementCollection
    @CollectionTable(name = "product_unit_stock", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "unit")
    @Column(name = "stock")
    private HashMap<PackagingUnit, Integer> unitStock;

    @Transient
    public Double getUnitPrice(PackagingUnit unit) {
        return unitPrice.getOrDefault(unit, 0.0);
    }

    @Transient
    public Integer getUnitStock(PackagingUnit unit) {
        return unitStock.getOrDefault(unit, 0);
    }

}