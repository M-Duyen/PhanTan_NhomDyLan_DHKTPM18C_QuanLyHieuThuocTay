package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "order_id", nullable = false)
    private String orderID;

    @Column(name = "order_date", nullable = false)
    @ToString.Include
    private LocalDateTime orderDate;

    @Column(name = "ship_to_address")
    @ToString.Include
    private String shipToAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    @ToString.Include
    private PaymentMethod paymentMethod;

    @Column(name = "discount")
    @ToString.Include
    private Double discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    @ToString.Include
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    @ToString.Include
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    @ToString.Include
    private Prescription prescription;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<OrderDetail> listOrderDetail;

    @Transient
    @ToString.Include
    public double getTotalDue() {
        double totalDue = 0;
        if (listOrderDetail != null) {
            for (OrderDetail orderDetail : listOrderDetail) {
                totalDue += orderDetail.getLineTotal();
            }
        }
        return totalDue - discount;
    }

    public Order(String orderID, LocalDateTime orderDate, String shipToAddress, PaymentMethod paymentMethod, Double discount, Employee employee, Customer customer, Prescription prescription) {
        this.orderID = orderID;
        this.orderDate = orderDate;
        this.shipToAddress = shipToAddress;
        this.paymentMethod = paymentMethod;
        this.discount = discount;
        this.employee = employee;
        this.customer = customer;
        this.prescription = prescription;
    }

    public Order() {

    }
}
