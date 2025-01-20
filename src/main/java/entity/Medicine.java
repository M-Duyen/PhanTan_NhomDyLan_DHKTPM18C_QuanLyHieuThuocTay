package entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Entity
@EqualsAndHashCode(callSuper = true)
public class Medicine extends Product {

    @Column(columnDefinition = "nvarchar(20)")
    private String activeIngredient;
    @Column(columnDefinition = "nvarchar(20)")
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

    public void setAdministrationRoute(AdministrationRoute administrationRoute) {
        this.administrationRoute = administrationRoute;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "activeIngredient='" + activeIngredient + '\'' +
                ", conversionUnit='" + conversionUnit + '\'' +
                ", administrationRoute=" + administrationRoute +
                "} " + super.toString();
    }
}
