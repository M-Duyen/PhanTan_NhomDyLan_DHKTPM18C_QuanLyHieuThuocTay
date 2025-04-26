package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicines")
@Data
@NoArgsConstructor
@ToString(callSuper = true)
public class Medicine extends Product implements Serializable {

    @Column(columnDefinition = "nvarchar(20)")
    private String activeIngredient;
    @Column(columnDefinition = "nvarchar(20)")
    private String conversionUnit;
    @ManyToOne
    @JoinColumn(name = "administrationID")
    private AdministrationRoute administrationRoute;

    public Medicine(String id, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, LocalDate endDate, String activeIngredient, String conversionUnit, AdministrationRoute administrationRoute, String noteUnit) {
        super(id, productName, registrationNumber, purchasePrice, taxPercentage, endDate, vendor, category, noteUnit);
        this.activeIngredient = activeIngredient;
        this.conversionUnit = conversionUnit;
        this.administrationRoute = administrationRoute;
    }
}
