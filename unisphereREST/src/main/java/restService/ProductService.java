package restService;

import model.Product;
import model.User;
import dao.ProductDAO;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("Products")
public class ProductService {

    private ProductDAO productDAO;

    @Context
    private ServletContext context;

    @PostConstruct
    public void init() {
        productDAO = new ProductDAO(context);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        List<Product> products = productDAO.getAllProducts();
        System.out.println("getAllProducts called");
        return products;
    }

    @GET
    @Path("/getProductById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product getProductById(@PathParam("id") int id) {
        Product product = productDAO.getProductById(id);
        return product;
    }
    
    @GET
	@Path("/getProductsByCategory/{catid}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByCategory(@PathParam("catid") int catid){
		
    	List<Product> products = productDAO.getProductsByCategory(catid);
        return products;
		
		
	}
    
    @GET
	@Path("/getProductsBySubcategory/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsBySubCategory(@PathParam("cat") String cat){
    	System.out.println("REST getProductsBySubCategory called");
    	List<Product> products = productDAO.getProductsBySubCategory(cat);
        return products;
		
		
	}
    
    @GET
	@Path("/getProductsByKeyword/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByKeyword(@PathParam("key") String key){
    	System.out.println("REST getProductsByKeyword called");
    	List<Product> products = productDAO.getProductsByKeyword(key);
        return products;
		
		
	}
    
    @GET
	@Path("/getFeaturedProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getUserbyUsername(){
		
    	List<Product> products = productDAO.getFeaturedProducts();
        return products;
		
		
	}
    
    @GET
	@Path("/getSubcategoriesByCategory/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getSubcategoriesByCategory(@PathParam("cat") String cat){
		
    	List<String> cats = productDAO.getSubcategoriesByCategory(cat);
        return cats;
		
		
	}

    
}
