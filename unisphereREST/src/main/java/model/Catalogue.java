package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//singleton dp
public class Catalogue {
    private static Catalogue instance;
    private List<Product> products;

    // Private constructor to prevent instantiation
    private Catalogue() {
        products = new ArrayList<>();
    }

    // Public method to provide access to the single instance
    public static synchronized Catalogue getInstance() {
        if (instance == null) {
            instance = new Catalogue();
        }
        return instance;
    }


    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> cat) {
        this.products = cat;
    }

    public void sort(int method) {
        switch (method) {
            case 1:
                // Sort A-Z by title
                Collections.sort(products, Comparator.comparing(Product::getTitle));
                break;
            case 2:
                // Sort Z-A by title
                Collections.sort(products, Comparator.comparing(Product::getTitle).reversed());
                break;
            case 3:
                // Sort low to high by price
                Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
                break;
            case 4:
                // Sort high to low by price
                Collections.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
                break;
            default:
                throw new IllegalArgumentException("Invalid sort method: " + method);
        }
        
        printCatalogue();
    }
    
    //for debugging
    public void printCatalogue() {
        if (products.isEmpty()) {
            System.out.println("The catalogue is empty.");
        } else {
            System.out.println("Catalogue:");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

	public void addProduct(Product product) {
		products.add(product);
		
	}
}
