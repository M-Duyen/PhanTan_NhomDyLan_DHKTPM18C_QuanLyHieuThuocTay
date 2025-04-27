package ui.model;

import lombok.Data;
import lombok.ToString;
import model.PackagingUnit;

import java.io.Serializable;

@Data
@ToString
public class ModelDataPS implements Serializable {
    String productID;
    String productName;
    PackagingUnit packagingUnit;
    int sold;
    int inStock;
    double totalPriceSold;

    public ModelDataPS(String productID, String productName, int sold, PackagingUnit packagingUnit) {
        this.productID = productID;
        this.productName = productName;
        this.sold = sold;
        this.packagingUnit = packagingUnit;
    }

    public ModelDataPS(String productID, String productName, PackagingUnit packagingUnit, int sold, int inStock, double totalPriceSold) {
        this.productID = productID;
        this.productName = productName;
        this.packagingUnit = packagingUnit;
        this.sold = sold;
        this.inStock = inStock;
        this.totalPriceSold = totalPriceSold;
    }
}
