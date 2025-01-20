package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "order_details")
public class OrderDetails {
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

    @Id
    @EqualsAndHashCode.Include
    @Enumerated(EnumType.STRING)
    private Enum_PackagingUnit unit;

    @Column(name = "order_quantity", nullable = false)
    private int orderQuantity;

    @Transient
    public double getLineTotal() {
        if (product != null) {
            return orderQuantity * product.getSellPrice(unit);
        }
        return 0;
    }
    public OrderDetails() {
    }
}
