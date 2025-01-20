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
//@Data
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


    public String getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(String promotionID) {
        if (promotionID == null || promotionID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khuyến mãi không được rỗng");
        }
        this.promotionID = promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        if (promotionName == null || promotionName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khuyến mãi không được rỗng");
        }
        this.promotionName = promotionName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        if(discount < 0){
            throw new IllegalArgumentException("Phần trăm khuyến mãi >= 0");
        }
        this.discount = discount;
    }

    public boolean isStats() {
        return stats;
    }

    public void setStats(boolean stats) {
        this.stats = stats;
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

    public LocalDate getEndDate() {
        return endDate;
    }

    public PromotionType getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(PromotionType promotionType) {
        this.promotionType = promotionType;
    }


    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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
        final Promotion other = (Promotion) obj;
        return Objects.equals(this.promotionID, other.promotionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), promotionID);
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
