package entity;

public class Unit {
    private String unitID;
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

