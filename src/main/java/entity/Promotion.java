package entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "promotions")
public class Promotion{
    @Id
    @Column(name = "promotion_id", nullable = false)
    private String promotionID;
    @Column(name = "promotion_name")
    private String promotionName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double discount;
    private boolean stats;
    @ManyToOne
    @JoinColumn(name = "promotionTypeID")
    private PromotionType promotionType;

    //ex:
//    @OneToMany(mappedBy = "promotion")
//    private Set<Product> productList;
}
