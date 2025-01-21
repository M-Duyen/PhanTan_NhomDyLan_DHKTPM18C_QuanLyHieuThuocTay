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

    public Vendor() {
    }

    public Vendor(String vendorID) {
        setVendorID(vendorID);
    }

    public Vendor(String vendorID, String vendorName, String country) {
        setVendorID(vendorID);
        setVendorName(vendorName);
        setCountry(country);
    }

    public void setVendorID(String vendorID) {
        if(vendorID == null || vendorID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã nhà cung cấp không được rỗng");
        }
        this.vendorID = vendorID;
    }

    public void setVendorName(String vendorName) {
        if(vendorName == null || vendorName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên nhà cung cấp không được rỗng");
        }
        this.vendorName = vendorName;
    }

    public void setCountry(String country) {
        if(country == null || country.trim().isEmpty()){
            throw new IllegalArgumentException("Nước sản xuất không được rỗng");
        }
        this.country = country;
    }
}
