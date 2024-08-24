package model;

public class Product {
    private int id;
    private float price;
    private String title;
    private String description;
    private int category_id;
    private int subcategory_id;
    private int stock;
    private int sold;
    

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
        return this.category_id;
    }

    public void setCategory(int category) {
        this.category_id = category;
    }
    
    public int getSubCategoryId() {
        return this.category_id;
    }

    public void setSubCategory(int category) {
        this.category_id = category;
    }
    
    public int getStock() {
        return this.stock;
    }

    public void setStock(int s) {
        this.stock = s;
    }
    
    public int getSold() {
        return this.stock;
    }
    
    public void setSold(int s) {
    	this.sold = s;
    }
}
