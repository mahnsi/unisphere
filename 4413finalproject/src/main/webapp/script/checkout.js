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
        
        function fetchExistingAddressandPayment(username) {
            $.ajax({
                url: "http://localhost:8080/unisphereREST/rest/Users/getAllUserInfo/" + username,
                method: "GET",
                success: function(response) {
                	console.log("success fetching all user info");
                	if(response){
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
                	}
                	
                	else{
                		$("#existing-address").text("No Saved Address. Please enter one below or on your profile.");
	
	                    $("#saved-payment").text("No Saved Payment. Please enter one below or on your profile.");
                		
                	}
                },
                error: function() {
                	console.log("hellor");
                }
            });
        }

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