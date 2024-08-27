package restService;

import model.Product;
import model.User;
import model.Cart;
import dao.CartDAO;
import dao.ProductDAO;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.json.JsonObject;

@Path("Cart")
public class CartService {

    private CartDAO cartDAO;
    private ProductDAO productDAO;

    @Context
    private ServletContext context;

    @PostConstruct
    public void init() {
        cartDAO = new CartDAO(context);
        productDAO = new ProductDAO(context);
    }

    @GET
    @Path("/getCart")
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getCartByUser(@Context HttpServletRequest request) {
        System.out.println("get cart called");
        Cart cart;
        User user = getSessionUser(request);
        if (user != null) {
            cart = cartDAO.getCartByUsername(user.getUsername());
        } else {
            // get session cart
            System.out.println("get cart for not logged in user");
            cart = getSessionCart(request);
        }
        return cart;
    }

    @POST
    @Path("/addToCart")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response addToCart(@Context HttpServletRequest request, Product product) {
        System.out.println("addtocart service called");
        
        
        // get session cart
        Cart cart = getSessionCart(request);

        if(cart.getCartItemByProductId(product.getId())!=null && product.getStock( ) <= cart.getCartItemByProductId(product.getId()).getQuantity()) {
            	return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Insufficient stock for the product")
                        .build();
         }
        
        User user = getSessionUser(request);
        cart.add(product);
        if (user != null) {
            cartDAO.addToCart(user.getUsername(), product);
            System.out.println("success add to cart -from service");
            return Response.ok("Product added to user cart").build();
        } 
            
        
        return Response.ok("Product added to session cart").build();
    }

    @PUT
    @Path("/removeFromCart")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeFromCart(@Context HttpServletRequest request, int productid) {
        Cart cart;
        // get session cart
        cart = getSessionCart(request);
        cart.remove(productid);
        
        User user = getSessionUser(request);
        if (user != null) {
            cart = cartDAO.getCartByUsername(user.getUsername());
            cartDAO.removeFromCart(user.getUsername(), productid);
            return Response.ok("Product removed from cart").build();
        } 
        
        return Response.ok("Product removed from session cart").build();
    }

    @PUT
    @Path("/updateQuantity")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateQuantity(@Context HttpServletRequest request, JsonObject jsonRequest) {
        try {
            int productId = jsonRequest.getInt("productId");
            String quantityStr = jsonRequest.getString("quantity");
            int quantity = Integer.parseInt(quantityStr);

            Cart cart;
            User user = getSessionUser(request);
            cart = getSessionCart(request);
            
            if(cart.getCartItemByProductId(productId)!=null && productDAO.getProductById(productId).getStock( ) < quantity) {
            	return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Insufficient stock for the product")
                        .build();
         }
            
            
            cart.updateQuantity(productId, quantity);
            
            if (user != null) {
                cart = cartDAO.getCartByUsername(user.getUsername());
                cartDAO.updateQuantity(user.getUsername(), productId, quantity);
                return Response.ok("User Cart updated successfully").build();
            }
                
            return Response.ok("Product quantity updated from session cart").build();
            
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("Error processing the request").build();
        }
    }

    public Cart getSessionCart(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get existing session
        if(session == null) {
            System.out.println("null session (getSessionCart)");
            session = request.getSession(true); // Create a new session if none exists
        }
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            System.out.println("active session, null cart - creating new cart");
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        return cart;
    }

    public User getSessionUser(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get existing session
        if (session == null) {
            session = request.getSession(true); // Create a new session if none exists
            System.out.println("No session found, creating a new one");
        }

        User user = (User) session.getAttribute("user");
        if (user == null) {
            System.out.println("No user found in session");
            Cart sessionCart = (Cart) session.getAttribute("cart");
            if (sessionCart == null) {
                // Create a new session cart if one doesn't exist
                sessionCart = new Cart();
                session.setAttribute("cart", sessionCart);
            }
            return null; // Return null if no user is logged in
        }
        return user;
    }
}
