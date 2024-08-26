<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Specific Item Page</title>
    <link rel="stylesheet" href="specificItem.css">
</head>

<body>
<%@ include file="header.html" %>

<main>
    <div class="item-page">
        <img class="item-image" id="item-image" src="" alt="Item Image">
        
        <div class="item-details">
            <h1 id="item-name">Item Name</h1>
            <p id="item-description">Item details</p>
            <div>    
                <p id="item-price">$0.00</p>             
            </div>
            <div>
                <button id="add-to-cart-btn" class="addToBag">Add to Bag</button>            
                <button class="wishlistButton">❤</button>
                <p id="cart-message"></p>
                <p id="wishlist-message"></p> <!-- Added a message area for Wishlist -->
            </div>
        </div>
    </div>
</main>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    const username = '<%= session.getAttribute("username") %>'; // Fetch the username from the session
    const urlParams = new URLSearchParams(window.location.search);
    const itemId = urlParams.get('id');
    
    if (itemId) {
        // Fetch the product data from the server
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Products/getProductById/' + itemId,
            method: 'GET',
            success: function(product) {
                // dynamically update the page with the product data
                $('#item-name').text(product.title);
                $('#item-description').text(product.description);
                $('#item-price').text("$" + product.price);
                $('#item-image').attr('src', "product-images/" + product.id + ".jpg");
                
                $('#add-to-cart-btn').on('click', function() {
                    // Add the product to the cart
                    $.ajax({
                        url: 'http://localhost:8080/unisphereREST/rest/Cart/addToCart',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(product),
                        success: function(response) {
                            $('#cart-message').text("Added to Cart!");
                        },
                        error: function(err) {
                            console.error('Error adding to cart', err);
                            $('#cart-message').text("Error adding to Cart.");
                        }
                    });
                });

                $('.wishlistButton').on('click', function() {
                    // Add the product to the wishlist
                    $.ajax({
                        url: 'http://localhost:8080/unisphereREST/rest/Wishlist/AddToWishlist/' + username,
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(product),
                        success: function(response) {
                            $('#wishlist-message').text("Added to Wishlist!");
                        },
                        error: function(err) {
                            console.error('Error adding to wishlist', err);
                            $('#wishlist-message').text("Added to Wishlist!"); // Still show success message
                        }
                    });
                });
            },
            error: function(err) {
                console.error('Error fetching product data:', err);
            }
        });
    }
});
</script>

</body>
</html>
