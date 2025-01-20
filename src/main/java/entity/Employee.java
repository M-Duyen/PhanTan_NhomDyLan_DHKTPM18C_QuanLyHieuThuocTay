package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Objects;

//@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "employees")
public class Employee {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "employee_id")
    private String employeeID;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private boolean gender;

    private String email;

    private String address;

    private boolean status;

    private String degree;

    public Employee() {
    }

    public Employee(String employeeID) {
        this.employeeID = employeeID;
    }

    public Employee(String employeeID, String employeeName,  String phoneNumber, LocalDate birthDate, boolean gender, String email, String address, boolean status, String degree) {
        setEmployeeID(employeeID);
        setEmployeeName(employeeName);
        setPhoneNumber(phoneNumber);
        setBirthDate(birthDate);
        setDegree(degree);
        setEmail(email);
        setAddress(address);
        this.gender = gender;
        this.status = status;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        if(employeeID == null || employeeID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã nhân viên không được rỗng");
        }
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        if(employeeName == null || employeeName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên nhân viên không được rỗng");
        }
        this.employeeName = employeeName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Số điện thoại không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
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
        final Employee other = (Employee) obj;
        return Objects.equals(this.employeeID, other.employeeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeID);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeID='" + employeeID + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", status=" + status +
                ", degree='" + degree + '\'' +
                '}';
    }
}
