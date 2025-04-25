package ui.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ModelDataPS_Circle {
    String name;
    int count;

    public ModelDataPS_Circle(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
