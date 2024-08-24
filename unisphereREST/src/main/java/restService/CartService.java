package restService;

import model.Product;
import model.User;
import model.Cart;
import dao.CartDAO;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("Cart")
public class CartService {

    private CartDAO cartDAO;

    @Context
    private ServletContext context;

    @PostConstruct
    public void init() {
        cartDAO = new CartDAO(context);
    }

    @GET
    @Path("/getCartByUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCartByUser(@PathParam("username") String username) {
        Cart cart = cartDAO.getCartByUsername(username);
        if (cart == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Cart not found for user: " + username).build();
        }
        return Response.ok(cart).build();
    }


    @POST
    @Path("/addToCart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(Cart cart, Product product) {
		return null;

        
    }


    @DELETE
    @Path("/removeFromCart/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFromCart(@PathParam("username") String username, Product product) {
		return null;
        
    }


    @PUT
    @Path("/updateQuantity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateQuantity(Cart cart) {
		return null;
        
        
    }
    
    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get existing session
        if (session != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                return Response.ok(cart).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                               .entity("No user in session").build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("No session found").build();
        }
    }

}

