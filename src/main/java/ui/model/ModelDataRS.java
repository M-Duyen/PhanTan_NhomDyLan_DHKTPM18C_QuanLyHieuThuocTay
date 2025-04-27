package ui.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ModelDataRS implements Serializable {
    String month;
    double all;
    double revenueMedicine;
    double revenueMedicalS;
    double revenueFunctionalFood;

    public ModelDataRS(String month) {
        this.month = month;
    }
    public ModelDataRS(String month, double all, double revenueMedicine, double revenueMedicalS, double revenueFunctionalFood) {
        this.month = month;
        this.all = all;
        this.revenueMedicine = revenueMedicine;
        this.revenueMedicalS = revenueMedicalS;
        this.revenueFunctionalFood = revenueFunctionalFood;
    }
}
