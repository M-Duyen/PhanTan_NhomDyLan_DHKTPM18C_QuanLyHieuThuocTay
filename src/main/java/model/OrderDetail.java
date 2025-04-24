package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @EqualsAndHashCode.Include
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private PackagingUnit unit;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @Transient
    public double getLineTotal() {
        if (product != null) {
//            return orderQuantity * product.getUnitPrice(unit);
            return orderQuantity * product.getSellPrice(unit);
        }
        return 0;
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
