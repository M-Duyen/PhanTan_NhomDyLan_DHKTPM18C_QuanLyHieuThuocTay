package ui.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ModelUser {
    private String userName;
    private boolean admin;

    public ModelUser(String userName, boolean admin) {
        this.userName = userName;
        this.admin = admin;
    }

    public ModelUser() {

    }
}
