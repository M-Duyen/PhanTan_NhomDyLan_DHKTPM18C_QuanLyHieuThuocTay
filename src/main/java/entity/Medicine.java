package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicines")
public class Medicine extends Product {

    @Column(name = "active_ingredient", columnDefinition = "nvarchar(20)")
    private String activeIngredient;

    @Column(name = "conversion_unit", columnDefinition = "nvarchar(20)")
    private String conversionUnit;

    @ManyToOne
    @JoinColumn(name = "administrationID")
    private AdministrationRoute administrationRoute;
}
