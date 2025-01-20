package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prescriptions")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Prescription {
    public Prescription(String prescriptionID, LocalDateTime createdDate, String diagnosis, String medicalFacility) {
        this.prescriptionID = prescriptionID;
        this.createdDate = createdDate;
        this.diagnosis = diagnosis;
        this.medicalFacility = medicalFacility;
    }

    public Prescription() {
    }

    @Id
    @Column(name = "prescription_id", nullable = false)
    @EqualsAndHashCode.Include
    private String prescriptionID;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    private String diagnosis;
    @Column(name = "medical_facility")
    private String medicalFacility;


}
