package model;

public class Product {
    private int id;
    private float price;
    private String title;
    private String description;
    private int categoryId;
    private int subcategory;
    private int stock;
    private int sold = 0;
    

    // Getter and Setter for id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter for price
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    // Getter and Setter for title
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for category
    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategory(int category) {
    	this.categoryId = category;
    }
    
    public int getSubCategory() {
        return this.subcategory;
    }

    public void setSubCategory(int category) {
        this.subcategory = category;
    }
    
    public int getStock() {
        return this.stock;
    }

    public void setStock(int s) {
        this.stock = s;
    }
    
    public int getSold() {
        return this.sold;
    }
    
    public void setSold(int s) {
    	this.sold = s;
    }
    
    @Override
    public String toString() {
        return "Product{title='" + title + "', price=" + price + '}';
    }
}
