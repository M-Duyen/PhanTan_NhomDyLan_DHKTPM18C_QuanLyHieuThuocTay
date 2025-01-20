package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import dao.Customer_DAO;

import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
public class Customer {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "customer_id")
    private String customerID;
    private String phoneNumber;
    private String customerName;
    private boolean gender;
    private String email ;
    private String addr;
    private LocalDate brithDate;

    public Customer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }

    public Customer(String customerID, String phoneNumber, String customerName, String email, boolean gender, String addr, LocalDate brithDate) {
        this.customerID = customerID;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.email = email;
        this.gender = gender;
        this.addr = addr;
        this.brithDate = brithDate;
    }

    public double getPoint() {
        return new Customer_DAO().getCustomerPoint(getPhoneNumber());
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được rỗng");
        }
        this.customerName = customerName;
    }

    public void setBrithDate(LocalDate brithDate) {
        if (brithDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày sinh phải nhỏ hơn ngày hiện tại");
        }
        this.brithDate = brithDate;
    }
}
