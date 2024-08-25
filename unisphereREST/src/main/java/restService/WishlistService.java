package restService;
import model.*;
import dao.*;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Wishlisr")
public class WishlistService {
	
	 private WishlistDAO wishlistDao;

    @Context
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
    	wishlistDao = new WishlistDAO(servletContext);
    }
	
	
	
	@GET
	@Path("/getWishlistByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Wishlist getWishlistByUsername(@PathParam("username") String uname){
		
		Wishlist wlist = wishlistDao.getWishlistByUsername(uname);
		return wlist;

	}
	
	@POST
	@Path("/AddToWishlist/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddToWishlist(@PathParam("username") String uname, Product product) {
		
		//call dao add to wishlist
		wishlistDao.insertIntoWishlist(product, uname);
		return null;

    }
	

}
