package entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Promotion extends PromotionType {
    @Id
    @Column(name = "promotion_id", nullable = false)
    private String promotionId;

    @Column(name = "promotion_name")
    private String promotionName;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    private double discount;

    private boolean stats;

    @ManyToOne
    @JoinColumn(name = "promotion_type_id")
    private PromotionType promotionType;
}
