package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
@Entity
public class Category {
    @Id
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

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        if(categoryID == null || categoryID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã loại sản phẩm không được rỗng");
        }
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        if(categoryName == null || categoryName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên loại sản phẩm không được rỗng");
        }
        this.categoryName = categoryName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Category other = (Category) obj;
        return Objects.equals(this.categoryID, other.categoryID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryID);
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryID='" + categoryID + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
