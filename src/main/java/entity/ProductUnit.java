package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.Data;

@Embeddable
@Data
public class ProductUnit {
    private double sellPrice;
    @Column(columnDefinition = "int")
    private int inStock;

}
