$(document).ready(function() {
    fetchWishlistItems();

    // Continue shopping button redirects to the catalogue page
    $('.continue-shopping').on('click', function() {
        window.location.href = "catalogue.jsp"; // Redirect to the catalogue page
    });

    // Function to fetch and display wishlist items
    function fetchWishlistItems() {
        const user = '<%= session.getAttribute("user") %>'; // Fetch the username from the session

        $.ajax({
            url: "http://localhost:8080/unisphereREST/rest/Wishlist/getWishlistByUsername/" + user.username,
            method: 'GET',
            success: function(wishlist) {
                let wishlistItems = wishlist.items;

                // Clear the current list to avoid duplicates
                $('.cart-items').empty();

                // Loop through the wishlist items
                $.each(wishlistItems, function(index, product) {
                    // Generate HTML for each item
                    let productHTML =
                        '<div class="product" data-product-id="' + product.id + '">' +
                            '<div class="product-image">' +
                                '<img src="product-images/' + product.id + '.jpg" alt="' + product.title + '">' +
                            '</div>' +
                            '<div class="product-details">' +
                                '<p>' + product.title + '</p>' +
                                '<p>' + product.description + '</p>' +
                            '</div>' +
                            '<div class="product-actions">' +
                                '<div class="product-price">$' + product.price.toFixed(2) + '</div>' +
                                '<button class="remove-button" data-product-id="' + product.id + '">Remove</button>' +
                            '</div>' +
                        '</div>';

                    // Append the generated HTML to the cart-items div
                    $('.cart-items').append(productHTML);
                });

                // Attach event listeners for remove buttons
                $('.cart-items').on('click', '.remove-button', function() {
                    let productId = $(this).data('product-id'); // Get product ID
                    removeWishlistItem(productId); 
                });
            },
            error: function(error) {
                console.error('Error fetching wishlist items:', error);
            }
        });
    }

    // Function to remove an item from the wishlist
    function removeWishlistItem(productId) {
        const user = '<%= session.getAttribute("user") %>'; // Fetch the username from the session

        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Wishlist/RemoveFromWishlist/' + user.username + '/' + productId,
            method: 'DELETE',
            success: function(response) {
                console.log("Product removed from wishlist: " + productId);
                // Remove the product from the DOM
                $('.product[data-product-id="' + productId + '"]').remove();
            },
            error: function(error) {
                console.error('Error removing wishlist item:', error);
            }
        });
    }
});
