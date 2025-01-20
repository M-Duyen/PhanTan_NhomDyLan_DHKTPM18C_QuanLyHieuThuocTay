package entity;

import java.time.LocalDate;
import java.util.Objects;

public class Manager {
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

    public String getManagerID() {
        return managerID;
    }

    public void setManagerID(String managerID) {
        if (managerID == null || managerID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã quản lý không được rỗng");
        }
        this.managerID = managerID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        if (managerName == null || managerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên quản lý không được rỗng");
        }
        this.managerName = managerName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
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
        final Manager other = (Manager) obj;
        return Objects.equals(this.managerID, other.managerID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(managerID);
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerID='" + managerID + '\'' +
                ", managerName='" + managerName + '\'' +
                ", birthDate=" + birthDate +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
