package ui.swing.progress;

public class ModelData {
    private String month;
    private double amount;
    private double cost;
    private double profit;

    public ModelData(String month, double amount, double cost, double profit) {
        this.month = month;
        this.amount = amount;
        this.cost = cost;
        this.profit = profit;
    }

    public String getMonth() {
        return month;
    }

    public double getAmount() {
        return amount;
    }

    public double getCost() {
        return cost;
    }

    public double getProfit() {
        return profit;
    }
}

