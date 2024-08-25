<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.html" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout Page</title>
    <link rel="stylesheet" href="style/cart_wishlist_checkout.css">
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
                    <p id="existing-address">Address details will be loaded here...</p>
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
        
<hr>
        
            <h2>Payment</h2>
            <div class="input-group">
                <label>
                    <input type="radio" name="payment-option" value="saved" checked> Use Saved Payment Method
                </label>
                <label>
                    <input type="radio" name="payment-option" value="new"> Enter New Payment Method
                </label>
            </div>

            <!-- Saved Payment Method Section -->
            <div id="saved-payment-section">
                <p id="saved-payment">Saved payment details will be loaded here...</p>
            </div>

            <!-- New Payment Method Section -->
            <div id="new-payment-section" style="display: none;">
                <div class="input-group">
                    <label for="card-number">Card Number</label>
                    <input type="text" id="card-number" name="card-number">
                </div>
                <div class="input-row">
                    <div class="input-group">
                        <label for="expiry-date">Expiry Date</label>
                        <input type="text" id="expiry-date" name="expiry-date" placeholder="MM/YY">
                    </div>
                    <div class="input-group">
                        <label for="cvv">CVV</label>
                        <input type="text" id="cvv" name="cvv">
                    </div>
                </div>
            </div>
       </section>

        <section class="order-summary">
            <h3>Order Summary</h3>
            <p>Shipping:</p>
            <p>Tax:</p>
            <p>Discount:</p>
            <p><strong>Estimated Total $0.00</strong></p>
            <button class="checkout-button" id="place-order-button">Place Order</button>
        </section>
    </main>

   <script>
    $(document).ready(function() {
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

                // Load existing address
                fetchExistingAddressandPayment(username);

            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("Error fetching session data:", textStatus, errorThrown);
            }
        });

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

        function fetchExistingAddressandPayment(username) {
            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Users/searchByUsername/" + username,
                method: "GET",
                success: function(response) {
                    let address = response.address; 
                    let payment = response.payment;
                    $("#existing-address").text(
                    	    address.firstName + " " + address.lastName + "\n" +
                    	    address.streetAddress + ", " + address.apartment + "\n" +
                    	    address.city + ", " + address.province
                    	);

                    	$("#saved-payment").text(
                    	    payment.cardHolderName + "\n" +
                    	    payment.cardNumber + ", " + payment.expiryDate + "\n" +
                    	    payment.cvv
                    	);

                },
                error: function() {
                    console.error("Error fetching existing address.");
                }
            });
        }

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
