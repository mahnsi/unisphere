<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    
</head>
<body>
    <%@ include file="header.html" %>
    
    <h1 class="page-title">Welcome, <span id = usernameDisplay></span></h1>
    

    <div class="container">
        <!-- Navigation Panel -->
        <div class="nav-panel">
            <ul>
                <li><a id="nav-info" onclick="showSection('info', this)">My Info</a></li>
                <li><a id="nav-orders" onclick="showSection('orders', this)">Order History</a></li>
                <li><a id="nav-payments" onclick="showSection('payments', this)">My Payments</a></li>
                <li><a id="nav-address" onclick="showSection('addresses', this)">My Addresses</a></li>
            </ul>
        </div>

        <!-- content panel -->
        <!-- initially the content is hidden and the js will show one at a time based on events -->
        <div class="content-panel">
            <div id="info" class="section" style="display: none;">
                <h2>My Info</h2>
                <p id ="userinfo">change firstname lastname username here</p>
                <button id="logoutButton">Log Out</button>
            </div>

            <div id="orders" class="section" style="display: none;">
			    <h2>Order History</h2>
			    <ul class="order-list">
			        <c:forEach var="order" items="${orders}">
			            <li onclick="showOrderDetails('${order.id}')">Order #${order.id}</li>
			        </c:forEach>
			    </ul>
			</div>


            <div id="order-details" class="section" style="display: none;">
                <h2>Order Details</h2>
                <p id="order-info"></p>
                <button onclick="backToOrders()">Back to Order History</button>
            </div>

            <div id="payments" class="section" style="display: none;">
                <h2>My Payments</h2>
                <p>add payment button to add payment method</p>
            </div>
            <div id="addresses" class="section" style="display: none;">
                <h2>Addresses</h2>
                <p>add address button to add address</p>
            </div>
        </div>
    </div>

    <script>
    //also need jquery for updating user info and payments
    
        //function get URL query parameters for order details
        function getQueryParams() {
            const params = new URLSearchParams(window.location.search);
            return {
                order: params.get('order')
            };
        }

        // show different sections based on the left panel selection
        function showSection(section, element) {
            // Hide all sections
            document.querySelectorAll('.section').forEach(section => {
                section.style.display = 'none';
            });

            // Show the selected section
            document.getElementById(section).style.display = 'block';

            // Remove 'active' class from all nav items
            document.querySelectorAll('.nav-panel a').forEach(navItem => {
                navItem.classList.remove('active');
            });

            // Add 'active' class to the clicked nav item
            element.classList.add('active');
        }

        // Function to show order details
        function showOrderDetails(orderId) {
            // Hide the order list
            document.getElementById('orders').style.display = 'none';

            // Show the order details section
            document.getElementById('order-details').style.display = 'block';

            // Set the order details
            $.ajax({
                url: `http://localhost:8080/unisphereREST/rest/orders/${orderId}`, // Replace with your endpoint
                method: 'GET',
                dataType: 'json',
                success: function(order) {
                    // Populate the order-info section with data
                    let orderDetails = `Details for Order #${order.id}:
                    \n- Date created: ${order.dateCreated}
                    \n- Items:`;
                    
                    order.items.forEach(function(item) {
                        orderDetails += `\n- Item: ${item.productName}, Quantity: ${item.quantity}`;
                    });
                    
                    orderDetails += `\n- Total: $${order.total}`;
                    
                    document.getElementById('order-info').innerText = orderDetails;
                },
                error: function() {
                    alert('Error fetching order details');
                }
            });
        }

        // Function to go back to order history
        function backToOrders() {
            document.getElementById('order-details').style.display = 'none';
            document.getElementById('orders').style.display = 'block';
        }

        //on load check the url to see if it wants to ope order details pg
        window.onload = function () {
            const params = getQueryParams();

            if (params.order) {
                // If there is an order in the query param, show the order details directly
                showSection('orders', document.getElementById('nav-orders'));
                showOrderDetails(`${params.order}`);
            } else {
                // Otherwise, default to showing the orders section
                showSection('info', document.getElementById('nav-orders'));
            }
        };
    </script>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
		$(document).ready(function() {
		    // Make the AJAX request to the backend to get the session data
		    $.ajax({
		        url: 'http://localhost:8080/unisphereREST/rest/Auth/session', // Replace with your session endpoint URL
		        method: 'GET',
		        dataType: 'json',
		        xhrFields: {
		            withCredentials: true // Include cookies in the request
		        },
		        success: function(response) {
		            // Handle the successful response
		            console.log("User data from session:", response);
		
		            // Example: Display the username on the page
		            $('#usernameDisplay').text(response.username);
		            
		            //display all the other user info like addresses and payments
		        },
		        error: function(jqXHR, textStatus, errorThrown) {
		            // Handle errors (user is not signed in or session expired)
		            console.error("Error fetching session data:", textStatus, errorThrown);
		            window.location.href = 'signin.jsp';
		        }
		    });
		
		    // Handle logout button click
		    $('#logoutButton').click(function() {
		        $.ajax({
		            url: 'http://localhost:8080/unisphereREST/rest/Auth/logout', 
		            method: 'POST',
		            xhrFields: {
		                withCredentials: true // Include cookies in the request
		            },
		            success: function(response) {
		                // Redirect to home page after successful logout
		                window.location.href = 'home.jsp';
		            },
		            error: function(jqXHR, textStatus, errorThrown) {
		                // Handle errors
		                console.error("Error logging out:", textStatus, errorThrown);
		                
		            }
		        });
		    });
		});
</script>


</body>

</html>
