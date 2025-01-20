package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class PromotionType {
    @Id
    private String promotionTypeID;
    private String promotionTypeName;

    public PromotionType() {
    }

    public PromotionType(String promotionTypeID) {
        this.promotionTypeID = promotionTypeID;
    }

    public PromotionType(String promotionTypeID, String promotionTypeName) {
        this.promotionTypeID = promotionTypeID;
        this.promotionTypeName = promotionTypeName;
    }

    public String getPromotionTypeID() {
        return promotionTypeID;
    }

    public void setPromotionTypeID(String promotionTypeID) {
        if(promotionTypeID == null || promotionTypeID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã loại khuyến mãi không được rỗng");
        }
        this.promotionTypeID = promotionTypeID;
    }

    public String getPromotionTypeName() {
        return promotionTypeName;
    }

    public void setPromotionTypeName(String promotionTypeName) {
        if(promotionTypeName == null || promotionTypeName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên loại khuyến mãi không được rỗng");
        }
        this.promotionTypeName = promotionTypeName;
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
        final PromotionType other = (PromotionType) obj;
        return Objects.equals(this.promotionTypeID, other.promotionTypeID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotionTypeID);
    }

    @Override
    public String toString() {
        return "PromotionType{" +
                "promotionTypeID='" + promotionTypeID + '\'' +
                ", promotionName='" + promotionTypeName + '\'' +
                '}';
    }
}
