package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Data
public class ProductUnit {
    @Column(name = "sell_price")
    private double sellPrice;

    @Column(name = "in_stock", columnDefinition = "int")
    private int inStock;

}
