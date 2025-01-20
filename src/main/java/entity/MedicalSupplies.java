package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicalSupplies extends Product{
    @Column(columnDefinition = "nvarchar(20)")
    private String medicalSupplyType;

}
