    package entity;

    import jakarta.persistence.*;
    import lombok.EqualsAndHashCode;
    import lombok.Data;

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
    }
