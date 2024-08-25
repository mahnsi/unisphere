package restService;
import model.*;
import dao.*;

import java.util.List;

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

    @Context
    private ServletContext servletContext;

    @PostConstruct
    public void init() {
        orderDao = new OrderDAO(servletContext);
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
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
		orderDao.insert(order);
		return null;
    }
	

}
