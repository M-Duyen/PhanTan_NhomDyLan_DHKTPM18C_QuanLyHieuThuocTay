package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vendor")
public class Vendor {
    @Id
    @Column(name = "vendor_id")
    private String vendorID;

    @Column(name = "vendor_name")
    private String vendorName;

    private String country;
}
