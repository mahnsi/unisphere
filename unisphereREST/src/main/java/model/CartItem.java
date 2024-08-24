package model;

public class CartItem {
    private Product product;
    private Integer quantity;

    // Default Constructor
    public CartItem() {
        // No-argument constructor
    }

    // Constructor with fields
    public CartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter for product
    public Product getProduct() {
        return product;
    }

    // Setter for product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter for quantity
    public Integer getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
