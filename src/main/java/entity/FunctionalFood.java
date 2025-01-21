package entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "functional_foods")
public class FunctionalFood extends Product {

    @Column(name = "main_nutrients", columnDefinition = "nvarchar(20)")
    private String mainNutrients;

    @Column(name = "supplementary_ingredients", columnDefinition = "nvarchar(20)")
    private String supplementaryIngredients;

    public FunctionalFood() {
    }
    
    public FunctionalFood(String productID, String productName, String registrationNumber,double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
			LocalDate endDate, String mainNutrients, String supplementaryIngredients) {
		super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category);
		setMainNutrients(mainNutrients);
        setSupplementaryIngredients(supplementaryIngredients);
	}

    public FunctionalFood(String productID, String productName, String registrationNumber,double purchasePrice, double taxPercentage, Vendor vendor, Category category, Promotion promotion,
                          LocalDate endDate, String mainNutrients, String supplementaryIngredients, String noteUnit) {
        super(productID, productName, registrationNumber, purchasePrice, taxPercentage, endDate, promotion, vendor, category, noteUnit);
        setMainNutrients(mainNutrients);
        setSupplementaryIngredients(supplementaryIngredients);
    }
	public FunctionalFood(String mainNutrients, String supplementaryIngredients) {
        setMainNutrients(mainNutrients);
        setSupplementaryIngredients(supplementaryIngredients);
    }

    public String getMainNutrients() {
        return mainNutrients;
    }

    public void setMainNutrients(String mainNutrients) {
        if(mainNutrients == null || mainNutrients.trim().isEmpty()){
            throw new IllegalArgumentException("Dưỡng chất chính không được rỗng");
        }
        this.mainNutrients = mainNutrients;
    }

    public String getSupplementaryIngredients() {
        return supplementaryIngredients;
    }
}
