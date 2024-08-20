<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="cart_wishlist_checkout.css">
    
</head>

<body>
<%@ include file="header/header.jsp" %>

 <main class = "orderconfirmation">
          <h2>Thank You for You Order!</h2>
          <h2>Order #********</h2>
          
          <div class="button-group">
	        <a href="profile.jsp?order=1"><button class="confirmation-button">View Order Details</button></a>
	        <button class="confirmation-button">Continue Shopping</button>
   		 </div>
 </main>

</body>
</html>