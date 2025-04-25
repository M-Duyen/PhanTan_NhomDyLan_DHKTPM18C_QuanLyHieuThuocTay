package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "medical_supplies")
public class MedicalSupply extends Product implements Serializable {

    @Column(name = "medicalSupply_type",columnDefinition = "nvarchar(20)")
    private String medicalSupplyType;

    public MedicalSupply(String id, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, LocalDate endDate, String medicalSupplyType, String noteUnit) {
        super(id, productName, registrationNumber, purchasePrice, taxPercentage, endDate, vendor, category, noteUnit);
        this.medicalSupplyType = medicalSupplyType;
    }

}
