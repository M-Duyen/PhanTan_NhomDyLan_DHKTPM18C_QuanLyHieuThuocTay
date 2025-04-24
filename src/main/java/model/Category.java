package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "categories")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "products")
public class Category implements Serializable {
    @Id
    @Column(name = "category_id", nullable = false)
    @EqualsAndHashCode.Include
    private String categoryID;

    @Column(name = "category_name")
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products;


}
