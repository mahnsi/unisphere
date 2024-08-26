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
        return wishlistDao.getWishlistByUsername(uname);
    }

    @POST
    @Path("/AddToWishlist/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToWishlist(@PathParam("username") String uname, Product product) {
        wishlistDao.insertIntoWishlist(product, uname);
        return Response.ok("Product added to wishlist").build();
    }

    @DELETE
    @Path("/RemoveFromWishlist/{username}/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFromWishlist(@PathParam("username") String uname, @PathParam("productId") int productId) {
        wishlistDao.removeFromWishlist(uname, productId);
        return Response.ok("Product removed from wishlist").build();
    }
}
