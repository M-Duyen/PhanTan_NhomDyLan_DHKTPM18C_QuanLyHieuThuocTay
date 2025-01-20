package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
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

    public void setEmployeeID(String employeeID) {
        if(employeeID == null || employeeID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã nhân viên không được rỗng");
        }
        this.employeeID = employeeID;
    }

    public void setEmployeeName(String employeeName) {
        if(employeeName == null || employeeName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên nhân viên không được rỗng");
        }
        this.employeeName = employeeName;
    }

    public void setPhoneNumber(String phoneNumber) {
        if(phoneNumber == null || phoneNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Số điện thoại không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }
}
