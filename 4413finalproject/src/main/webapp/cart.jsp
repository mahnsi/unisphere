<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="cart_and_wishlist.css">
    
</head>

<body>
<%@ include file="header/header.jsp" %>

 <main>
        <section class="cart">
            <h2>My Cart</h2>
            <div class="cart-items">
                <div class="product">
				    <div class="product-image"></div>
				    <div class="product-details">Product Details</div>
				    <div class="product-actions">
				        <div class="product-price">$19.99</div>
				        <div class="product-quantity">
				            <label for="quantity">Qty:</label>
				            <input type="number" id="quantity" name="quantity" value="1" min="1">
				            <button class="update-button">Update</button>
				        </div>
				        <button class="remove-button">Remove</button>
				    </div>
				</div>
            </div>
            
		    
            <button class="continue-shopping">Continue Shopping</button>
        </section>
        
        <section class="order-summary">
            <h3>Order Summary</h3>
            <p>Shipping:</p>
            <p>Tax:</p>
            <p>Discount:</p>
            <p><strong>Estimated Total $0.00</strong></p>
            <button class="checkout-button">Checkout</button>
        </section>
    </main>

</body>
</html>