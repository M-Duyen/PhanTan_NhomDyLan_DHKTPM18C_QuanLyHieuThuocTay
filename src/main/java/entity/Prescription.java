package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "prescriptions")
public class Prescription {
    @Id
    @Column(name = "prescription_id")
    @EqualsAndHashCode.Include
    private String prescriptionID;

    @Column(name = "create_date")
    private LocalDate createdDate;

    private String diagnosis;

    @Column(name = "medical_facility")
    private String medicalFacility;

    @OneToMany(mappedBy = "prescription")
    private List<Order> orders;

    public Prescription() {
    }

    public Prescription(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public Prescription(String prescriptionID, LocalDate createdDate, String diagnosis, String medicalFacility) {
        this.prescriptionID = prescriptionID;
        this.createdDate = createdDate;
        this.diagnosis = diagnosis;
        this.medicalFacility = medicalFacility;
    }

    public void setPrescriptionID(String prescriptionID) {
        if(prescriptionID == null || prescriptionID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã đơn thuốc không được rỗng");
        }
        this.prescriptionID = prescriptionID;
    }

    public void setCreatedDate(LocalDate createdDate) {
        if(createdDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ngày tạo đơn thuốc phải nhỏ hơn hoặc bằng ngày hiện tại");
        }
        this.createdDate = createdDate;
    }

    public void setDiagnosis(String diagnosis) {
        if(diagnosis == null || diagnosis.trim().isEmpty()){
            throw new IllegalArgumentException("Chẩn đoán không được rỗng");
        }
        this.diagnosis = diagnosis;
    }

    public void setMedicalFacility(String medicalFacility) {
        if(medicalFacility == null || medicalFacility.trim().isEmpty()){
            throw new IllegalArgumentException("Cơ sở y tế không được rỗng");
        }
        this.medicalFacility = medicalFacility;
    }
}
