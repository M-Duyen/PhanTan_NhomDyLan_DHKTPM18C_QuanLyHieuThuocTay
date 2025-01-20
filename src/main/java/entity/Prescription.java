package entity;

import java.time.LocalDate;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "prescriptions")
public class Prescription {
    @Id
    @Column(name = "prescription_id")
    @EqualsAndHashCode.Include
    private String prescriptionId;
    private String prescriptionID;
    private LocalDate createdDate;
    private String diagnosis;
    private String medicalFacility;
}
