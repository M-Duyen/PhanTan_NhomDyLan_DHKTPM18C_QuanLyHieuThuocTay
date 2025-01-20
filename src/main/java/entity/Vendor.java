package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "vendors")
public class Vendor {
    //TODO:
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "vendor_id", nullable = false)
    private String vendorID;

    private String vendorName;

    private String country;

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
