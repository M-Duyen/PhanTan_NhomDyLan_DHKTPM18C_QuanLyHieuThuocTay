package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "orders")
public class Order {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "order_id", nullable = false)
    private String orderID;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "ship_to_address")
    private String shipToAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "discount")
    private double discount;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToMany
    @JoinColumn(name = "order")
    private ArrayList<OrderDetail> listOrderDetail;

    @Transient
    public double getTotalDue() {
        double totalDue = 0;
        if (listOrderDetail != null) {
            for (OrderDetail orderDetail : listOrderDetail) {
                totalDue += orderDetail.getLineTotal();
            }
        }
        return totalDue * (1 - discount);
    }
}
