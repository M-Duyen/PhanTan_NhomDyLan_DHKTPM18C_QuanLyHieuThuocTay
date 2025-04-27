package ui.model;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class ModelUser implements Serializable {
    private String userName;
    private boolean admin;

    public ModelUser(String userName, boolean admin) {
        this.userName = userName;
        this.admin = admin;
    }

    public ModelUser() {

    }
}
