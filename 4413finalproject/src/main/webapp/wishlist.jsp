<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wishlist</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="cart_wishlist_checkout.css">
    
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>

<section class="cart">
            <h2>My Wishlist</h2>
            <div class="cart-items">
                <div class="product">
				    <div class="product-image"></div>
				    <div class="product-details">Product Details</div>
				    <div class="product-actions">
				        <div class="product-price">$19.99</div>
				        <button class="remove-button">Remove</button>
				    </div>
				</div>
            </div>
            
		    
            <button class="continue-shopping">Continue Shopping</button>
        </section>
     
</main>

</body>
</html>