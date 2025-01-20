package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "prescription")
public class Prescription {
    @Id
    @Column(name = "prescription_id")
    private String prescriptionID;
    @Column(name = "create_date")
    private LocalDate createdDate;
    private String diagnosis;
    @Column(name = "medical_facility")
    private String medicalFacility;
}
