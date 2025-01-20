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
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "product_id", nullable = false)
    private String productID;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "registration_number")
    private String registrationNumber;

    @Column(name = "purchase_price")
    private double purchasePrice;

    @Column(name = "tax_percentage")
    private double taxPercentage;

    @Column(name = "start_date")
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