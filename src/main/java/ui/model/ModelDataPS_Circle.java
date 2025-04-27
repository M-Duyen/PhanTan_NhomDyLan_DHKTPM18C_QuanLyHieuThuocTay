package ui.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ModelDataPS_Circle implements Serializable {
    String name;
    int count;

    public ModelDataPS_Circle(String name, int count) {
        this.name = name;
        this.count = count;
    }
}
