package model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data

@Entity
@Table(name = "vendors")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = "products")
@NoArgsConstructor
public class Vendor implements Serializable {

    @Id
    @Column(name = "vendor_id", nullable = false)
    @EqualsAndHashCode.Include
    private String vendorID;

    @Column(name = "vendor_name")
    private String vendorName;

    private String country;

    @OneToMany(mappedBy = "vendor")
    private List<Product> products;

    public Vendor(String vendorID, String vendorName, String vendorCountry) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.country = vendorCountry;
    }
}
