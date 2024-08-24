package restService;
import model.*;
import dao.*;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import org.json.JSONObject;

@Path("Auth")
public class AuthService {

	private UserDAO userDao;

    @Context
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        userDao = new UserDAO(servletContext);
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String jsonInput, @Context HttpServletRequest request) {
        // Parse JSON input manually
        JSONObject json = new JSONObject(jsonInput);
        String username = json.getString("username");
        String password = json.getString("password");

        User user = userDao.getUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED)
                           .entity("Invalid username or password").build();
        }
    }


    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Return a JSON object with a success message
        return Response.ok("{\"message\":\"Logged out successfully\"}").build();
    }

    
    @GET
    @Path("/session")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSessionUser(@Context HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Get existing session
        if (session != null) {
            User user = (User) session.getAttribute("user");
            if (user != null) {
                return Response.ok(user).build();
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