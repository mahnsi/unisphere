<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.html" %>
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

                <h2>Shipping Address</h2>
                <div class="input-group">
                    <label>
                        <input type="radio" id = "exad" name="address-option" value="existing" checked> Use Existing Address
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
                            <input type="text" id="first-name" name="first-name" required>
                        </div>
                        <div class="input-group">
                            <label for="last-name">Last Name</label>
                            <input type="text" id="last-name" name="last-name">
                        </div>
                    </div>
                    <div class="input-group full-width">
                        <label for="street-address">Street Address</label>
                        <input type="text" id="street-address" name="street-address" required>
                    </div>
                    <br>
                    <div class="input-group full-width">
                        <label for="apartment">Apt/Suite/Buzzer/Etc.</label>
                        <input type="text" id="apartment" name="apartment">
                    </div>
                    <div class="input-group full-width">
                        <label for="country">Country</label>
                        <input type="text" id="country" name="country" required>
                    </div>
                    <br>
                    <div class="input-row">
                        <div class="input-group">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city" required>
                        </div>
                        <div class="input-group">
                            <label for="province">Province</label>
                            <input type="text" id="province" name="province" required>
                        </div>
                        <div class="input-group">
                            <label for="postal-code">Postal Code</label>
                            <input type="text" id="postal-code" name="postal-code" required>
                        </div>
                    </div>
                </div>

            <hr>
            
            <h2>Payment</h2>
            <div class="input-group">
                <label>
                    <input type="radio" name="payment-option" id = "expa" value="saved" checked> Use Saved Payment Method
                </label>
                <label>
                    <input type="radio" name="payment-option" value="new"> Enter New Payment Method
                </label>
            </div>

            <!-- Saved Payment Method Section -->
            <div id="saved-payment-section">
                <p id="saved-payment">saved Payment info loaded here..</p>
            </div>

            <!-- New Payment Method Section -->
            <div id="new-payment-section" style="display: none;">
	            <div class="input-group">
	                    <label for="card-holder-name">Card Holder Name</label>
	                    <input type="text" id="card-holder-name" name="card-holder-name" required>
	                </div>
                
                <div class="input-group">
                    <label for="card-number">Card Number</label>
                    <input type="text" id="card-number" name="card-number" required>
                </div>
                <div class="input-row">
                    <div class="input-group">
                        <label for="expiry-date">Expsiry Date</label>
                        <input type="text" id="expiry-date" name="expiry-date" placeholder="MM/YY" required>
                    </div>
                    <div class="input-group">
                        <label for="cvv">CVV</label>
                        <input type="text" id="cvv" name="cvv" required>
                    </div>
                </div>
            </div>
            
        </section>
        
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

    <script src="script/checkout.js"></script>
</body>
</html>

