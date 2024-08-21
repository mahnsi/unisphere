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
	public List<Product> getUserbyUsername(@PathParam("catid") int catid){
		
    	List<Product> products = productDAO.getProductsByCategory(catid);
        return products;
		
		
	}

    
}
