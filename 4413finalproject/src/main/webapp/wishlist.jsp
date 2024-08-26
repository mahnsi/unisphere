<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.html" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wishlist</title>
    <link rel="stylesheet" href="style/style.css">
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">
</head>

<body>

<main>
    <section class="cart">
        <h2>My Wishlist</h2>
        <div class="cart-items">
            <!-- Wishlist items will be dynamically loaded here -->
        </div>
        <button class="continue-shopping">Continue Shopping</button>
    </section>
</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="script/wishlist.js"></script> <!-- Include the wishlist.js file -->

</body>
</html>
