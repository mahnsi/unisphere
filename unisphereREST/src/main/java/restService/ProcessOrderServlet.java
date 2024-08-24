package restService;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/processOrder")
public class ProcessOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String ORDER_INSERT_SQL = "INSERT INTO ORDERS (id, total, created_by, offer_id) VALUES (?, ?, ?, ?)";
    private static final String ORDERED_ITEM_INSERT_SQL = "INSERT INTO ORDERED_ITEM (order_id, product_id, quantity) VALUES (?, ?, ?)";
    private ServletContext context;

    @Override
    public void init() throws ServletException {
        context = getServletContext();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String createdBy = request.getParameter("createdBy");
        String offerId = request.getParameter("offerId");
        String[] productIds = request.getParameterValues("productIds");
        String[] quantities = request.getParameterValues("quantities");
        String total = request.getParameter("total");

        String orderNumber = generateOrderNumber();

        try (Connection conn = getConnection()) {
            // Start transaction
            conn.setAutoCommit(false);

            // Insert order into ORDERS table
            try (PreparedStatement stmt = conn.prepareStatement(ORDER_INSERT_SQL)) {
                stmt.setString(1, orderNumber);
                stmt.setString(2, total);
                stmt.setString(3, createdBy);
                if (offerId != null && !offerId.isEmpty()) {
                    stmt.setInt(4, Integer.parseInt(offerId));
                } else {
                    stmt.setNull(4, java.sql.Types.INTEGER);
                }
                stmt.executeUpdate();
            }

            // Insert items into ORDERED_ITEM table
            try (PreparedStatement stmt = conn.prepareStatement(ORDERED_ITEM_INSERT_SQL)) {
                for (int i = 0; i < productIds.length; i++) {
                    stmt.setString(1, orderNumber);
                    stmt.setString(2, productIds[i]);
                    stmt.setInt(3, Integer.parseInt(quantities[i]));
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            // Commit transaction
            conn.commit();

        } catch (SQLException e) {
            // Handle transaction rollback
            try (Connection conn = getConnection()) {
                conn.rollback();
            } catch (SQLException rollbackEx) {
                throw new ServletException("Transaction rollback failed", rollbackEx);
            }
            throw new ServletException("Database error when processing order", e);
        }

        // Redirect to order confirmation page with the order number
        response.setContentType("application/json");
        response.getWriter().write("{\"orderNumber\":\"" + orderNumber + "\"}");

    }

    private String generateOrderNumber() {
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000000);
        return String.format("%08d", num); // 8-digit order number
    }

    private Connection getConnection() throws SQLException {
        String path = context.getRealPath("/UniSphere.db");
        return DriverManager.getConnection("jdbc:sqlite:" + path);
    }
}
