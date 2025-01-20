package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {
    private String categoryID;
    private String categoryName;
}
