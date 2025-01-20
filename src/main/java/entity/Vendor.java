package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;
import java.util.Objects;
@Entity
public class Vendor {
    @Id
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

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        if(vendorID == null || vendorID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã nhà cung cấp không được rỗng");
        }
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        if(vendorName == null || vendorName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên nhà cung cấp không được rỗng");
        }
        this.vendorName = vendorName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        if(country == null || country.trim().isEmpty()){
            throw new IllegalArgumentException("Nước sản xuất không được rỗng");
        }
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vendor other = (Vendor) obj;
        return Objects.equals(this.vendorID, other.vendorID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vendorID);
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorID='" + vendorID + '\'' +
                ", vendorName='" + vendorName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
