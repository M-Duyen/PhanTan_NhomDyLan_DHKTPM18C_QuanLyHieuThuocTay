package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class PromotionType {
    @Id
    private String promotionTypeID;
    private String promotionTypeName;

}
