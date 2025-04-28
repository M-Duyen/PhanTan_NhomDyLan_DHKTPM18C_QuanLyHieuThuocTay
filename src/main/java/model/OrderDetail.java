package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Table(name = "order_details")
@ToString(onlyExplicitlyIncluded = true)
public class OrderDetail implements Serializable {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @ToString.Include
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @ToString.Include
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    @ToString.Include
    private PackagingUnit unit;

    @Column(name = "order_quantity", nullable = false)
    @ToString.Include
    private int orderQuantity;


    @Transient
    @ToString.Include
    public double getLineTotal() {
        if (product != null) {
            return orderQuantity * product.getSellPrice(unit);
        }
        return 0;
    }

    public OrderDetail() {
    }

    public OrderDetail(Order order, Product product, PackagingUnit unit, int orderQuantity) {
        this.order = order;
        this.product = product;
        this.unit = unit;
        this.orderQuantity = orderQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PackagingUnit getUnit() {
        return unit;
    }

    public void setUnit(PackagingUnit unit) {
        this.unit = unit;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "order=" + order +
                ", product=" + product +
                ", unit=" + unit +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
