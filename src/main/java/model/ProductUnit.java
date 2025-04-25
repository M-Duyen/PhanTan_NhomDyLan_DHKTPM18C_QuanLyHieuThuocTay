package model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class ProductUnit implements Serializable {
    @Column(name = "sell_price")
    private double sellPrice;

    @Column(name = "in_stock", columnDefinition = "int")
    private int inStock;

    public ProductUnit(double v, int quantityInStock) {
        this.sellPrice = v;
        this.inStock = quantityInStock;
    }

    public ProductUnit() {

    }
}
