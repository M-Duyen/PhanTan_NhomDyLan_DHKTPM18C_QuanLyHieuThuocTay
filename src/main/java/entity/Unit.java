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
}
