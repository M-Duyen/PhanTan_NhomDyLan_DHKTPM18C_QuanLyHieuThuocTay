package entity;

import dao.Customer_DAO;
import dao.Order_DAO;
import database.ConnectDB;


import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private String customerID;
    private String phoneNumber;
    private String customerName;
    private boolean gender;
    private String email ;
    private String addr;
    private LocalDate brithDate;

    public Customer(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }

    public Customer(String customerID, String phoneNumber, String customerName, String email, boolean gender, String addr, LocalDate brithDate) {
        this.customerID = customerID;
        this.phoneNumber = phoneNumber;
        this.customerName = customerName;
        this.email = email;
        this.gender = gender;
        this.addr = addr;
        this.brithDate = brithDate;
    }

    public double getPoint() {
        return new Customer_DAO().getCustomerPoint(getPhoneNumber());
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được rỗng");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được rỗng");
        }
        this.customerName = customerName;
    }

    public LocalDate getBrithDate() {
        return brithDate;
    }


    public void setBrithDate(LocalDate brithDate) {
        if (brithDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày sinh phải nhỏ hơn ngày hiện tại");
        }
        this.brithDate = brithDate;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        final Customer other = (Customer) obj;
        return Objects.equals(this.phoneNumber, other.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID='" + customerID + '\'' +
                "phoneNumber='" + phoneNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ",sex=" + gender + '\'' +
                ", brithDate=" + brithDate +
                ", email='" + email + '\'' +
                ", point=" + getPoint() +
                ", address='" + addr + '\'' +
                '}';
    }
}
