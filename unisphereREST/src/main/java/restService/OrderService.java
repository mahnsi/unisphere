package restService;

import model.*;
import dao.*;

import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
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

@Path("Orders")
public class OrderService {
	
	private OrderDAO orderDao;
	private UserDAO userDao;

	@Context
	private ServletContext servletContext;

	@PostConstruct
	public void init() {
		orderDao = new OrderDAO(servletContext);
		userDao  = new UserDAO (servletContext);
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getAllOrders(){
		List<Order> olist = orderDao.getAllOrders();
		return olist;
	}
	
	@GET
	@Path("/getOrdersByUsername/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Order> getOrderbyOrdername(@PathParam("username") String uname){
		List<Order> olist = orderDao.getOrdersByUsername(uname);
		return olist;
	}
	
	@GET
	@Path("/getOrderById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Order getOrderbyId(@PathParam("id") int id){
		
		Order order = orderDao.getOrderById(id);
		return order;
		
		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createOrder(Order order) {
		System.out.println("rest createOrder called");
		// Generate a random order number
		int orderNumber = new Random().nextInt(999999999);

		order.setId(orderNumber);

		// Save the order with the generated order number
		orderDao.insert(order);
		userDao.clearCart(order.getCreatedBy());
		// Return the order number in the response
		return Response.ok(order).build();
	}
}
