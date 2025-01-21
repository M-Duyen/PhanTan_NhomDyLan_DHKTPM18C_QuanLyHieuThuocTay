package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.common.aliasing.qual.Unique;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
public class Customer {
    @Unique
    @EqualsAndHashCode.Include
    @Column(name = "customer_id", nullable = false, columnDefinition = "char(10)")
    private String customerID;

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "phone_number", nullable = false, columnDefinition = "char(10)")
    private String phoneNumber;

    @Column(name = "customer_name")
    private String customerName;
    private boolean gender;

    @Column(name = "email", unique = true)
    private String email ;

    private String addr;

    @Column(name = "birth_date")
    private LocalDate brithDate;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

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

//    public double getPoint() {
//        return new Customer_DAO().getCustomerPoint(getPhoneNumber());
//    }

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