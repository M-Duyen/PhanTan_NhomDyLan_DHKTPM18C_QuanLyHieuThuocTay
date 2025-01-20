package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "vendors")
public class Vendor {
    //TODO:
    @Id
    @Column(name = "vendor_id", nullable = false)
    private String vendorID;

}
