package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
public class AdministrationRoute {
    @Id
    private String administrationID;
    private String administrationName;
}