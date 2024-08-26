<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">

</head>
<body>
<%@ include file="header.html" %>

<main class="orderconfirmation">
    <h2>Thank You for Your Order!</h2>
    <h2>Order #<%= request.getParameter("orderNumber") %></h2>
    
    <div class="button-group">
        <a href="profile.jsp?order=1"><button class="confirmation-button">View Order Details</button></a>
        <a href="home.jsp"><button class="confirmation-button">Continue Shopping</button></a>
    </div>
</main>

</body>
</html>
