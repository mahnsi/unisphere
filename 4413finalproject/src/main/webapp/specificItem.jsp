<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Specific Item Page</title>
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" href="specificItem.css">
</head>

<body>
<%@ include file="header/header.jsp" %>

<main>
        <div class="item-page">
            <div class="item-image">Item Image</div>
            <div class="item-details">
                <h1>Item Name</h1>
                <p>Item details</p>
                <div class="rating">★★★★★ 10</div>
                <div>
                <select>
                    <option value="size">Select a size</option>
                    <!-- Add more options as needed -->
                </select>                  
                </div>
                <div>
                <button id = "add-to-cart-btn" class="addToBag">Add to Bag</button>            
                <button class="wishlistButton">❤</button>
            </div>
            </div>
        </div>
    </main>
    
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
$(document).ready(function() {
    $('.add-to-cart-btn').click(function() {
        // Get the product ID from the button's data attribute
        var productId = $(this).data('product-id');
        
        // Assume you have the username and product details, you can retrieve them from the page or form
        var username = 'exampleUser'; // Replace with actual username retrieval logic
        var product = {
            id: productId,
            // Include other product details if necessary
        };

        // Make an AJAX POST request to add the product to the cart
        $.ajax({
            url: '/Cart/addToCart',  // Replace with the correct path to your REST service
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(product),
            dataType: 'json',
            success: function(response) {
                // Handle successful response (e.g., update UI, show confirmation message)
                alert('Product added to cart successfully!');
            },
            error: function(xhr, status, error) {
                // Handle error response
                alert('Error adding product to cart: ' + xhr.responseText);
            }
        });
    });
});

</script>
</body>
</html>