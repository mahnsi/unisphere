<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.html" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Page</title>
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">
    
</head>

<body>

 <main>
        <section class="cart">
            <h2>Delivery - N item </h2>
            <p>Standard Shipping Delivers in 1-4 business days</p>
            
            <div class = "cart-item">
            	<div class="product">
				    <div class="product-image"></div>
				    <div class="product-details">Product Details</div>
				    <div class="product-actions">
				        <div class="product-price">$19.99</div>
				        <div class="product-quantity">Qty:</div>
        			</div>
        		</div>
        	</div>
        	
        	<br>
        	
        	<form class = "shipping-address">
        		<h2>Shipping Address</h2>       		 
			      	<div class="input-row">
				        <div class="input-group">
				            <label for="first-name">First Name</label>
				            <input type="text" id="first-name" name="first-name">
				        </div>
				        
				        <div class="input-group">
				            <label for="last-name">Last Name</label>
				            <input type="text" id="last-name" name="last-name">
				        </div>
				    </div>
				   
				    <div class="input-group full-width">
				        <label for="street-address">Street Address</label>
				        <input type="text" id="street-address" name="street-address">
				    </div>
					<br>
				    <div class="input-group full-width">
				        <label for="apartment">Apt/Suite/Buzzer/Etc.</label>
				        <input type="text" id="apartment" name="apartment">
				    </div>
					<br>
				    <div class="input-row">
				        <div class="input-group">
				            <label for="city">City</label>
				            <input type="text" id="city" name="city">
				        </div>
				        
				        <div class="input-group">
				            <label for="province">Province</label>
				            <input type="text" id="province" name="province">
				        </div>
			    	</div>
			    
        	</form>
        </section>
        <section class="order-summary">
            <h3>Order Summary</h3>
            <p>Shipping:</p>
            <p>Tax:</p>
            <p>Discount:</p>
            <p><strong>Estimated Total $0.00</strong></p>
            
            <a href="orderconfirmation.jsp"><button class="checkout-button">Proceed to Payment</button></a>
        </section>
    </main>
    <script>
	/* JS or jQuery.. to do ajax call */
	
	 $(document).ready(function() {
        $(".shipping-address").submit(function(event) {
            event.preventDefault(); // Prevent default form submission
            processOrder();
        });

        function processOrder() {
            var formData = {
                firstName: $("#first-name").val(),
                lastName: $("#last-name").val(),
                streetAddress: $("#street-address").val(),
                apartment: $("#apartment").val(),
                city: $("#city").val(),
                province: $("#province").val()
            };

            $.ajax({
                type: "POST",
                url: "processOrder",
                data: formData,
                success: function(response) {
                    window.location.href = "orderconfirmation.jsp?orderNumber=" + response.orderNumber;
                }

                error: function() {
                    alert("Error processing your order. Please try again.");
                }
            });
        }
    });
    </script>
</body>
</html>