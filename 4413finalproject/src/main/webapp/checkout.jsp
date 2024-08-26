<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Page</title>
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>

<body>
    <main>
        <section class="cart">
            <h2>Delivery</h2>
            <p>Standard Shipping Delivers in 1-4 business days</p>

            <form class="shipping-address">
                <h2>Shipping Address</h2>
                <div class="input-group">
                    <label>
                        <input type="radio" name="address-option" value="existing" checked> Use Existing Address
                    </label>
                    <label>
                        <input type="radio" name="address-option" value="new"> Enter New Address
                    </label>
                </div>

                <!-- Existing Address Section -->
                <div id="existing-address-section">
                    <p id="existing-address">Address Info loaded here..</p>
                </div>

                <!-- New Address Section -->
                <div id="new-address-section" style="display: none;">
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
                </div>
            </form>
        </section>
        
        <hr>
        
        <section class="order-summary">
            <h3>Order Summary</h3>
            <p id="shipping-cost">Shipping: $5.00</p>
            <p id="tax">Tax: $0.00</p>
            <p id="discount">Discount: $0.00</p>
            <p><strong id="subtotal">Subtotal: $0.00</strong></p>
            <p><strong id="estimated-total">Estimated Total: $0.00</strong></p>
            <button class="checkout-button" id="place-order-button">Place Order</button>
        </section>
    </main>

<script>
    $(document).ready(function() {
        // Fetch the cart data and update the order summary
        $.ajax({
            url: 'http://localhost:8080/unisphereREST/rest/Cart/getCart',
            method: 'GET',
            dataType: 'json',
            success: function(cart) {
                console.log("Cart data:", cart);
                updateOrderSummary(cart);
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching cart data:", textStatus, errorThrown);
            }
        });

        function updateOrderSummary(cart) {
            // Ensure that the cart data is valid before accessing its properties
            if (!cart || typeof cart.totalPrice !== 'number') {
                console.error("Cart data is not valid:", cart);
                return;
            }

            // Debugging the cart items and total price
            console.log("Total Price:", cart.totalPrice);
            console.log("Items in Cart:", cart.items);

            // Variables
            let subtotal = cart.totalPrice || 0; // Use the totalPrice directly from the cart data
            console.log("Calculated Subtotal:", subtotal); // Debugging subtotal calculation

            let shipping = 5.00; // Example flat rate shipping
            console.log("Flat Shipping Rate:", shipping); // Debugging shipping calculation

            let tax = subtotal * 0.13; // Assuming 13% tax rate
            console.log("Calculated Tax:", tax); // Debugging tax calculation

            let total = subtotal + shipping + tax;
            console.log("Final Estimated Total:", total); // Debugging final total calculation

            // Update the order summary on the page
            $('#subtotal').text(`Subtotal: $${subtotal.toFixed(2)}`);
            $('#shipping-cost').text(`Shipping: $${shipping.toFixed(2)}`);
            $('#tax').text(`Tax: $${tax.toFixed(2)}`);
            $('#estimated-total').text(`Estimated Total: $${total.toFixed(2)}`);
        }

        // Handle address option changes
        $("input[name='address-option']").change(function() {
            if ($(this).val() === "existing") {
                $("#existing-address-section").show();
                $("#new-address-section").hide();
            } else {
                $("#existing-address-section").hide();
                $("#new-address-section").show();
            }
        });

        // Handle payment option changes
        $("input[name='payment-option']").change(function() {
            if ($(this).val() === "saved") {
                $("#saved-payment-section").show();
                $("#new-payment-section").hide();
            } else {
                $("#saved-payment-section").hide();
                $("#new-payment-section").show();
            }
        });

        $("#place-order-button").click(function() {
            processOrder();
        });

        function processOrder() {
            let addressOption = $("input[name='address-option']:checked").val();
            let paymentOption = $("input[name='payment-option']:checked").val();

            let formData = {
                addressOption: addressOption,
                paymentOption: paymentOption
            };

            // If the user selects a new address, gather the address details
            if (addressOption === "new") {
                formData.address = {
                    firstName: $("#first-name").val() || null,
                    lastName: $("#last-name").val() || null,
                    streetAddress: $("#street-address").val() || null,
                    apartment: $("#apartment").val() || null,
                    city: $("#city").val() || null,
                    province: $("#province").val() || null
                };
            } else {
                formData.address = {};  // Empty object if using an existing address
            }

            // If the user selects a new payment method, gather the payment details
            if (paymentOption === "new") {
                formData.payment = {
                    cardNumber: $("#card-number").val() || null,
                    expiryDate: $("#expiry-date").val() || null,
                    cvv: $("#cvv").val() || null
                };
            } else {
                formData.payment = {};  // Empty object if using a saved payment method
            }

            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Orders",
                method: "POST",
                data: JSON.stringify(formData),
                contentType: "application/json",
                success: function(response) {
                    window.location.href = "orderconfirmation.jsp?orderNumber=" + response.orderNumber;
                },
                error: function() {
                    alert("Error processing your order. Please try again.");
                }
            });
        }
    });
</script>

</body>
</html>
