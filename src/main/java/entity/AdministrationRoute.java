package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "administration_routes")
public class AdministrationRoute {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "administration_id")
    private String administrationID;

    private String administrationName;

    public AdministrationRoute() {

    }

    public AdministrationRoute(String administrationID) {
        setAdministrationID(administrationID);
    }

    public AdministrationRoute(String administrationID, String administrationName) {
        setAdministrationID(administrationID);
        setAdministrationName(administrationName);
    }

    public String getAdministrationID() {
        return administrationID;
    }

    public void setAdministrationID(String administrationID) {
        if(administrationID == null || administrationID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã đường dùng không được rỗng");
        }
        this.administrationID = administrationID;
    }

    public String getAdministrationName() {
        return administrationName;
    }

    public void setAdministrationName(String administrationName) {
        if(administrationName == null || administrationName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên đường dùng không được rỗng");
        }
        this.administrationName = administrationName;
    }
}