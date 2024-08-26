$(document).ready(function() {
    // Get the item ID from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const itemId = urlParams.get('id');
	const user = '<%= session.getAttribute("user") %>'; // Fetch the username from the session
    
    if (itemId) {
        // Fetch the product data from the server
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Products/getProductById/' + itemId,
            method: 'GET',
            success: function(product) {
                // Dynamically update the page with the product data
                $('#item-name').text(product.title);
                $('#item-description').text(product.description);
                $('#item-price').text("$" + product.price);
                $('#inv-cnt').text(product.stock + " left in stock.");
                $('#item-image').attr('src', "product-images/" + product.id + ".jpg");
                
                // Handle the Add to Cart button click
                $('#add-to-cart-btn').on('click', function() {
                    // Fetch the current user session
                    $.ajax({
                        url: 'http://localhost:8080/unisphereREST/rest/Cart/addToCart',
                        method: 'POST',
                        contentType: 'application/json',
                        data: JSON.stringify(product),
                        success: function(response) {
                            $('#cart-message').text("Added to Cart!");
                            setTimeout(function() {
                                $('#cart-message').text(""); // Clear the message after 2 seconds
                            }, 2000); // 2000 milliseconds = 2 seconds
                        },
                        error: function(err) {
                            $('#cart-message').text("Cart quantity must not exceed inventory count!");
                            setTimeout(function() {
                                $('#cart-message').text(""); // Clear the message after 2 seconds
                            }, 2000); // 2000 milliseconds = 2 seconds
                            console.error('Error adding to cart', err);
                        }
                    });
                });
                
                // Handle the Wishlist button click
                $('.wishlistButton').on('click', function() {
                    // Add the product to the wishlist
					$.ajax({
					    url: 'http://localhost:8080/unisphereREST/rest/Wishlist/AddToWishlist/' + user.username,
					    method: 'POST',
					    contentType: 'application/json',
					    data: JSON.stringify(product),
					    success: function(response) {
					        $('#wishlist-message').text("Added to Wishlist!");
					    },
					    error: function(err) {
					        if (err.status === 409) {
					            $('#wishlist-message').text("Item is already in wishlist");
					        } else if (err.status === 401){
					            $('#wishlist-message').text("Sign in to access wishlist.");
					        }
					        console.error('Error adding to wishlist:', err);
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
