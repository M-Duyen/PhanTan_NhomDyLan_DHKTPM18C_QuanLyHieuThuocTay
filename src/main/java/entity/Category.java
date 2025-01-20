package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "categorys")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class Category {
    @Id
    @Column(name = "category_id", nullable = false)
    @EqualsAndHashCode.Include
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;


}
