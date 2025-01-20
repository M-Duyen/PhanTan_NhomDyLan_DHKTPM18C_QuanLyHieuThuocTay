package entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Entity
@Table(name = "vendors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vendor {

    @Id
    @Column(name = "vendor_id", nullable = false)
    @EqualsAndHashCode.Include
    private String vendorID;

    @Column(name = "vendor_name")
    private String vendorName;

    private String country;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;
}
