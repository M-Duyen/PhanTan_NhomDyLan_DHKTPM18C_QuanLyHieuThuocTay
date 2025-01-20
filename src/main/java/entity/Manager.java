package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "managers")
public class Manager {
    @Id
    @Column(name = "manager_id", nullable = false, unique = true)
    private String managerID;

    private String managerName;

    private LocalDate birthDate;

    private String phoneNumber;

    public Manager() {
    }

    public Manager(String managerID) {
        setManagerID(managerID);
    }

    public Manager(String managerID, String managerName, LocalDate birthDate, String phoneNumber) {
        setManagerID(managerID);
        setManagerName(managerName);
        setBirthDate(birthDate);
        setPhoneNumber(phoneNumber);
    }

    public void setManagerID(String managerID) {
        if (managerID == null || managerID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã quản lý không được rỗng");
        }
        this.managerID = managerID;
    }

    public void setManagerName(String managerName) {
        if (managerName == null || managerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên quản lý không được rỗng");
        }
        this.managerName = managerName;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày sinh phải nhỏ hơn ngày hiện tại");
        }
        this.birthDate = birthDate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }
}
