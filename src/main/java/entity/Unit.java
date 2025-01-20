package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "units")
public class Unit {
    @Id
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

    // Getters and setters
    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Unit [unitID=" + unitID + ", unitName=" + unitName + ", description=" + description + "]";
    }
}

