package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@Table(name = "promotion_types")
public class PromotionType {
    @Id
    @Column(name = "promotion_type_id", nullable = false, columnDefinition = "char(4)")
    @EqualsAndHashCode.Include
    private String promotionTypeID;

    @Column(name = "promotion_type_name", nullable = false)
    private String promotionTypeName;

    @OneToMany(mappedBy = "promotionType")
    private List<Promotion> promotion;
}
