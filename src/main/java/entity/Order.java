package entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @OneToMany
    @JoinColumn(name = "order")
    private List<OrderDetail> listOrderDetail;

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

    public Order() {
    }

    public Order(String orderID) {
        setOrderID(orderID);
    }

    public Order(String orderID, LocalDateTime orderDate, String shipToAddress, PaymentMethod paymentMethod, double discount, Employee employee, Customer customer, Prescription prescription) {
        setOrderID(orderID);
        setOrderDate(orderDate);
        this.shipToAddress = shipToAddress;
        this.paymentMethod = paymentMethod;
        this.discount = discount;
        this.employee = employee;
        this.customer = customer;
        this.prescription = prescription;
        listOrderDetail = new ArrayList<>();
    }

    public void addOrderDetail(OrderDetail orderDetail){
        listOrderDetail.add(orderDetail);
    }

    public void setOrderID(String orderID) {
        if (orderID == null || orderID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng");
        }
        this.orderID = orderID;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        if(orderDate.isAfter(LocalDate.now().plusDays(1).atStartOfDay())){
            throw new IllegalArgumentException("Ngày lập hóa đơn phải nhỏ hơn hoặc bằng ngày hiện tại");
        }
        this.orderDate = orderDate;
    }

    //Kiểm tra xem hóa đơn còn thời hạn đổi trả không
    public boolean isOverdue() {
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(orderDate, now);
        return daysBetween > 30;
    }
}
