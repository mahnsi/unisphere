package restService;

import model.User;
import model.Address;
import model.Cart;
import model.Payment;
import dao.UserDAO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("Users")
public class UserService {

    private UserDAO userDao;

    @Context
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        userDao = new UserDAO(servletContext);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @GET
    @Path("/searchByUsername/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserbyUsername(@PathParam("username") String uname) {
        return userDao.getUserByUsername(uname);
    }
    
    @GET
    @Path("/getAllUserInfo/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFullUserbyUsername(@PathParam("username") String uname) {
    	System.out.println("userservice getAllUserInfo called ");
        User user = userDao.getFullUserByUsername(uname);
        return Response.ok(user).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user, @Context HttpServletRequest request) {
        // Check if the user with the same username or email already exists
    	System.out.println("rest createUser");
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT)
                           .entity("Username already exists").build();
        }

        // Save the new user
        userDao.setCart(user, getSessionCart(request));
        
        boolean isCreated = userDao.insert(user);
        if (isCreated) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("User could not be created").build();
        }
    }
    
    @PUT
    @Path("/updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(String jsonInput) {
        JSONObject json = new JSONObject(jsonInput);
        String firstName = json.getString("firstName");
        String lastName = json.getString("lastName");
        String uname = json.getString("username");

        User user = userDao.getFullUserByUsername(uname);
            if (user != null) {
                user.setFirstName(firstName);
                user.setLastName(lastName);

                userDao.update(uname, user);

                return Response.ok(user).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity("No user in session").build();
            }
        }

    @PUT
    @Path("/updateAddress/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAddress(@PathParam("username") String username, Address updatedAddress, @Context HttpServletRequest request) {
        // Check if the user exists
        User user = userDao.getFullUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
        }

        // Update the user's address
        user.setAddress(updatedAddress);
        userDao.updateAddress(user, updatedAddress);

        // Return a success response with the updated address information
        return Response.ok(updatedAddress).build();
    }

    @PUT
    @Path("/updatePayment/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePayment(@PathParam("username") String username, Payment updatedPayment, @Context HttpServletRequest request) {
        // Check if the user exists
        User user = userDao.getFullUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
        }

        // Update the user's payment information
        user.setPayment(updatedPayment);
        userDao.updatePayment(user, updatedPayment);


        // Return a success response with the updated payment information
        return Response.ok(updatedPayment).build();
    }
    
    @DELETE
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("username") String username) {
        // Check if the user exists
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
        }

        // Delete the user
        boolean isDeleted = userDao.delete(username);
        if (isDeleted) {
            return Response.ok("{\"message\":\"User deleted successfully\"}").build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"message\":\"User could not be deleted\"}").build();
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

    
}
