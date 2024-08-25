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

    <script>
    $(document).ready(function() {
        // Fetch session data to get the username
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Auth/session',
            method: 'GET',
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            success: function(response) {
                console.log("User data from session:", response);
                let username = response.username;

                // Use the username to fetch cart items
                fetchCartItems(username);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching session data:", textStatus, errorThrown);
            }
        });

        // Function to fetch cart items for a specific user
        function fetchCartItems(username) {
            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Cart/getCartByUser/" + username,
                method: 'GET',
                success: function(cart) {
                    let cartItems = cart.items;
                    let total = cart.totalPrice;

                    // Loop through the cart items
                    $.each(cartItems, function(index, item) {
                        let product = item.product;
                        let quantity = item.quantity;

                        // Generate HTML for each item
                        let productHTML =
                            '<div class="product" data-product-id="' + product.id + '">' +
                                '<div class="product-image">' +
                                    '<img src="product-images/' + product.id + '.jpg" alt="' + product.title + '">' +
                                '</div>' +
                                '<div class="product-details">' +
                                    '<p>' + product.title + '</p>' +
                                '</div>' +
                                '<div class="product-actions">' +
                                    '<div class="product-price">$' + product.price.toFixed(2) + '</div>' +
                                    '<div class="product-quantity">' +
                                        '<label for="quantity-' + index + '">Qty:</label>' +
                                        '<input type="number" id="quantity-' + index + '" name="quantity" value="' + quantity + '" min="1">' +
                                        '<button class="update-button" data-product-id="' + product.id + '">Update</button>' +
                                    '</div>' +
                                    '<button class="remove-button" data-product-id="' + product.id + '">Remove</button>' +
                                '</div>' +
                            '</div>';

                        // Append the generated HTML to the cart-items div
                        $('.cart-items').append(productHTML);
                    });

                    // Update the estimated total in the order summary
                    $('#estimated-total').text('$' + total.toFixed(2));

                    // Attach event listeners for remove and update buttons
                    $('.cart-items').on('click', '.remove-button', function() {
                        let productId = $(this).data('product-id'); // Get product ID
                        removeCartItem(username, productId); // Pass username and product ID to removeCartItem
                    });

                    $('.cart-items').on('click', '.update-button', function() {
                        let productId = $(this).data('product-id'); // Get product ID
                        let quantity = $(this).siblings('input[name="quantity"]').val(); // Get updated quantity
                        updateCartItem(username, productId, quantity); // Pass username, product ID, and quantity to updateCartItem
                    });
                },
                error: function(error) {
                    console.error('Error fetching cart items:', error);
                }
            });
        }

        // Function to remove item from the cart
        function removeCartItem(username, productId) {
            $.ajax({
                url: 'http://localhost:8080/unisphereREST/rest/Cart/removeFromCart/' + username,
                method: 'PUT',
                contentType: 'text/plain',
                data: productId.toString(),
                success: function(response) {
                    // remove the product from the DOM
                    $('.product[data-product-id="' + productId + '"]').remove();

                    // Optionally update the total after removal
                    updateCartTotal();
                },
                error: function(error) {
                    console.error('Error removing cart item:', error);
                }
            });
        }

        function updateCartItem(username, productId, quantity) {
        	if(quantity<1){
        		alert("Quantity must be at least 1."); // Or use another method of feedback
                return;
        	}
            $.ajax({
                url: 'http://localhost:8080/unisphereREST/rest/Cart/updateQuantity/' + username,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    productId: productId,
                    quantity: quantity
                }),
                success: function(response) {
                    updateCartTotal();
                    
                },
                error: function(error) {
                    console.error('Error updating cart item:', error);
                }
            });
        }

        function updateCartTotal() {
            let total = 0.00;

            $('.product').each(function() {
                let price = parseFloat($(this).find('.product-price').text().replace('$', ''));
                let quantity = parseInt($(this).find('input[name="quantity"]').val());
                total += price * quantity;
            });

            $('#estimated-total').text('$' + total.toFixed(2));
        }
    });

    </script>
</body>
</html>
