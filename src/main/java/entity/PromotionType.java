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
@Table(name = "promotion_types")
public class PromotionType {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "promotion_type_id")
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
}
