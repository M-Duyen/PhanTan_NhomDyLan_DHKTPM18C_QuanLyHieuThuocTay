package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;
@Entity
@Data
@Table(name = "vendors")
public class Vendor {
    @Id
    @Column(name = "vendor_id", nullable = false)
    private String vendorID;
    private String vendorName;
    private String country;
}
