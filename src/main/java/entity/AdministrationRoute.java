package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "administration_route")
public class AdministrationRoute {
    @Id
    @Column(name = "administration_route_id")
    private String administrationRouteID;
    @Column(name = "administration_route_name")
    private String administrationRouteName;
}
