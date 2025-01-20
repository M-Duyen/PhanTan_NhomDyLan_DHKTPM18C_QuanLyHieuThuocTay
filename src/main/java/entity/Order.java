package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

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
    private Enum_PaymentMethod paymentMethod;

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
    private ArrayList<OrderDetails> listOrderDetail;

    @Transient
    public double getTotalDue() {
        double totalDue = 0;
        if (listOrderDetail != null) {
            for (OrderDetails orderDetail : listOrderDetail) {
                totalDue += orderDetail.getLineTotal();
            }
        }
        return totalDue * (1 - discount);
    }

    //Kiểm tra xem hóa đơn còn thời hạn đổi trả không
    public boolean isOverdue() {
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(orderDate, now);
        return daysBetween > 30;
    }
}
