package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class ProductUnit {
    @Column(columnDefinition = "money")
    private double sellPrice;
    @Column(columnDefinition = "int")
    private int inStock;

}
