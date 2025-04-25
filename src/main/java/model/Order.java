package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "order_id", nullable = false)
    @ToString.Include
    private String orderID;

    @Column(name = "order_date", nullable = false)
    @ToString.Include
    private LocalDateTime orderDate;

    @ToString.Include
    @Column(name = "ship_to_address")
    private String shipToAddress;

    @Enumerated(EnumType.STRING)
    @ToString.Include
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @ToString.Include
    @Column(name = "discount")
    private Double discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
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
        return totalDue * (1 - discount);
    }



}
