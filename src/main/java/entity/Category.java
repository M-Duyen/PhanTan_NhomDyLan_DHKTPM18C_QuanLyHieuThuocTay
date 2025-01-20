package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "categories")
public class Category {
    //TODO:
    @Id
    @Column(name = "category_id", nullable = false, unique = true)
    @EqualsAndHashCode.Include
    private String categoryID;
    private String categoryName;

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
