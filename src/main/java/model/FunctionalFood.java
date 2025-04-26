package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "functional_foods")
@NoArgsConstructor
@ToString(callSuper = true)
public class FunctionalFood extends Product implements Serializable {

    @Column(columnDefinition = "nvarchar(20)")
    private String mainNutrients;
    @Column(columnDefinition = "nvarchar(20)")
    private String supplementaryIngredients;

    public FunctionalFood(String id, String productName, String registrationNumber, double purchasePrice, double taxPercentage, Vendor vendor, Category category, LocalDate endDate, String mainNutrients, String supplementaryIngredients, String noteUnit) {
        super(id, productName, registrationNumber, purchasePrice, taxPercentage, endDate, vendor, category, noteUnit);
        this.mainNutrients = mainNutrients;
        this.supplementaryIngredients = supplementaryIngredients;
    }
}
