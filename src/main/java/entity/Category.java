package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "categories")
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

    public Category() {
    }

    public Category(String categoryID) {
        setCategoryID(categoryID);
    }

    public Category(String categoryID, String categoryName) {
        setCategoryID(categoryID);
        setCategoryName(categoryName);
    }

    public void setCategoryID(String categoryID) {
        if(categoryID == null || categoryID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã loại sản phẩm không được rỗng");
        }
        this.categoryID = categoryID;
    }

    public void setCategoryName(String categoryName) {
        if(categoryName == null || categoryName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên loại sản phẩm không được rỗng");
        }
        this.categoryName = categoryName;
    }
}
