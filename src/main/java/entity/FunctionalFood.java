package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "functional_foods")
public class FunctionalFood extends Product {

    @Column(columnDefinition = "nvarchar(20)")
    private String mainNutrients;
    @Column(columnDefinition = "nvarchar(20)")
    private String supplementaryIngredients;
}
