package restService;

import model.User;
import dao.UserDAO;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public List<User> getAllUsers(){

		List<User> ulist = userDao.getAllUsers();
		return ulist;
	}


	@GET
	@Path("/searchByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserbyUsername(@PathParam("username") String uname){

		User user = userDao.getUserByUsername(uname);
		return user;


	}
	
	@GET
	@Path("/getAllUserInfo/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getFullUserbyUsername(@PathParam("username") String uname){

		User user = userDao.getFullUserByUsername(uname);
		return user;


	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {//json given in request is automatically converted to User
        // Check if the user with the same username or email already exists
        User existingUser = userDao.getUserByUsername(user.getUsername());
        if (existingUser != null) {
            return Response.status(Response.Status.CONFLICT)//sends a 409
                           .entity("Username already exists").build();
        }

        // Save the new user
        boolean isCreated = userDao.insert(user);
        if (isCreated) {
            return Response.status(Response.Status.CREATED).entity(user).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                           .entity("User could not be created").build();
        }
	}

    @PUT
    @Path("/update/{username}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("username") String oldUsername, User updatedUser, @Context HttpServletRequest request) {
        // Check if the user exists
        User existingUser = userDao.getUserByUsername(oldUsername);
        if (existingUser == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"message\":\"User not found\"}").build();
        }

        // Update the user information, including the username
        userDao.update(oldUsername, updatedUser);

        // Update the session with the new user details
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.setAttribute("user", updatedUser);
        }

        // Return a success response with the updated user information
        return Response.ok(updatedUser).build();
    }
}
