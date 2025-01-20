package entity;

import java.time.LocalDate;
import java.util.Objects;
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
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "customer_id")
    private String customerID;
    private String phoneNumber;
    private String customerName;
    private boolean gender;
    private String email ;
    private String addr;
    private LocalDate brithDate;



}
