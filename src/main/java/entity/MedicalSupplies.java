package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medical_supplies")
public class MedicalSupplies extends Product {

    @Column(name = "medicalSupply_type",columnDefinition = "nvarchar(20)")
    private String medicalSupplyType;

}
