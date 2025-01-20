package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "units")
public class Unit {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "unit_id", nullable = false)
    private String unitID;

    @Column(name = "unit_name")
    private String unitName;

    private String description;

    public Unit(String unitID, String unitName, String description) {
        this.unitID = unitID;
        this.unitName = unitName;
        this.description = description;
    }

    public Unit(String unitID) {
        this.unitID = unitID;
    }

    public Unit() {}
}

