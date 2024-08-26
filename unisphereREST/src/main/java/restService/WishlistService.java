package restService;

import model.Product;
import model.Wishlist;
import dao.WishlistDAO;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Wishlist")
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
    public Wishlist getWishlistByUsername(@PathParam("username") String uname) {
    	System.out.println("get wishlist service called");
        return wishlistDao.getWishlistByUsername(uname);
    }

    @POST
    @Path("/AddToWishlist/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToWishlist(@PathParam("username") String uname, Product product) {
    	System.out.println("add to wishlist service called");
    	int h = wishlistDao.insertIntoWishlist(product, uname);
        if(h > 1) {
        	return Response.ok("Product added to wishlist").build();
        }
        
        else if(h==-1) {
        	return Response.status(Response.Status.CONFLICT) // 409 Conflict
                    .entity("Product is already in the wishlist")
                    .build();
        }
        
        else {
        	return Response.status(Response.Status.UNAUTHORIZED) // 401 Unauthorized
                    .entity("You need to be signed in to access this resource")
                    .build();
        }
        
    }

    @DELETE
    @Path("/RemoveFromWishlist/{username}/{productId}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeFromWishlist(@PathParam("username") String uname, @PathParam("productId") int productId) {
        wishlistDao.removeFromWishlist(uname, productId);
        return Response.ok("Product removed from wishlist").build();
    }
}
