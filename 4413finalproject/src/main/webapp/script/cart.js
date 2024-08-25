$(document).ready(function() {
        fetchCartItems();

        // Function to fetch cart items for the session
        function fetchCartItems() {
            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Cart/getCart/",
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
                        removeCartItem(productId); 
                    });

                    $('.cart-items').on('click', '.update-button', function() {
                        let productId = $(this).data('product-id'); // Get product ID
                        let quantity = $(this).siblings('input[name="quantity"]').val(); // Get updated quantity
                        updateCartItem(productId, quantity); 
                    });
                },
                error: function(error) {
                    console.error('Error fetching cart items:', error);
                }
            });
        }

        // Function to remove item from the cart
        function removeCartItem(productId) {
            $.ajax({
                url: 'http://localhost:8080/unisphereREST/rest/Cart/removeFromCart/',
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

        function updateCartItem(productId, quantity) {
        	if(quantity<1){
        		alert("Quantity must be at least 1."); // Or use another method of feedback
                return;
        	}
            $.ajax({
                url: 'http://localhost:8080/unisphereREST/rest/Cart/updateQuantity/',
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
