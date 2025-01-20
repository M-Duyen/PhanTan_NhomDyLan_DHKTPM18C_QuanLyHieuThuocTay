package ui.model;

public class ModelDataRS {
    String month;
    double all;
    double revenueMedicine;
    double revenueMedicalS;
    double revenueFunctionalFood;

    public ModelDataRS(String month, double all, double revenueMedicine, double revenueMedicalS, double revenueFunctionalFood) {
        this.month = month;
        this.all = all;
        this.revenueMedicine = revenueMedicine;
        this.revenueMedicalS = revenueMedicalS;
        this.revenueFunctionalFood = revenueFunctionalFood;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }

    public double getRevenueMedicine() {
        return revenueMedicine;
    }

    public void setRevenueMedicine(double revenueMedicine) {
        this.revenueMedicine = revenueMedicine;
    }

    public double getRevenueMedicalS() {
        return revenueMedicalS;
    }

    public void setRevenueMedicalS(double revenueMedicalS) {
        this.revenueMedicalS = revenueMedicalS;
    }

    public double getRevenueFunctionalFood() {
        return revenueFunctionalFood;
    }

    public void setRevenueFunctionalFood(double revenueFunctionalFood) {
        this.revenueFunctionalFood = revenueFunctionalFood;
    }

    @Override
    public String toString() {
        return "ModelDataRS{" +
                "month='" + month + '\'' +
                ", all=" + all +
                ", revenueMedicine=" + revenueMedicine +
                ", revenueMedicalS=" + revenueMedicalS +
                ", revenueFunctionalFood=" + revenueFunctionalFood +
                '}';
    }
}
