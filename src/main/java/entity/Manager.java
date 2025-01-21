package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "managers")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Manager {
    @Id
    @Column (name = "manager_id", nullable = false, columnDefinition = "char(5)")
    @EqualsAndHashCode.Include
    private String managerID;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "manager")
    private List<Account> account;

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

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }
}
