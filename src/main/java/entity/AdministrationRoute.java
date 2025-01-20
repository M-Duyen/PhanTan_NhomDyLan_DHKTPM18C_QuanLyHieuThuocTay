package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "administration_routes")
public class AdministrationRoute {

    public AdministrationRoute() {
    }

    public AdministrationRoute(String administrationRouteID, String administrationRouteName) {
        this.administrationRouteID = administrationRouteID;
        this.administrationRouteName = administrationRouteName;
    }

    @Id
    @Column(name = "administration_route_id")
    private String administrationRouteID;
    @Column(name = "administration_route_name")
    private String administrationRouteName;


}
