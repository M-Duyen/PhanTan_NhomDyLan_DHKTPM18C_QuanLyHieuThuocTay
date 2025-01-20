package entity;

import dao.OrderDetails_DAO;
import dao.Order_DAO;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

public class Order {
    private String orderID;
    private LocalDateTime orderDate;
    private String shipToAddress;
    private Enum_PaymentMethod paymentMethod;
    private double discount;
    private Employee employee;
    private Customer customer;

    private Prescription prescription;
    private ArrayList<OrderDetails> listOrderDetail;


    public Order() {
    }

    public Order(String orderID) {
        setOrderID(orderID);
    }

    public Order(String orderID, LocalDateTime orderDate, String shipToAddress, Enum_PaymentMethod paymentMethod, double discount, Employee employee, Customer customer, Prescription prescription) {
        setOrderID(orderID);
        setOrderDate(orderDate);
        this.shipToAddress = shipToAddress;
        this.paymentMethod = paymentMethod;
        this.discount = discount;
        this.employee = employee;
        this.customer = customer;
        this.prescription = prescription;
        listOrderDetail = new ArrayList<>();
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalDue(){
        return Math.ceil(Order_DAO.getInstance().getTotalDue(orderID));
    }

    public void addOrderDetail(OrderDetails odt){
        listOrderDetail.add(odt);
    }
    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        if (orderID == null || orderID.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã hóa đơn không được rỗng");
        }
        this.orderID = orderID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
       if(orderDate.isAfter(LocalDate.now().plusDays(1).atStartOfDay())){
           throw new IllegalArgumentException("Ngày lập hóa đơn phải nhỏ hơn hoặc bằng ngày hiện tại");
       }
        this.orderDate = orderDate;
    }

    public String getShipToAddress() {
        return shipToAddress;
    }

    public void setShipToAddress(String shipToAddress) {
        this.shipToAddress = shipToAddress;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Enum_PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Enum_PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    //Kiểm tra xem hóa đơn còn thời hạn đổi trả không
    public boolean isOverdue() {
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(orderDate, now);
        return daysBetween > 30;
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
        final Order other = (Order) obj;
        return Objects.equals(this.orderID, other.orderID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderID);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID='" + orderID + '\'' +
                ", orderDate=" + orderDate +
                ", shipToAddress='" + shipToAddress + '\'' +
                ", paymentMethod=" + paymentMethod +
                ", discount=" + discount +
                ", totalDue= " + getTotalDue() +
                ", employee=" + employee +
                ", customer=" + customer.getPhoneNumber() +
                ", prescription=" + prescription +
                '}';
    }
}
