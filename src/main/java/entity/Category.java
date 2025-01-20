package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Id;
import java.util.Objects;

@Entity
@Data
@Table(name = "categories")
public class Category {
    @Id
    private String categoryID;
    private String categoryName;
}
