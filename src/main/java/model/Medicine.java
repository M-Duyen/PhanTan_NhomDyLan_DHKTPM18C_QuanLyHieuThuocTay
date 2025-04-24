package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicines")
@Data
public class Medicine extends Product implements Serializable {

    @Column(columnDefinition = "nvarchar(20)")
    private String activeIngredient;
    @Column(columnDefinition = "nvarchar(20)")
    private String conversionUnit;
    @ManyToOne
    @JoinColumn(name = "administrationID")
    private AdministrationRoute administrationRoute;

}
