package entity;

import java.util.Objects;

public class OrderDetails {
    private int orderQuantity;

    private Order order;
    private Product product;
    private PackagingUnit unit;

    public OrderDetails() {
    }

    public OrderDetails(Order order, Product product) {
        this.order = order;
        this.product = product;
    }

    public OrderDetails(Order order, Product product, int orderQuantity) {
        this.order = order;
        this.product = product;
        setOrderQuantity(orderQuantity);
    }

    public OrderDetails(int orderQuantity, Order order, Product product, PackagingUnit unit) {
        this.orderQuantity = orderQuantity;
        this.order = order;
        this.product = product;
        this.unit = unit;
    }

    public PackagingUnit getUnit() {
        return unit;
    }

    public void setUnit(PackagingUnit unit) {
        this.unit = unit;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        if(orderQuantity <= 0){
            throw new IllegalArgumentException("Số lượng sản phẩm phai lớn hơn 0");
        }
        this.orderQuantity = orderQuantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getLineTotal(){
        return product.getPriceByUnit(unit) * orderQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetails that = (OrderDetails) o;
        return Objects.equals(order, that.order) && Objects.equals(product, that.product) && unit == that.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, product, unit);
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderQuantity=" + orderQuantity +
                "lineTotal=" + getLineTotal() +
                ", order=" + order +
                ", product=" + product +
                ", unit=" + unit +
                '}';
    }
}
