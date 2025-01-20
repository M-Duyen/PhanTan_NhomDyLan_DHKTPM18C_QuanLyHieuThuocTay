package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Prescription {
    private String prescriptionID;
    private LocalDate createdDate;
    private String diagnosis;
    private String medicalFacility;

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

    public String getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(String prescriptionID) {
        if(prescriptionID == null || prescriptionID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã đơn thuốc không được rỗng");
        }
        this.prescriptionID = prescriptionID;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        if(createdDate.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("Ngày tạo đơn thuốc phải nhỏ hơn hoặc bằng ngày hiện tại");
        }
        this.createdDate = createdDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        if(diagnosis == null || diagnosis.trim().isEmpty()){
            throw new IllegalArgumentException("Chẩn đoán không được rỗng");
        }
        this.diagnosis = diagnosis;
    }

    public String getMedicalFacility() {
        return medicalFacility;
    }

    public void setMedicalFacility(String medicalFacility) {
        if(medicalFacility == null || medicalFacility.trim().isEmpty()){
            throw new IllegalArgumentException("Cơ sở y tế không được rỗng");
        }
        this.medicalFacility = medicalFacility;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Prescription other = (Prescription) obj;
        return Objects.equals(this.prescriptionID, other.prescriptionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prescriptionID);
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionID='" + prescriptionID + '\'' +
                ", createdDate=" + createdDate +
                ", diagnosis='" + diagnosis + '\'' +
                ", medicalFacility='" + medicalFacility + '\'' +
                '}';
    }
}
