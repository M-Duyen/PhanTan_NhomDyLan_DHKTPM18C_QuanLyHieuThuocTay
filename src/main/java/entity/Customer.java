package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "customers")
public class Customer {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "customer_id")
    private String customerID;

}