package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ProductUnit {
    @Column(columnDefinition = "money")
    private double sellPrice;
    @Column(columnDefinition = "int")
    private int inStock;

    public ProductUnit() {}

    public ProductUnit(double sellPrice, int inStock) {
        this.sellPrice = sellPrice;
        this.inStock = inStock;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }
}
