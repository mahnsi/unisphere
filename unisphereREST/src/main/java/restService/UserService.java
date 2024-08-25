package restService;

import model.User;
import dao.UserDAO;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
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
