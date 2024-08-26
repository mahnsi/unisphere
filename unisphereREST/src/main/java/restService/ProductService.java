package restService;

import model.Catalogue;

import model.Product;
import model.User;
import dao.ProductDAO;
import ds.Tuple;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Path("Products")
public class ProductService {

    private ProductDAO productDAO;
    private Catalogue catalogue = Catalogue.getInstance();

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
        catalogue.setProducts(products);
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
	@Path("/getProductsByCategory/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByCategory(@PathParam("cat") String cat){
		
    	List<Product> products = productDAO.getProductsByCategory(cat);
    	catalogue.setProducts(products);
        return products;
		
		
	}
    
    @GET
	@Path("/getProductsBySubcategory/{cat}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsBySubCategory(@PathParam("cat") String cat){
    	System.out.println("REST getProductsBySubCategory called");
    	List<Product> products = productDAO.getProductsBySubCategory(cat);
    	catalogue.setProducts(products);
        return products;
		
		
	}
    
    @GET
	@Path("/getProductsByKeyword/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByKeyword(@PathParam("key") String key){
    	System.out.println("REST getProductsByKeyword called");
    	List<Product> products = productDAO.getProductsByKeyword(key);
    	System.out.println("h"+products.size());
    	catalogue.setProducts(products);
        return products;
		
		
	}
    
    @GET
    @Path("/sort/{method}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> sort(@PathParam("method") int method) {
    	System.out.println("rest sort catalogue called");
    	
    	List<Product> products = catalogue.getProducts();
        catalogue.sort(method);
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
	public List<Tuple> getSubcategoriesByCategory(@PathParam("cat") String cat){
		
    	List<Tuple> cats = productDAO.getSubcategoriesByCategory(cat);
        return cats;
    }
    
    @GET
	@Path("/getAllCategories")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCategories(){
		
    	List<String> cats = productDAO.getAllCategories();
        return cats;
    }
		
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addProduct(Product product) {
        try {
        	System.out.println("addProduct REST called");
            // Add the product using the ProductDAO
            productDAO.addProduct(product);
            catalogue.addProduct(product);
            // Return a success response with the created product's ID
            return Response.status(Response.Status.CREATED)
                           .entity("Product added successfully with ID: " + product.getId())
                           .build();
        } catch (Exception e) {
            e.printStackTrace();
            // Return a server error response in case of failure
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error adding product: " + e.getMessage())
                           .build();
        }
    }
    
    @PUT
    @Path("/updateInventory")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateInventory(@QueryParam("id") int id, @QueryParam("quantity") int quantity) {
        
    	try {
    		System.out.println("REST updateInventory called");
            // Retrieve the product using the ProductDAO and update its inventory
            boolean updated = productDAO.updateProductQuantity(id, quantity);

            if (updated) {
                // Return a success response indicating the product inventory was updated
            	System.out.println("updateinventory success from service");
                return Response.status(Response.Status.OK)
                               .entity("Product inventory updated successfully for ID: " + id)
                               .build();
            } else {
                // Return a not found response if the product was not found
                return Response.status(Response.Status.NOT_FOUND)
                               .entity("Product with ID " + id + " not found.")
                               .build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Return a server error response in case of failure
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error updating product inventory: " + e.getMessage())
                           .build();
        }
    }
    
    
    @POST
    @Path("/uploadImage")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(FormDataMultiPart input) {
    	System.out.println("upload image rest called");
        try {
            // Retrieve the file part from the input
            FormDataBodyPart filePart = input.getField("image");
            if (filePart != null) {
                FormDataContentDisposition fileDetails = filePart.getFormDataContentDisposition();
                InputStream inputStream = filePart.getValueAs(InputStream.class);

                // Define the file location
                String fileName = fileDetails.getFileName();
                File file = new File("/Users/mahnsi/Downloads" + fileName);

                // Write the file to the server
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }

                return Response.status(Status.OK).entity("Image uploaded successfully!").build();
            } else {
                return Response.status(Status.BAD_REQUEST).entity("No image found in the request.").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Failed to upload image.").build();
        }
    }
}
