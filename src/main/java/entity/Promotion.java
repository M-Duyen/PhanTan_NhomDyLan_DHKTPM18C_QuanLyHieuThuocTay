package entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "promotions")
public class Promotion {
    //TODO:
    @Id
    @Column(name = "promotion_id", nullable = false)
    private String promotionId;

    @Column(name = "promotion_name")
    private String promotionName;

    @ManyToOne
    @JoinColumn(name = "promotion_type_id")
    private PromotionType promotionType;

}
