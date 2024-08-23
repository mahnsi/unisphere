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
                <h1 id = "item-name" >Item Name</h1>
                <p id = "item-description">Item details</p>
                <div>    
                <p id="item-price">$0.00</p>             
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
	    // Get the item ID from the URL
	    const urlParams = new URLSearchParams(window.location.search);
	    const itemId = urlParams.get('id');
	    
	    if (itemId) {
	        // Fetch the product data from the server
	        $.ajax({
	            url: 'http://localhost:8080/unisphereREST/rest/Products/getProductById/' + itemId,
	            method: 'GET',
	            success: function(data) {
	                // Update the page with the product data
	                $('#item-name').text(data.title);
	                $('#item-description').text(data.description);
	                $('#item-price').text("$" + data.price);
	                $('#item-image').attr('src', "product-images/" + data.id + ".jpg");
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