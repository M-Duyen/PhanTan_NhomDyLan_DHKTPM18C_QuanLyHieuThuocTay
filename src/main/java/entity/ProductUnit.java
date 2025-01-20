package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Data
@Table(name = "product_units")
public class ProductUnit {
    @Column(columnDefinition = "money")
    private double sellPrice;
    @Column(columnDefinition = "int")
    private int inStock;

}
