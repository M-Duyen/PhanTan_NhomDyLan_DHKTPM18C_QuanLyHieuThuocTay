package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "promotions")
public class Promotion extends PromotionType {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
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
    public Promotion() {
    }


    public Promotion(String promotionID) {
        setPromotionID(promotionID);
    }


    public Promotion(String promotionID, String promotionName, LocalDate startDate, LocalDate endDate, double discount, boolean stats, PromotionType promotionType) {
        this.promotionID = promotionID;
        this.promotionName = promotionName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
        this.stats = stats;
        this.promotionType = promotionType;
    }

    public void setPromotionID(String promotionID) {
        if (promotionID == null || promotionID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mãi không được rỗng");
        }
        this.promotionID = promotionID;
    }

    public void setPromotionName(String promotionName) {
        if (promotionName == null || promotionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được rỗng");
        }
        this.promotionName = promotionName;
    }

    public void setDiscount(double discount) {
        if(discount < 0){
            throw new IllegalArgumentException("Phần trăm khuyến mãi >= 0");
        }
        this.discount = discount;
    }

    public void setStartDate(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Ngày bắt đầu không thể là null.");
        }
        if (startDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày bắt đầu phải là ngày hiện tại hoặc sau ngày hiện tại.");
        }
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "promotionID='" + promotionID + '\'' +
                ", promotionName='" + promotionName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", discount=" + discount +
                ", status=" + (stats ? "Đang áp dụng" : "Ngưng áp dụng") +
                ", promotionType=" + promotionType +
                '}';
    }
}
