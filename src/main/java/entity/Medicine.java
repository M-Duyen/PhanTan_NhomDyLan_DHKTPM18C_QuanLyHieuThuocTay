package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicines")
@Data
public class Medicine extends Product {

    @Column(columnDefinition = "nvarchar(20)")
    private String activeIngredient;
    @Column(columnDefinition = "nvarchar(20)")
    private String conversionUnit;
    @ManyToOne
    @JoinColumn(name = "administrationID")
    private AdministrationRoute administrationRoute;


}
