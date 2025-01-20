package entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class AdministrationRoute {
    @Id
    private String administrationID;
    private String administrationName;

    public AdministrationRoute() {

    }

    public AdministrationRoute(String administrationID) {
        setAdministrationID(administrationID);
    }

    public AdministrationRoute(String administrationID, String administrationName) {
        setAdministrationID(administrationID);
        setAdministrationName(administrationName);
    }

    public String getAdministrationID() {
        return administrationID;
    }

    public void setAdministrationID(String administrationID) {
        if(administrationID == null || administrationID.trim().isEmpty()){
            throw new IllegalArgumentException("Mã đường dùng không được rỗng");
        }
        this.administrationID = administrationID;
    }

    public String getAdministrationName() {
        return administrationName;
    }

    public void setAdministrationName(String administrationName) {
        if(administrationName == null || administrationName.trim().isEmpty()){
            throw new IllegalArgumentException("Tên đường dùng không được rỗng");
        }
        this.administrationName = administrationName;
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
        final AdministrationRoute other = (AdministrationRoute) obj;
        return Objects.equals(this.administrationID, other.administrationID);
    } 

    @Override
    public int hashCode() {
        return Objects.hash(administrationID);
    }

    @Override
    public String toString() {
        return "AdministrationRoute{" +
                "administrationID='" + administrationID + '\'' +
                ", administrationName='" + administrationName + '\'' +
                '}';
    }
}