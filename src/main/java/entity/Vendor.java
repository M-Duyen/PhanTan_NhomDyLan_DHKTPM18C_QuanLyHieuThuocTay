package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vendors")
public class Vendor {

    public Vendor(String vendorID, String vendorName, String country) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.country = country;
    }

    public Vendor() {
    }

    @Id
    @Column(name = "vendor_id", nullable = false)
    private String vendorID;

    @Column(name = "vendor_name")
    private String vendorName;

    private String country;
}
