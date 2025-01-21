package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "medical_supplies")
public class MedicalSupplies extends Product{

    @Column(name = "medical_supply_type", columnDefinition = "nvarchar(20)")
    private String medicalSupplyType;
}
