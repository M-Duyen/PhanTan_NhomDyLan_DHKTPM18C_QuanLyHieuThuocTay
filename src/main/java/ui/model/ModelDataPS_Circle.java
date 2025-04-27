package ui.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ModelDataPS_Circle implements Serializable {
    String name;
    long count;

    public ModelDataPS_Circle(String name, long count) {
        this.name = name;
        this.count = count;
    }
}
