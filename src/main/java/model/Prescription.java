package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "prescriptions")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Prescription implements Serializable {
    @Id
    @Column(name = "prescription_id", nullable = false)
    @EqualsAndHashCode.Include
    private String prescriptionID;
    @Column(name = "create_date")
    private LocalDateTime createdDate;
    private String diagnosis;
    @Column(name = "medical_facility")
    private String medicalFacility;

    @OneToMany(mappedBy = "prescription")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Prescription{" +
                "medicalFacility='" + medicalFacility + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", createdDate=" + createdDate +
                ", prescriptionID='" + prescriptionID + '\'' +
                '}';
    }
}
