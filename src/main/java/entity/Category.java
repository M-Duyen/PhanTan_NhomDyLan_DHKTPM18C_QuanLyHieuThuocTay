package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "categorys")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Category {
    public Category(String categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public Category() {
    }

    @Id
    @Column(name = "category_id", nullable = false)
    @EqualsAndHashCode.Include
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;
}
