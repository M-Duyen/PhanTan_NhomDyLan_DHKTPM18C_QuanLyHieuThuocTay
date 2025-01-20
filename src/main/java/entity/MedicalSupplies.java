package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "meidcal_supplies")
public class MedicalSupplies extends Product{

    @Column(name = "medical_supply_type", columnDefinition = "nvarchar(20)")
    private String medicalSupplyType;

    public MedicalSupplies() {
    }
    
    public MedicalSupplies(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
			LocalDate endDate, String medicalSupplyType) {
        super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category);
		setMedicalSupplyType(medicalSupplyType);
	}

    public MedicalSupplies(String productID, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
                           LocalDate endDate, String medicalSupplyType, String noteUnit) {
        super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category, noteUnit);
        setMedicalSupplyType(medicalSupplyType);
    }

	public MedicalSupplies(String medicalSupplyType) {
        setMedicalSupplyType(medicalSupplyType);
    }

    public void setMedicalSupplyType(String medicalSupplyType) {
        if(medicalSupplyType == null || medicalSupplyType.trim().isEmpty()){
            throw new IllegalArgumentException("Loại vật tư y tế không được rỗng");
        }
        this.medicalSupplyType = medicalSupplyType;
    }
}
