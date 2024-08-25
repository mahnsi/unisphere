package restService;

import model.Product;

import model.User;
import model.Cart;
import dao.CartDAO;

import java.util.Map;
import javax.json.*;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
    public Cart getCartByUser(@PathParam("username") String username) {
    	System.out.println("REST getcartbyuser called");
        Cart cart = cartDAO.getCartByUsername(username);
        if (cart == null) {
        	System.out.println("null cart");
            return null;
        }
        return cart;
    }

    @POST
    @Path("/addToCart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToCart(@Context HttpServletRequest request, Product product) {
    	System.out.println("addtocart service called");
        User user = getSessionUser(request);
        if (user != null) {
            cartDAO.addToCart(user.getUsername(), product);
            System.out.println("success add to cart -from service");
            return Response.ok("Product added to cart").build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("User not logged in").build();
        }
    }


    @PUT
    @Path("/removeFromCart/{username}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeFromCart(@PathParam("username") String username, int productid) {
        Cart cart = cartDAO.getCartByUsername(username);
        if (cart != null) {
            cartDAO.removeFromCart(username, productid);
            //System.out.println("remove from cart complete -cartservie");
            return Response.ok("Product removed from cart").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                           .entity("Cart not found for user: " + username).build();
        }
    }

    @PUT
    @Path("/updateQuantity/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateQuantity(@PathParam("username") String username, JsonObject jsonRequest) {
        try {
            // Extract values from the JSON object
            int productId = jsonRequest.getInt("productId");
            String quantityStr = jsonRequest.getString("quantity");
            int quantity = Integer.parseInt(quantityStr);

            // Retrieve and update the cart
            Cart cart = cartDAO.getCartByUsername(username);
            if (cart != null) {
                // Logic to update quantity of items in the cart
                cartDAO.updateQuantity(username, productId, quantity);
                return Response.ok("Cart updated successfully").build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                               .entity("Invalid cart data").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error processing the request").build();
        }
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
                               .entity("No cart found in session").build();
            }
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("No session found").build();
        }
    }

    public User getSessionUser(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get existing session
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return user;
            } else {
                // No user in session
                Cart sessionCart = (Cart) session.getAttribute("cart");
                if (sessionCart == null) {
                    // Create a new session cart if one doesn't exist
                    sessionCart = new Cart();
                    session.setAttribute("cart", sessionCart);
                }
            }
        } else {
            // No session found, return null
            return null;
        }
        return null;
    }
}
