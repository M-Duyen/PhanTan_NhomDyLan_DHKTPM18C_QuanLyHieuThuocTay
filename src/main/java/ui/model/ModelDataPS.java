package ui.model;

public class ModelDataPS {
    String productName;
    String unitDes;
    int sold;
    int inStock;
    double purchasePrice;
    double sellPrice;
    double profit;

    public ModelDataPS(String productName, String unitDes, int sold, int inStock, double purchasePrice, double sellPrice) {
        this.productName = productName;
        this.unitDes = unitDes;
        this.sold = sold;
        this.inStock = inStock;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.profit = profit;
    }

    public ModelDataPS(String productName, String unitDes, int sold, int inStock) {
        this.productName = productName;
        this.unitDes = unitDes;
        this.sold = sold;
        this.inStock = inStock;
    }

    public String getUnitDes() {
        return unitDes;
    }

    public void setUnitDes(String unitDes) {
        this.unitDes = unitDes;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    @Override
    public String toString() {
        return "ModelDataPS{" +
                "productName='" + productName + '\'' +
                ", unitDes='" + unitDes + '\'' +
                ", sold=" + sold +
                ", inStock=" + inStock +
                ", purchasePrice=" + purchasePrice +
                ", sellPrice=" + sellPrice +
                ", profit=" + profit +
                '}';
    }
}
