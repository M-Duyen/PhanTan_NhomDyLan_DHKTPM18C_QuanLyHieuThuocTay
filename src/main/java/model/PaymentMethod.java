package model;

public enum PaymentMethod {
    CASH("CASH"), BANK_TRANSFER("BANK TRANSFER"), CREDIT_CARD("CREDIT CARD");
    private String paymentMethod;

    PaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return paymentMethod;
    }
}
