package se.jensen.linkan.userorderservice.dto;

public class OrderResponse {

    private String productTitle;
    private Double productPrice;
    private Integer quantity;
    private Double totalPrice;

    public OrderResponse() {
    }

    public OrderResponse(String productTitle,
                         Double productPrice,
                         Integer quantity) {

        this.productTitle = productTitle;
        this.productPrice = productPrice != null ? productPrice : 0.0;
        this.quantity = quantity != null ? quantity : 0;
        this.totalPrice = this.productPrice * this.quantity;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
}