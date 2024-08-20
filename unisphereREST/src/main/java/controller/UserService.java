package controller;
import model.*;
import dao.*;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("Users")
public class UserService {
	
	//private BookDAOImpl bookDAO = new BookDAOImpl("/Books.db");
	UserDAO userDao = new UserDAO(null);//servletcontext

	
	public UserService() {
		
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
	

}
