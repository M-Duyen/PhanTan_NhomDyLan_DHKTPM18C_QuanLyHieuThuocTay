package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "promotions")
@ToString(exclude = "promotionType")
public class Promotion implements Serializable {
    @Id
    @Column(name = "promotion_id", nullable = false)
    private String promotionId;

    @Column(name = "promotion_name")
    private String promotionName;

    @ManyToOne
    @JoinColumn(name = "promotion_type_id")
    private PromotionType promotionType;

    @Column(name = "start_date", columnDefinition = "date")
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "date")
    private LocalDate endDate;
    private double discount;
    private boolean status;
}
