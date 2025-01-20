package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@EqualsAndHashCode(callSuper = true)
public class FunctionalFood extends Product{
    @Column(columnDefinition = "nvarchar(20)")
    private String mainNutrients;
    @Column(columnDefinition = "nvarchar(20)")
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

    public void setSupplementaryIngredients(String supplementaryIngredients) {
        this.supplementaryIngredients = supplementaryIngredients;
    }

    @Override
    public String toString() {
        return "FunctionalFood{" +
                "mainNutrients='" + mainNutrients + '\'' +
                ", supplementaryIngredients='" + supplementaryIngredients + '\'' +
                "} " + super.toString();
    }
}
