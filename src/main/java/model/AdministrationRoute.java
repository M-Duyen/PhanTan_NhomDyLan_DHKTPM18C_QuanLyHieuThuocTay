package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "administration_routes")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AdministrationRoute implements Serializable {

    @Id
    @Column(name = "administration_route_id")
    @EqualsAndHashCode.Include
    private String administrationRouteID;
    @Column(name = "administration_route_name")
    private String administrationRouteName;


}
