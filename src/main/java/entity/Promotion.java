package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

}
