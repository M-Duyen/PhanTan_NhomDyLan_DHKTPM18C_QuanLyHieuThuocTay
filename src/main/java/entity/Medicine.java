package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicines")
public class Medicine extends Product {

    @Column(name = "active_ingredient", columnDefinition = "nvarchar(20)")
    private String activeIngredient;

    @Column(name = "conversion_unit", columnDefinition = "nvarchar(20)")
    private String conversionUnit;

    @ManyToOne
    @JoinColumn(name = "administrationID")
    private AdministrationRoute administrationRoute;

    public Medicine() {
    }
    
    public Medicine(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
			LocalDate endDate, String activeIngredient, String conversionUnit, AdministrationRoute administrationRoute) {
        super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category);
		setActiveIngredient(activeIngredient);
        setConversionUnit(conversionUnit);
        this.administrationRoute = administrationRoute;
	}
    public Medicine(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
                    LocalDate endDate, String activeIngredient, String conversionUnit, AdministrationRoute administrationRoute, String noteUnit) {
        super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category, noteUnit);
        setActiveIngredient(activeIngredient);
        setConversionUnit(conversionUnit);
        this.administrationRoute = administrationRoute;
    }

	public Medicine(String activeIngredient, String conversionUnit, AdministrationRoute administrationRoute) {
        setActiveIngredient(activeIngredient);
        setConversionUnit(conversionUnit);
        this.administrationRoute = administrationRoute;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        if (activeIngredient == null || activeIngredient.trim().isEmpty()) {
            throw new IllegalArgumentException("Hoạt chất không được rỗng");
        }
        this.activeIngredient = activeIngredient;
    }

    public String getConversionUnit() {
        return conversionUnit;
    }

    public void setConversionUnit(String conversionUnit) {
        if (conversionUnit == null || conversionUnit.trim().isEmpty()) {
            throw new IllegalArgumentException("Đơn vị quy đổi không được rỗng");
        }
        this.conversionUnit = conversionUnit;
    }

    public AdministrationRoute getAdministrationRoute() {
        return administrationRoute;
    }
}
