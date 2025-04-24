package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.checkerframework.common.aliasing.qual.Unique;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
public class Customer implements Serializable {
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

    private double point;

    @Column(name = "email", unique = true)
    private String email ;
    private String addr;
    @Column(name = "birth_date")
    private LocalDate brithDate;

    @OneToMany(mappedBy = "customer")
    private List<Order> order;

//    @Override
//    public String toString() {
//        return "Customer{" +
//                "brithDate=" + brithDate +
//                ", addr='" + addr + '\'' +
//                ", email='" + email + '\'' +
//                ", gender=" + gender +
//                ", customerName='" + customerName + '\'' +
//                ", phoneNumber='" + phoneNumber + '\'' +
//                ", customerID='" + customerID + '\'' +
//                '}';
//    }
}