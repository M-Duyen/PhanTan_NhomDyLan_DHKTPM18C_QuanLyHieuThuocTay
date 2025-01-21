package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "administration_routes")
public class AdministrationRoute {
    @Id
    @Column(name = "administration_route_id")
    @EqualsAndHashCode.Include
    private String administrationRouteID;

    @Column(name = "administration_route_name")
    private String administrationRouteName;

    public AdministrationRoute() {
    }

    public AdministrationRoute(String administrationRouteID) {
        setAdministrationID(administrationRouteID);
    }

    public AdministrationRoute(String administrationRouteID, String administrationName) {
        setAdministrationID(administrationRouteID);
        setAdministrationName(administrationName);
    }

    public void setAdministrationID(String administrationRouteID) {
        if(administrationRouteID == null || administrationRouteID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã đường dùng không được rỗng");
        }
        this.administrationRouteID = administrationRouteID;
    }

    public void setAdministrationName(String administrationName) {
        if(administrationName == null || administrationName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên đường dùng không được rỗng");
        }
        this.administrationRouteID = administrationName;
    }
}