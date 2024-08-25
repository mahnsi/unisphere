<!DOCTYPE html>
<%@ include file="header.html" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Cart</title>
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
    <main>
        <section class="cart">
            <h2>My Cart</h2>
            <div class="cart-items">
                <!-- Cart items will be appended here by jQuery -->
            </div>
            <button class="continue-shopping">Continue Shopping</button>
        </section>
        
        <section class="order-summary">
            <h3>Order Summary</h3>
            <p>Shipping: <span id="shipping-cost">Shipping Calculated at Checkout</span></p>
            <p>Discount: <span id="discount">Offers applied at checkout</span></p>
            <p><strong>Subtotal: <span id="estimated-total">$0.00</span></strong></p>
            <a href="checkout.jsp"><button class="checkout-button">Checkout</button></a>
        </section>
    </main>

    <script src= "script/cart.js"></script>
</body>
</html>
